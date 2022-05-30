package com.example.club_project.repository.query;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.domain.JoinState;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.club_project.domain.QCategory.category;
import static com.example.club_project.domain.QClub.club;
import static com.example.club_project.domain.QClubJoinState.clubJoinState;

/**
 * Entity가 아닌 API 전용 DTO 객체를 select 하기 위한 Repository
 */
@RequiredArgsConstructor
@Component
public class ClubQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClubDTO.SimpleResponse> findAll(List<Long> categories, String university, String name, Pageable pageable) {

        return queryFactory
                .select(
                    Projections.constructor(ClubDTO.SimpleResponse.class,
                        club.id,
                        club.name,
                        club.address,
                        club.university,
                        club.description,
                        club.imageUrl,
                        club.category.id,
                        clubJoinState.id.count()
                    )
                )
                .from(club)
                    .join(club.category, category)
                    .leftJoin(clubJoinState).on(club.id.eq(clubJoinState.club.id))
                                            .on(clubJoinState.joinState.ne(JoinState.NOT_JOINED))
                                            .on(clubJoinState.isUsed.eq(true))
                .where(
                    universityEq(university),
                    categoriesEq(categories),
                    nameContains(name)
                )
                .groupBy(club.id, club.name)
                .orderBy(club.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    private BooleanExpression universityEq(String university) {
        if (StringUtils.isBlank(university)) {
            return null;
        }
        return club.university.eq(university);
    }

    private BooleanExpression categoriesEq(List<Long> categories) {
        if (ObjectUtils.isEmpty(categories)) {
            return null;
        }
        return club.category.id.in(categories);
    }

    private BooleanExpression nameContains(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return club.name.contains(name);
    }
}
