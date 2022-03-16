package com.example.club_project.domain;


import com.example.club_project.domain.base.AuditingCreateUpdateEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "roleSet")
@Table(name = "users")
public class User extends AuditingCreateUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Setter
    @JsonIgnore
    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String university;

    @Column(name= "profile_url", length = 500)
    private String profileUrl;

    @Column(nullable = false, length = 100)
    private String introduction;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> roleSet = new HashSet<>();

    public void addUserRole(UserRole userRole) {
        roleSet.add(userRole);
    }

    public void updateUserInfo(String name, String nickname, String university, String introduction){
        this.name = name;
        this.nickname = nickname;
        this.introduction = introduction;
        this.university = university;
    }

    @Builder
    private User(String email, String name, String profileUrl, String password, String nickname, String university, String introduction){
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.password = password;
        this.nickname = nickname;
        this.university = university;
        this.introduction = introduction;
    }
}
