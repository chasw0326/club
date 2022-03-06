package com.example.club_project.security.util;

import com.example.club_project.dto.SignupDTO;
import com.example.club_project.entity.Member;
import com.example.club_project.entity.MemberRole;
import com.example.club_project.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
public class AuthUtil {

    @Autowired
    private MemberRepository memberRepository;
    // BCryptPasswordEncoder
    private final PasswordEncoder passwordEncoder;

    public AuthUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member signup(final SignupDTO dto) {
        log.info("signup email: {}", dto.getEmail());
        log.info("signup username: {}", dto.getUsername());
        log.info("signup name: {}", dto.getName());

        if (memberRepository.existsByEmail(dto.getEmail())) {
            // 중복 에러 던저야함
        }

        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        member.addMemberRole(MemberRole.USER);

        memberRepository.save(member);

        return member;
    }
}
