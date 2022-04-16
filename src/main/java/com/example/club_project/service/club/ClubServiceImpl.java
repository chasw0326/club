package com.example.club_project.service.club;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.domain.Category;
import com.example.club_project.domain.Club;
import com.example.club_project.exception.custom.AlreadyExistsException;
import com.example.club_project.exception.custom.NotFoundException;
import com.example.club_project.repository.ClubRepository;
import com.example.club_project.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;


@Transactional(readOnly = true)
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
    public ClubDTO.Response registerClub(String name, String address, String university, String description, Long categoryId) {
        return convertToDTO(this.register(name, address, university, description, categoryId));
    }

    @Override
    @Transactional
    public ClubDTO.Response updateClub(Long id, String name, String address, String university, String description, Long categoryId) {
        return convertToDTO(this.update(id, name, address, university, description, categoryId));
    }

    @Override
    public ClubDTO.Response convertToDTO(Club club) {
        return ClubDTO.Response.from(club);
    }

    /**
     * Service Region
     * for other Services
     */
    @Override
    @Transactional
    public Club register(String name, String address, String university, String description, Long categoryId) {
        requireNonNull(name, "name 입력값은 필수입니다.");
        requireNonNull(address, "address 입력값은 필수입니다.");
        requireNonNull(university, "university 입력값은 필수입니다.");
        requireNonNull(description, "description 입력값은 필수입니다.");
        requireNonNull(categoryId, "categoryId 입력값은 필수입니다.");

        if (existed(name, university)) {
            throw new AlreadyExistsException("이미 존재하는 클럽입니다");
        }

        Category category = categoryService.getCategory(categoryId);

        Club club = Club.builder()
                .name(name)
                .address(address)
                .university(university)
                .description(description)
                .category(category)
                .build();

        return clubRepository.save(club);
    }

    @Override
    public Club getClub(Long id) {
        requireNonNull(id, "id 입력값은 필수입니다.");

        return clubRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 클럽입니다."));
    }

    @Override
    public Club getClub(String name, String university) {
        requireNonNull(name, "name 입력값은 필수입니다.");
        requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.findByNameAndUniversity(name, university)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 클럽입니다."));
    }

    @Override
    public List<Club> getClubs(String university, Pageable pageable) {
        requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.findAllByUniversity(university, pageable);
    }

    @Override
    public List<Club> getClubs(String name, String university, Pageable pageable) {
        requireNonNull(name, "name 입력값은 필수입니다.");
        requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.findAllByNameAndUniversity(name, university, pageable);
    }

    @Override
    public List<Club> getClubs(List<Long> categories, String university, Pageable pageable) {
        requireNonNull(categories, "categories 입력값은 필수입니다.");
        requireNonNull(university, "university 입력값은 필수입니다.");

        List<Category> clubCategories = categoryService.getCategoriesById(categories);

        return clubRepository.findAll(clubCategories, university, pageable);
    }

    @Override
    public List<Club> getClubs(List<Long> categories, String university, String name, Pageable pageable) {
        requireNonNull(categories, "categories 입력값은 필수입니다.");
        requireNonNull(university, "university 입력값은 필수입니다.");
        requireNonNull(name, "name 입력값은 필수입니다.");

        List<Category> clubCategories = categoryService.getCategoriesById(categories);

        return clubRepository.findAll(clubCategories, name, university, pageable);
    }

    @Override
    @Transactional
    public Club update(Long id, String name, String address, String university, String description, Long categoryId) {
        requireNonNull(id, "id 입력값은 필수입니다.");
        requireNonNull(name, "name 입력값은 필수입니다.");
        requireNonNull(address, "address 입력값은 필수입니다.");
        requireNonNull(university, "university 입력값은 필수입니다.");
        requireNonNull(description, "description 입력값은 필수입니다.");
        requireNonNull(categoryId, "categoryId 입력값은 필수입니다.");

        Club updatedClub = clubRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 클럽입니다."));

        final String updatedClubName = StringUtils.isNotEmpty(name) ? name : updatedClub.getName();

        if (!updatedClubName.equals(updatedClub.getName())) {
            clubRepository.findByNameAndUniversity(updatedClubName, university).filter(club -> {
                if (!club.getId().equals(updatedClub.getId())) {
                    throw new AlreadyExistsException("이미 대학교에 존재하는 동아리명으로는 바꿀 수 없습니다");
                }
                return false;
            });
        }

        if (ObjectUtils.isEmpty(categoryId)) {
            updatedClub.update(updatedClubName, address, university, description, updatedClub.getCategory());
        } else {
            Category category = categoryService.getCategory(categoryId);
            updatedClub.update(updatedClubName, address, university, description, category);
        }

        return updatedClub;
    }

    @Override
    @Transactional
    public void updateImage(Long id, String imageUrl) {
        Club updatedClub = getClub(id);
        updatedClub.updateImage(imageUrl);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        requireNonNull(id, "id 입력값은 필수입니다.");

        Club deleteClub = clubRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("삭제하려는 id값이 존재하지 않습니다."));

        clubRepository.delete(deleteClub);
    }

    @Override
    public boolean existed(String name, String university) {
        requireNonNull(name, "name 입력값은 필수입니다.");
        requireNonNull(university, "university 입력값은 필수입니다.");

        return clubRepository.existsByNameAndUniversity(name, university);
    }
}
