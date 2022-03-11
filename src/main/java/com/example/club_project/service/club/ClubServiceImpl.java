package com.example.club_project.service.club;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.domain.Category;
import com.example.club_project.domain.Club;
import com.example.club_project.repository.ClubRepository;
import com.example.club_project.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final CategoryService categoryService;

    @Transactional
    public ClubDTO.Response register(String name, String address, String university, String description, String categoryName, String imageUrl) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        Objects.requireNonNull(address, "address 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");
        Objects.requireNonNull(description, "description 입력값은 필수입니다.");
        Objects.requireNonNull(categoryName, "categoryName 입력값은 필수입니다.");

        if (existed(name, university)) {
            throw new EntityExistsException("이미 존재하는 클럽입니다");
        }

        Category category = categoryService.getCategory(categoryName);

        Club club = Club.builder()
                .name(name)
                .address(address)
                .university(university)
                .description(description)
                .category(category)
                .imageUrl(imageUrl)
                .build();

        return ClubDTO.Response.from(clubRepository.save(club));
    }

    @Transactional
    public ClubDTO.Response update(Long id, String name, String address, String university, String description, String categoryName, String imageUrl) {
        Club updatedClub = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));

        if (ObjectUtils.isEmpty(categoryName)) {
            updatedClub.update(name, address, university, description, updatedClub.getCategory(), imageUrl);
        } else {
            Category category = categoryService.getCategory(categoryName);
            updatedClub.update(name, address, university, description, category, imageUrl);
        }

        return ClubDTO.Response.from(updatedClub);
    }

    @Transactional(readOnly = true)
    public boolean existed(String name, String university) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.existsByNameAndUniversity(name, university);
    }

    @Transactional(readOnly = true)
    public ClubDTO.Response getClub(Long id) {
        Objects.requireNonNull(id, "id 입력값은 필수입니다.");

        return clubRepository.findById(id)
                .map(ClubDTO.Response::from)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));
    }

    @Transactional(readOnly = true)
    public ClubDTO.Response getClub(String name, String university) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.findByNameAndUniversity(name, university)
                .map(ClubDTO.Response::from)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));
    }

    @Transactional(readOnly = true)
    public List<ClubDTO.Response> getClubs(String university, Pageable pageable) {
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.findAllByUniversity(university, pageable)
                .stream()
                .map(ClubDTO.Response::from)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<ClubDTO.Response> getClubs(List<String> categories, String university, Pageable pageable) {
        Objects.requireNonNull(categories, "categories 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        List<Category> clubCategories = categoryService.getCategories(categories);

        return clubRepository.findAll(clubCategories, university, pageable)
                .stream()
                .map(ClubDTO.Response::from)
                .collect(toList());
    }

    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "id 입력값은 필수입니다.");

        clubRepository.deleteById(id);
    }
}