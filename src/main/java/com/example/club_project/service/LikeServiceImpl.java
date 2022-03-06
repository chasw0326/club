package com.example.club_project.service;

import com.example.club_project.entity.Board;
import com.example.club_project.entity.Likes;
import com.example.club_project.entity.Member;
import com.example.club_project.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class LikeServiceImpl {

    private final LikesRepository repository;

    @Transactional
    public void like(Long BoardId, Long principalId){
        Board board = Board.builder()
                .bno(BoardId)
                .build();
        Member member = Member.builder()
                .mno(principalId)
                .build();
        Likes like = Likes.builder()
                .board(board)
                .member(member)
                .build();

        log.info("BoardId: {}", BoardId);
        log.info("userId: {}", principalId);

        repository.save(like);
    }

    @Transactional
    public void undoLike(Long BoardId, Long principalId){
        log.info("BoardId: {}", BoardId);
        log.info("userId: {}", principalId);
        repository.unlike(BoardId, principalId);
    }

}
