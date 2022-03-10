package com.example.club_project.service.club;

import com.example.club_project.domain.Category;
import com.example.club_project.domain.Club;
import com.example.club_project.repository.ClubRepository;
import com.example.club_project.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final CategoryService categoryService;

    @Transactional
    public Club register(String name, String address, String university, String description, String categoryName, String imageUrl) {

        if (existed(name, university)) {
            throw new EntityExistsException("이미 존재하는 클럽입니다");
        }

        Category category = categoryService.getCategory(categoryName);

        if (imageUrl == null) {
            return clubRepository.save(Club.of(name, address, university, description, category));
        } else {
            return clubRepository.save(Club.of(name, address, university, description, category, imageUrl));
        }
    }

    @Transactional(readOnly = true)
    public boolean existed(String name, String university) {
        return clubRepository.existsByNameAndUniversity(name, university);
    }

    @Transactional(readOnly = true)
    public Club getClub(String name, String university) {
        return clubRepository.findByNameAndUniversity(name, university)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));
    }

    @Transactional(readOnly = true)
    public List<Club> getClubs(String university) {
        return clubRepository.findAllByUniversity(university);
    }

    @Transactional(readOnly = true)
    public List<Club> getClubs(List<String> categories, String university) {
        List<Category> clubCategories = categoryService.getCategories(categories);

        return clubRepository.findAll(clubCategories, university);
    }

    @Transactional
    public long delete(String name, String university) {
        return clubRepository.deleteByNameAndUniversity(name, university);
    }
}
