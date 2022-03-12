package com.example.club_project.security.service;

import com.example.club_project.domain.User;
import com.example.club_project.repository.UserRepository;
import com.example.club_project.security.dto.AuthUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // username으로 email을 사용합니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Login in progress..................");
        log.info("InstaUserDetailsService loadUserByUsername " + username);

        Optional<User> result = userRepository.findByEmail(username);

        if(!result.isPresent()){
            throw new UsernameNotFoundException("Check Email or Social ");
        }

        User user = result.get();

        log.info("-----------------------------");
        log.info(user.toString());

        return new AuthUserDTO(
                user.getEmail(),
                user.getPassword(),
                user.getRoleSet().stream()
                        .map(role-> new SimpleGrantedAuthority
                                ("ROLE_"+role.name())).collect(Collectors.toSet()),
                user.getId()
        );
    }
}
