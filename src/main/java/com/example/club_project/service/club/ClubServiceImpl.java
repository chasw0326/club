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

    /**
     * DTO region
     * for Controller
     */
    @Override
    @Transactional
    public ClubDTO.Response registerClub(String name, String address, String university, String description, Long categoryId, String imageUrl) {
        return convertToDTO(this.register(name, address, university, description, categoryId, imageUrl));
    }

    @Override
    @Transactional(readOnly = true)
    public ClubDTO.Response getClubDto(Long id) {
        return convertToDTO(this.getClub(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubDTO.Response> getClubDtos(String university, Pageable pageable) {
        return this.getClubs(university, pageable)
                .stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubDTO.Response> getClubDtos(String name, String university, Pageable pageable) {
        return this.getClubs(name, university, pageable)
                .stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubDTO.Response> getClubDtos(List<Long> categories, String university, Pageable pageable) {
        return this.getClubs(categories, university, pageable)
                .stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubDTO.Response> getClubDtos(List<Long> categories, String university, String name, Pageable pageable) {
        return this.getClubs(categories, university, name, pageable)
                .stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    @Transactional
    public ClubDTO.Response updateClub(Long id, String name, String address, String university, String description, Long categoryId, String imageUrl) {
        return convertToDTO(this.update(id, name, address, university, description, categoryId, imageUrl));
    }

    private ClubDTO.Response convertToDTO(Club club) {
        return ClubDTO.Response.from(club);
    }

    /**
     * Service Region
     * for other Services
     */
    @Override
    @Transactional
    public Club register(String name, String address, String university, String description, Long categoryId, String imageUrl) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        Objects.requireNonNull(address, "address 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");
        Objects.requireNonNull(description, "description 입력값은 필수입니다.");
        Objects.requireNonNull(categoryId, "categoryId 입력값은 필수입니다.");

        if (existed(name, university)) {
            throw new EntityExistsException("이미 존재하는 클럽입니다");
        }

        Category category = categoryService.getCategory(categoryId);

        Club club = Club.builder()
                .name(name)
                .address(address)
                .university(university)
                .description(description)
                .category(category)
                .imageUrl(imageUrl)
                .build();

        return clubRepository.save(club);
    }

    @Override
    @Transactional(readOnly = true)
    public Club getClub(Long id) {
        Objects.requireNonNull(id, "id 입력값은 필수입니다.");

        return clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public Club getClub(String name, String university) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.findByNameAndUniversity(name, university)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Club> getClubs(String university, Pageable pageable) {
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.findAllByUniversity(university, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Club> getClubs(String name, String university, Pageable pageable) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.findAllByNameAndUniversity(name, university, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Club> getClubs(List<Long> categories, String university, Pageable pageable) {
        Objects.requireNonNull(categories, "categories 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        List<Category> clubCategories = categoryService.getCategoriesById(categories);

        return clubRepository.findAll(clubCategories, university, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Club> getClubs(List<Long> categories, String university, String name, Pageable pageable) {
        Objects.requireNonNull(categories, "categories 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");

        List<Category> clubCategories = categoryService.getCategoriesById(categories);

        return clubRepository.findAll(clubCategories, name, university, pageable);
    }

    @Override
    @Transactional
    public Club update(Long id, String name, String address, String university, String description, Long categoryId, String imageUrl) {
        Objects.requireNonNull(id, "id 입력값은 필수입니다.");
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        Club updatedClub = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));

        clubRepository.findByNameAndUniversity(name, university).filter(club -> {
            if (!club.getId().equals(updatedClub.getId())) {
                throw new RuntimeException("이미 대학교에 존재하는 동아리명으로는 바꿀 수 없습니다");
            }
            return false;
        });

        if (ObjectUtils.isEmpty(categoryId)) {
            updatedClub.update(name, address, university, description, updatedClub.getCategory(), imageUrl);
        } else {
            Category category = categoryService.getCategory(categoryId);
            updatedClub.update(name, address, university, description, category, imageUrl);
        }

        return updatedClub;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "id 입력값은 필수입니다.");

        Club deleteClub = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("삭제하려는 id값이 존재하지 않습니다."));

        clubRepository.delete(deleteClub);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existed(String name, String university) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        Objects.requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.existsByNameAndUniversity(name, university);
    }
}
