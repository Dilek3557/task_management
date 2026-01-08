package com.dilekkaraca.taskmanagament.user.service;

import com.dilekkaraca.taskmanagament.user.User;
import com.dilekkaraca.taskmanagament.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.name()))
                        .collect(Collectors.toSet())
        );
    }
}

