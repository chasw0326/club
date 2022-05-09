package com.example.club_project.domain;

import com.example.club_project.domain.base.AuditingCreateUpdateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "clubs")
public class Club extends AuditingCreateUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 20, nullable = false)
    private String university;

    @Column(length = 100, nullable = false)
    private String description;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void update(String name, String address, String university, String description, Category category) {
        checkArgument(
                isEmpty(name) || name.length() <= 10,
                "동아리명은 10자 이하여야 합니다.");
        checkArgument(
                isEmpty(address) || address.length() <= 50,
                "동아리 위치는 50자 이하여야 합니다.");
        checkArgument(
                isEmpty(university) || university.length() <= 20,
                "소속 대학교명은 20자 이하여야 합니다.");
        checkArgument(
                isEmpty(description) || description.length() <= 100,
                "동아리 소개글은 100자 이하여야 합니다.");

        this.name = isEmpty(name) ? this.name : name;
        this.address = isEmpty(address) ? this.address : address;
        this.university = isEmpty(university) ? this.university : university;
        this.description = isEmpty(description) ? this.description : description;
        this.category = isEmpty(category) ? this.category : category;
    }

    public void updateImage(String imageUrl) {
        checkArgument(
                isEmpty(imageUrl) || imageUrl.length() <= 250,
                "imageUrl 길이는 250자 이하여야 합니다.");

        this.imageUrl = isEmpty(imageUrl) ? this.imageUrl : imageUrl;
    }

    @Builder
    private Club(String name, String address, String university, String description, Category category, String imageUrl) {
        checkArgument(isNotEmpty(name), "동아리명은 필수 값 입니다.");
        checkArgument(name.length() <= 10, "동아리명은 10자 이하여야 합니다.");
        checkArgument(isNotEmpty(address), "동아리 위치는 필수 값 입니다.");
        checkArgument(address.length() <= 50, "동아리 위치는 50자 이하여야 합니다.");
        checkArgument(isNotEmpty(university), "소속 대학교는 필수 값 입니다.");
        checkArgument(university.length() <= 20, "소속 대학교명은 20자 이하여야 합니다.");
        checkArgument(isNotEmpty(description), "동아리 소개는 필수 값 입니다.");
        checkArgument(description.length() <= 100, "동아리 소개글은 100자 이하여야 합니다.");
        checkArgument(isNotEmpty(category), "동아리 카테고리는 필수 값 입니다.");
        checkArgument(
                isEmpty(imageUrl) || imageUrl.length() <= 250,
                "imageUrl 길이는 250자 이하여야 합니다.");

        this.name = name;
        this.address = address;
        this.university = university;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }
}
