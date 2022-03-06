package com.example.club_project.entity;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Meeting extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetno;

    private LocalDateTime date;

    private String location;

    private String money;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;

}
