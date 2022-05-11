package com.example.club_project.domain;

import com.example.club_project.domain.base.AuditingCreateUpdateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "clubs")
public class Club extends AuditingCreateUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    @Length(max = 10, message = "동아리명은 10자 이하여야 합니다.")
    @NotBlank(message = "동아리명은 필수 값 입니다.")
    private String name;

    @Column(length = 50, nullable = false)
    @Length(max = 50, message = "동아리 위치는 50자 이하여야 합니다.")
    @NotBlank(message = "동아리 위치는 필수 값 입니다.")
    private String address;

    @Column(length = 20, nullable = false)
    @Length(max = 20, message = "소속 대학교명은 20자 이하여야 합니다.")
    @NotBlank(message = "소속 대학교는 필수 값 입니다.")
    private String university;

    @Column(length = 100, nullable = false)
    @Length(max = 100, message = "동아리 소개글은 100자 이하여야 합니다.")
    @NotBlank(message = "동아리 소개는 필수 값 입니다.")
    private String description;

    @Length(max = 250, message = "imageUrl 길이는 250자 이하여야 합니다.")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotNull(message = "동아리 카테고리는 필수 값 입니다.")
    private Category category;

    public void update(String name, String address, String university, String description, Category category) {
        this.name = isEmpty(name) ? this.name : name;
        this.address = isEmpty(address) ? this.address : address;
        this.university = isEmpty(university) ? this.university : university;
        this.description = isEmpty(description) ? this.description : description;
        this.category = isEmpty(category) ? this.category : category;
    }

    public void updateImage(String imageUrl) {
        this.name = isEmpty(imageUrl) ? this.imageUrl : imageUrl;
    }

    @Builder
    private Club(String name, String address, String university, String description, Category category, String imageUrl) {
        this.name = name;
        this.address = address;
        this.university = university;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }
}
