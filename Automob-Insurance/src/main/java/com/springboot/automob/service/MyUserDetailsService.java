package com.springboot.automob.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.automob.model.User;
import com.springboot.automob.repository.UserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInfo = userRepository.getUserByUsername(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Convert RoleType to String and use it in SimpleGrantedAuthority
        return new org.springframework.security.core.userdetails.User(
                userInfo.getUsername(), 
                userInfo.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userInfo.getRoleType().name()))
        );
    }
}
