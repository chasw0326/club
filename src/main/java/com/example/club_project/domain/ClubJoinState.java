package com.example.club_project.domain;

import com.example.club_project.domain.base.AuditingCreateUpdateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "club_join_states")
public class ClubJoinState extends AuditingCreateUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private JoinState joinState;

    private boolean isUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "user 값은 필수 값 입니다.")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "club 값은 필수 값 입니다.")
    private Club club;

    public boolean isNotUsed() {
        return !isUsed;
    }

    public void update(JoinState joinState) {
        this.joinState = joinState;
    }

    public void update(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public void update(JoinState joinState, boolean isUsed) {
        this.joinState = joinState;
        this.isUsed = isUsed;
    }

    @Builder
    private ClubJoinState(User user, Club club, JoinState joinState) {
        this.user = user;
        this.club = club;
        this.joinState = joinState;
        this.isUsed = true;
    }
}
