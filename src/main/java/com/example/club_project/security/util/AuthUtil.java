package com.example.club_project.security.util;

import com.example.club_project.dto.PasswordDTO;
import com.example.club_project.dto.SignupDTO;
import com.example.club_project.entity.Member;
import com.example.club_project.entity.MemberRole;
import com.example.club_project.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
    public void changePassword(final PasswordDTO dto, Long principalId){
        Optional<Member> result = memberRepository.findById(principalId);
        if (!result.isPresent()) {
            log.error("result is empty, input: {}", principalId);
            throw new IllegalArgumentException("멤버id를 확인하세요. 입력한 값:" + principalId);
        }
        Member member = result.get();
        // 입력한 비밀번호와 현재 비밀번호가 다를때
        if (!passwordEncoder.matches(dto.getOldPw(), member.getPassword())) {
            log.warn("이전 비밀번호가 다릅니다.");
            throw new IllegalArgumentException("이전 비밀번호가 다릅니다.");
        }
        // 1차, 2차 비밀번호 입력이 다를때
        if (!dto.getNewPw().equals(dto.getCheckNewPw())) {
            log.warn("확인 비밀번호가 새 비밀번호와 다릅니다.");
            throw new IllegalArgumentException("확인 비밀번호가 새 비밀번호와 다릅니다.");
        }
        member.setPassword(passwordEncoder.encode(dto.getNewPw()));
        memberRepository.save(member);
        log.info("changePassword");
    }
}
