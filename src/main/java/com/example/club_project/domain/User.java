package com.example.club_project.domain;


import com.example.club_project.domain.base.AuditingCreateUpdateEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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

    @Column(nullable = false, length = 60)
    @Length(min = 1, max = 60, message = "사이즈를 확인하세요.")
    @NotBlank(message = "이메일은 필수 값 입니다.")
    @Email
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 80)
    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    private String password;

    @Column(nullable = false, length = 20)
    @Length(min = 1, max = 20, message = "사이즈를 확인하세요.")
    @NotBlank(message = "이름은 필수 값 입니다.")
    private String name;

    @Column(nullable = false, length = 40)
    @Length(min = 1, max = 40, message = "사이즈를 확인하세요.")
    @NotBlank(message = "사용자 이름은 필수 값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",
            message = "사용자이름은 영어랑 숫자만 가능합니다.")
    private String nickname;

    @Column(nullable = false, length = 50)
    @Length(min = 1, max = 50, message = "사이즈를 확인하세요.")
    @NotBlank(message = "대학교명은 필수 값 입니다.")
    private String university;

    @Column(name= "profile_url", length = 500)
    private String profileUrl;

    @Column(nullable = false, length = 100)
    @Length(min = 1, max = 100, message = "자기소개는 필수 값 입니다.")
    @NotBlank(message = "자기소개는 필수 값 입니다.")
    private String introduction;

    @ElementCollection(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserRole> roleSet = new HashSet<>();

    public void addUserRole(UserRole userRole) {
        roleSet.add(userRole);
    }

    public void updateUser(String name, String nickname, String university, String introduction){
        this.name = name;
        this.nickname = nickname;
        this.introduction = introduction;
        this.university = university;
    }

    public void updatePassword(String password){
        this.password = password;
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
