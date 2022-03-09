package com.example.club_project.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(value = AuditingEntityListener.class )
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String university;

    @Column(length = 500)
    private String profile_url;

    @Column(length = 100)
    private String introduction;

    @ElementCollection(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserRole> roleSet = new HashSet<>();

    public void addUserRole(UserRole userRole) {
        roleSet.add(userRole);
    }

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
