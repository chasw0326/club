package com.example.club_project.domain;

import com.example.club_project.domain.base.AuditingCreateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static org.apache.commons.lang3.StringUtils.isEmpty;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "categories")
public class Category extends AuditingCreateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    @Length(max = 20, message = "카테고리명은 20자 이하여야 합니다.")
    @NotBlank(message = "카테고리명은 필수 값 입니다.")
    private String name;

    @Column(length = 50, nullable = false)
    @Length(max = 50, message = "카테고리 설명은 50자 이하여야 합니다.")
    @NotBlank(message = "카테고리 설명은 필수 값 입니다.")
    private String description;

    public void update(String name, String description) {
        this.name = isEmpty(name) ? this.name : name;
        this.description = isEmpty(description) ? this.description : description;
    }

    @Builder
    private Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
