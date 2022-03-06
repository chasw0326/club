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
@ToString
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(
        name = "LIKES_UK",
        columnNames = {"member", "board"}
)})
public class Likes extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;


}
