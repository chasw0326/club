package com.example.club_project.service.user;

import com.example.club_project.controller.user.UserDTO;
import com.example.club_project.domain.User;
import com.example.club_project.domain.UserRole;
import com.example.club_project.repository.UserRepository;
import com.example.club_project.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidateUtil validateUtil;

    //TODO: userRepositoty.findById 있는 부분들 메서드로 추출할 것
    @Override
    @Transactional(readOnly = true)
    public UserDTO.UpdateResponse getUserUpdateRespDTO(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
        return UserDTO.UpdateResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .introduction(user.getIntroduction())
                .nickname(user.getNickname())
                .university(user.getUniversity())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO.NicknameAndProfile getUsernameAndPicture(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
        return UserDTO.NicknameAndProfile.builder()
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .build();
    }

    @Override
    @Transactional
    public Long signup(User user) {
        final String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists input: {}", email);
            throw new IllegalArgumentException("Email already exists input: " + email);
        }
        String rawPassword = user.getPassword();
        validatePassword(rawPassword);

        String encPassword = passwordEncoder.encode(rawPassword);
        user.updatePassword(encPassword);
        user.addUserRole(UserRole.USER);
        validateUtil.validate(user);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    @Transactional
    public Long updateUser(Long principalId, String name, String nickname, String university, String introduction) {
        User user = userRepository.findById(principalId)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
        user.updateUser(name, nickname, university, introduction);
        validateUtil.validate(user);
        return user.getId();
    }

    @Override
    @Transactional
    public void updatePassword(Long principalId, String oldPw, String newPw, String checkPw) {
        User user = userRepository.findById(principalId)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));

        if (!passwordEncoder.matches(oldPw, user.getPassword())) {
            log.warn("이전 비밀번호와 다릅니다.");
            throw new IllegalArgumentException("이전 비밀번호와 다릅니다.");
        }

        if (!newPw.equals(checkPw)) {
            log.warn("확인 비밀번호가 새 비밀번호와 다릅니다.");
            throw new IllegalArgumentException("확인 비밀번호가 새 비밀번호와 다릅니다.");
        }

        validatePassword(newPw);
        user.updatePassword(passwordEncoder.encode(newPw));

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long principalId) {
        return userRepository.findById(principalId)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
    }

    private void validatePassword(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (!password.matches(pattern)) {
            // TODO: 예외 수정 예정
            throw new RuntimeException("비밀번호 형식 틀림");
        }
    }
}

