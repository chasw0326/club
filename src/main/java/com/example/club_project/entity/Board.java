package com.example.club_project.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"member"})
@Entity
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    //공지사항인지 가입문의인지 자유글인지
    private String type;

    @Transient
    private Long likeCnt;

    @Transient
    private boolean likeState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;
}
