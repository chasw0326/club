package com.example.club_project.service.user;

import com.example.club_project.controller.user.PasswordDTO;
import com.example.club_project.controller.user.UserUpdateDTO;
import com.example.club_project.domain.User;

public interface UserService {

    UserUpdateDTO.Response getUserUpdateRespDTO(Long id);

    PasswordDTO.Response getPasswordRespDTO(Long id);

    Long signup(User user);

    User getUser(Long principalId);

    Long updateUserInfo(Long principalId, String name, String nickname, String university, String introduction);

    void updatePassword(Long principalId, String oldPw, String newPw, String checkPw);

}
