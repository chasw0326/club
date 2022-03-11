package com.example.club_project.domain;

import com.example.club_project.domain.base.AuditingCreateUpdateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.Objects;


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

    public void update(String name, String address, String university, String description, Category category, String imageUrl) {
        this.name = ObjectUtils.isEmpty(name) ? this.name : name;
        this.address = ObjectUtils.isEmpty(address) ? this.address : address;
        this.university = ObjectUtils.isEmpty(university) ? this.university : university;
        this.description = ObjectUtils.isEmpty(description) ? this.description : description;
        this.category = ObjectUtils.isEmpty(category) ? this.category : category;
        this.imageUrl = ObjectUtils.isEmpty(imageUrl) ? this.imageUrl : imageUrl;
    }

    @Builder
    private Club(String name, String address, String university, String description, Category category, String imageUrl) {
        Objects.requireNonNull(name, "name값은 필수입니다.");
        Objects.requireNonNull(address, "address값은 필수입니다.");
        Objects.requireNonNull(university, "university값은 필수입니다.");
        Objects.requireNonNull(description, "description값은 필수입니다.");
        Objects.requireNonNull(category, "category값은 필수입니다.");

        this.name = name;
        this.address = address;
        this.university = university;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }
}
