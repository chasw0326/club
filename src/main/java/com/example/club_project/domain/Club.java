package com.example.club_project.domain;

import com.example.club_project.domain.base.AuditingCreateUpdateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public void update(String imageUrl) {
        Objects.requireNonNull(imageUrl, "imageUrl must not be null");
        this.imageUrl = imageUrl;
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Club(String name, String address, String university, String description, Category category) {
        this.name = name;
        this.address = address;
        this.university = university;
        this.description = description;
        this.category = category;
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Club(String name, String address, String university, String description, Category category, String imageUrl) {
        this.name = name;
        this.address = address;
        this.university = university;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public static Club of(String name, String address, String university, String description, Category category) {
        return Club.builder()
                .name(name)
                .address(address)
                .university(university)
                .description(description)
                .category(category)
                .build();
    }

    public static Club of(String name, String address, String university, String description, Category category, String imageUrl) {
        return Club.builder()
                .name(name)
                .address(address)
                .university(university)
                .description(description)
                .category(category)
                .imageUrl(imageUrl)
                .build();
    }
}
