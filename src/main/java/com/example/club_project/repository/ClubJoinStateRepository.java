package com.example.club_project.repository;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.domain.ClubJoinState;
import com.example.club_project.domain.JoinState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubJoinStateRepository extends JpaRepository<ClubJoinState, Long> {

    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.id = :id")
    Optional<ClubJoinState> findById(@Param("id") Long id);


    /**
     * User and Club Region (findByClubAndUser)
     */
    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.user.id = :userId " +
                        "and c.club.id = :clubId")
    Optional<ClubJoinState> find(@Param("userId") Long userId, @Param("clubId") Long clubId);

    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.user.id = :userId " +
                        "and c.club.id = :clubId " +
                        "and c.joinState = :joinState")
    Optional<ClubJoinState> find(@Param("userId") Long userId,
                                 @Param("clubId") Long clubId,
                                 @Param("joinState") JoinState joinState);

    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.user.id = :userId " +
                        "and c.club.id = :clubId " +
                        "and c.joinState <> :joinState")
    Optional<ClubJoinState> findExceptJoinState(@Param("userId") Long userId,
                                                @Param("clubId") Long clubId,
                                                @Param("joinState") JoinState joinState);

    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.user.id = :userId " +
                        "and c.club.id = :clubId " +
                        "and c.joinState <= :joinState")
    Optional<ClubJoinState> findContainingJoinState(@Param("userId") Long userId,
                                                    @Param("clubId") Long clubId,
                                                    @Param("joinState") JoinState joinState);


    /**
     * Club Region (findByClub)
     */
    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.club.id = :clubId")
    List<ClubJoinState> findAllByClub(@Param("clubId") Long clubId, Pageable pageable);

    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.club.id = :clubId " +
                        "and c.joinState = :joinState")
    List<ClubJoinState> findAllByClub(@Param("clubId") Long clubId,
                                      @Param("joinState") JoinState joinState,
                                      Pageable pageable);

    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.club.id = :clubId " +
                        "and c.joinState <= :joinState")
    List<ClubJoinState> findAllByClubContainingJoinState(@Param("clubId") Long clubId,
                                                         @Param("joinState") JoinState joinState,
                                                         Pageable pageable);


    /**
     * User Region (findByUser)
     */
    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.user.id = :userId")
    List<ClubJoinState> findAllByUser(@Param("userId") Long userId, Pageable pageable);

    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.user.id = :userId " +
                        "and c.joinState = :joinState")
    List<ClubJoinState> findAllByUser(@Param("userId") Long userId,
                                      @Param("joinState") JoinState joinState,
                                      Pageable pageable);

    @Query("select c from ClubJoinState c " +
                        "join fetch c.user " +
                        "join fetch c.club club " +
                        "join fetch club.category " +
                    "where c.user.id = :userId " +
                        "and c.joinState <= :joinState")
    List<ClubJoinState> findAllByUserContainingJoinState(@Param("userId") Long userId,
                                                         @Param("joinState") JoinState joinState,
                                                         Pageable pageable);

    /**
     * For ClubWithMemberCount
     */
    @Query("select new com.example.club_project.controller.club.ClubDTO(" +
                "club.id, " +
                "club.name, " +
                "club.address, " +
                "club.university, " +
                "club.description, " +
                "club.imageUrl, " +
                "category.id, " +
                "count(user)" +
            ") " +
            "from ClubJoinState c " +
                "join c.user user " +
                "join c.club club " +
                "join club.category category " +
            "where club.university = :university " +
                "and c.isUsed = true " +
            "group by club")
    List<ClubDTO> findAllByUniversity(@Param("university") String university, Pageable pageable);

    @Query("select new com.example.club_project.controller.club.ClubDTO(" +
                "club.id, " +
                "club.name, " +
                "club.address, " +
                "club.university, " +
                "club.description, " +
                "club.imageUrl, " +
                "category.id, " +
                "count(user)" +
            ") " +
            "from ClubJoinState c " +
                "join c.user user " +
                "join c.club club " +
                "join club.category category " +
            "where club.name like %:name% " +
                "and club.university = :university " +
                "and c.isUsed = true " +
            "group by club")
    List<ClubDTO> findAllByNameAndUniversity(@Param("name") String name,
                                             @Param("university") String university,
                                             Pageable pageable);

    @Query("select new com.example.club_project.controller.club.ClubDTO(" +
                "club.id, " +
                "club.name, " +
                "club.address, " +
                "club.university, " +
                "club.description, " +
                "club.imageUrl, " +
                "category.id, " +
                "count(user)" +
            ") " +
            "from ClubJoinState c " +
                "join c.user user " +
                "join c.club club " +
                "join club.category category " +
            "where club.university = :university " +
                "and category.id in :categories " +
                "and c.isUsed = true " +
            "group by club")
    List<ClubDTO> findAll(@Param("categories") List<Long> categories,
                          @Param("university") String university,
                          Pageable pageable);

    @Query("select new com.example.club_project.controller.club.ClubDTO(" +
                "club.id, " +
                "club.name, " +
                "club.address, " +
                "club.university, " +
                "club.description, " +
                "club.imageUrl, " +
                "category.id, " +
                "count(user)" +
            ") " +
            "from ClubJoinState c " +
                "join c.user user " +
                "join c.club club " +
                "join club.category category " +
            "where club.name like %:name% " +
                "and club.university = :university " +
                "and category.id in :categories " +
                "and c.isUsed = true " +
            "group by club")
    List<ClubDTO> findAll(@Param("categories") List<Long> categories,
                          @Param("name") String name,
                          @Param("university") String university,
                          Pageable pageable);
}
