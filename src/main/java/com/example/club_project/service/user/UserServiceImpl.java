package com.example.club_project.service.user;

import com.example.club_project.domain.User;
import com.example.club_project.domain.UserRole;
import com.example.club_project.repository.UserRepository;
import com.example.club_project.service.upload.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UploadService uploadService;


    @Override
    @Transactional
    public Long signup(User user){
        final String email = user.getEmail();
        if(userRepository.existsByEmail(email)){
            log.warn("Email already exists input: {}", email);
            throw new IllegalArgumentException("Email already exists input: " + email);
        }
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.addUserRole(UserRole.USER);
        userRepository.save(user);

        return user.getId();
    }

    @Override
    @Transactional
    public Long updateUserInfo(Long userId, String name, String nickname, String university, String introduction){
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
        user.updateUserInfo(name, nickname, university, introduction);
        return user.getId();
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, String oldPw, String newPw, String checkPw){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));

        if(!passwordEncoder.matches(oldPw, user.getPassword())){
            log.warn("이전 비밀번호와 다릅니다.");
            throw new IllegalArgumentException("이전 비밀번호와 다릅니다.");
        }

        if(!newPw.equals(checkPw)){
            log.warn("확인 비밀번호가 새 비밀번호와 다릅니다.");
            throw new IllegalArgumentException("확인 비밀번호가 새 비밀번호와 다릅니다.");
        }

        user.setPassword(passwordEncoder.encode(newPw));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateProfilePicture(Long userId, MultipartFile multipartFile){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));

        // TODO: uploadPath 외부에서 따로 지정할 것
        String uploadPath = null;
        String saveName = uploadService.uploadFile(multipartFile, null);

        user.updateProfilePicture(saveName);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
    }
}
