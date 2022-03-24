package com.example.club_project.service.user;

import com.example.club_project.controller.user.PasswordDTO;
import com.example.club_project.controller.user.UserUpdateDTO;
import com.example.club_project.domain.User;
import com.example.club_project.domain.UserRole;
import com.example.club_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //TODO: userRepositoty.findById 있는 부분들 메서드로 추출할 것
    @Override
    @Transactional(readOnly = true)
    public UserUpdateDTO.Response getUserUpdateRespDTO(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
        return UserUpdateDTO.Response.builder()
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PasswordDTO.changeScreenData getPasswordRespDTO(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
        return PasswordDTO.changeScreenData.builder()
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .build();
    }

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
    public Long updateUserInfo(Long principalId, String name, String nickname, String university, String introduction){
        User user = userRepository.findById(principalId)
        .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
        user.updateUserInfo(name, nickname, university, introduction);
        return user.getId();
    }

    @Override
    @Transactional
    public void updatePassword(Long principalId, String oldPw, String newPw, String checkPw){
        User user = userRepository.findById(principalId)
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
    @Transactional(readOnly = true)
    public User getUser(Long principalId){
        return userRepository.findById(principalId)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
    }
}
