package com.example.club_project.service;

import com.example.club_project.dto.ReplyDTO;
import com.example.club_project.entity.Board;
import com.example.club_project.entity.Member;
import com.example.club_project.entity.Reply;
import com.example.club_project.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl {

    private final ReplyRepository repository;

    @Transactional
    public Long register(ReplyDTO dto, Long principalId) {
        Reply reply = Reply.builder()
                .board(Board.builder()
                        .bno(dto.getBno())
                        .build())
                .member(Member.builder()
                        .mno(principalId)
                        .build())
                .content(dto.getText())
                .build();
        repository.save(reply);
        return reply.getRno();
    }

    @Transactional
    public void delete(Long rno, Long principalId) {
        Optional<Reply> result = repository.findById(rno);
        if (result.isPresent()) {
            Reply reply = result.get();
            if (!reply.getMember().getMno().equals(principalId)) {
                log.error("error deleting reply by userId: {}", principalId);
                throw new IllegalArgumentException(); // 권한 에러로 수정 예정
            }
            repository.deleteById(rno);
        }
    }

    @Transactional
    public void modify(ReplyDTO dto, Long principalId) {
        Optional<Reply> result = repository.findById(dto.getRno());
        if (result.isPresent()) {
            Reply reply = result.get();
            if (!reply.getMember().getMno().equals(principalId)) {
                log.error("error modifying reply by userId: {}", principalId);
                throw new IllegalArgumentException(); // 권한 에러로 수정 예정
            }
            repository.deleteById(reply.getRno());
        }
    }
}
