package com.example.club_project.domain;

import com.example.club_project.domain.base.AuditingCreateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


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
        checkArgument(
                isEmpty(name) || name.length() <= 20,
                "카테고리명은 20자 이하여야 합니다.");
        checkArgument(
                isEmpty(description) || description.length() <= 50,
                "카테고리 설명은 50자 이하여야 합니다.");

        this.name = isEmpty(name) ? this.name : name;
        this.description = isEmpty(description) ? this.description : description;
    }

    @Builder
    private Category(String name, String description) {
        checkArgument(isNotEmpty(name), "카테고리명은 필수 값 입니다.");
        checkArgument(name.length() <= 20, "카테고리명은 20자 이하여야 합니다.");
        checkArgument(isNotEmpty(description), "카테고리 설명은 필수 값 입니다.");
        checkArgument(description.length() <= 50, "카테고리 설명은 50자 이하여야 합니다.");

        this.name = name;
        this.description = description;
    }
}
