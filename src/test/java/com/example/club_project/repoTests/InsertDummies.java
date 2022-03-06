package com.example.club_project.repoTests;

import com.example.club_project.entity.*;
import com.example.club_project.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class InsertDummies {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void ex(){
        Member member = Member.builder()
                .email("chasw326@naver.com")
                .name("cha")
                .password("1234")
                .build();

        memberRepository.save(member);

        Board board = Board.builder()
                .title("title")
                .content("content")
                .member(Member.builder()
                        .mno(1L)
                        .build())
                .build();

        boardRepository.save(board);
        System.out.println(member.getMno());
        System.out.println(board.getBno());
        Reply reply = Reply.builder()
                .board(board)
                .member(member)
                .content("dldldl")
                .build();

        replyRepository.save(reply);
    }

    @Test
    void ex2(){
        Reply reply = replyRepository.getById(2L);
        replyRepository.delete(reply);
    }


    @Test
    void ex34(){
//        Club club = clubRepository.getById(1L);
//        clubRepository.delete(club);
        Meeting meeting = meetingRepository.getById(1L);
        meetingRepository.delete(meeting);
    }
}
