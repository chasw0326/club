package com.example.club_project.service.user;

import com.example.club_project.domain.User;

public interface UserService {

    Long signup(User user);

    User getUser(Long principalId);
    
    Long updateUserInfo(Long principalId, String name, String nickname, String university, String introduction);

    void updatePassword(Long principalId, String oldPw, String newPw, String checkPw);

}
