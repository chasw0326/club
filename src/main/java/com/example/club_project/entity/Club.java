package com.example.club_project.entity;

import lombok.*;
import org.hibernate.action.internal.OrphanRemovalAction;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Club extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    private String owner;

    private String intro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @NotEmpty
    private String university;

}
