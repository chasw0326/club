package com.example.club_project.service.user;

import com.example.club_project.controller.user.UserDTO;
import com.example.club_project.domain.User;

public interface UserService {

    UserDTO.UpdateResponse getUserUpdateRespDTO(Long id);

    UserDTO.NicknameAndProfile getUsernameAndPicture(Long id);

    Long signup(User user);

    User getUser(Long principalId);

    Long updateUser(Long principalId, String name, String nickname, String university, String introduction);

    void updatePassword(Long principalId, String oldPw, String newPw, String checkPw);

    void updateProfileImage(Long userId, String profileImageUrl);
}
