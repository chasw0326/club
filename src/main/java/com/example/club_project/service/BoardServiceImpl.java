package com.example.club_project.service;

import com.example.club_project.dto.BoardDTO;
import com.example.club_project.entity.Board;
import com.example.club_project.entity.Member;
import com.example.club_project.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl {

    private final BoardRepository boardRepository;

    @Transactional
    public Long register(Long principalId, BoardDTO dto){

        Board board = Board.builder()
                .member(Member.builder()
                        .mno(principalId)
                        .build())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        boardRepository.save(board);
        return board.getBno();
    }

    @Transactional
    public void remove(Long bno, Long principalId){
        Optional<Board> result = boardRepository.findById(bno);
        if(!result.isPresent()){
            throw new IllegalArgumentException();
        }
        Board board = result.get();
        if(board.getMember().getMno().equals(principalId)){
            boardRepository.delete(board);
        }else{
            throw new IllegalArgumentException();
        }
    }

}
