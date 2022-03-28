package com.example.club_project.domain;

import com.example.club_project.domain.base.AuditingCreateUpdateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post extends AuditingCreateUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;

    @Column(nullable = false, length = 100)
    @Length(max = 100, message = "사이즈를 확인하세요.")
    @NotBlank(message = "제목은 필수 값 입니다.")
    private String title;

    @Column(nullable = false, length = 500)
    @Length(min = 1, max = 500, message = "사이즈를 확인하세요.")
    @NotNull(message = "내용을 입력하세요")
    private String content;

    @Builder
    private Post(User user, Club club, String title, String content){
        this.user = user;
        this.club = club;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}


