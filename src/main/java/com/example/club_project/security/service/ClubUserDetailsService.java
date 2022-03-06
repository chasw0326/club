package com.example.club_project.security.service;

import com.example.club_project.entity.Member;
import com.example.club_project.repository.MemberRepository;
import com.example.club_project.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Login in progress..................");
        log.info("InstaUserDetailsService loadUserByUsername " + username);

        Optional<Member> result = repository.findByEmail(username);

        if(!result.isPresent()){
            throw new UsernameNotFoundException("Check Email or Social ");
        }

        Member member = result.get();

        log.info("-----------------------------");
        log.info(member);

        return new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getRoleSet().stream()
                        .map(role-> new SimpleGrantedAuthority
                                ("ROLE_"+role.name())).collect(Collectors.toSet()),
                member.getMno()
        );
    }
}
