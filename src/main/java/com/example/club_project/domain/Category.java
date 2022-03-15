package com.example.club_project.domain;

import com.example.club_project.domain.base.AuditingCreateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "categories")
public class Category extends AuditingCreateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String description;

    public void update(String name, String description) {
        this.name = StringUtils.isEmpty(name) ? this.name : name;
        this.description = StringUtils.isEmpty(description) ? this.description : description;
    }

    @Builder
    private Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
