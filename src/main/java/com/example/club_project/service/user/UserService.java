package com.example.club_project.service.user;

import com.example.club_project.domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    Long signup(User user);

    User getUser(Long userId);
    
    Long updateUserInfo(Long userId, String name, String nickname, String university, String introduction);

    void updateProfilePicture(Long userId, MultipartFile multipartFile);

    void updatePassword(Long userId, String oldPw, String newPw, String checkPw);

}
