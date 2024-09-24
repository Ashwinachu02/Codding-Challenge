package com.springboot.automob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.automob.enums.RoleType;
import com.springboot.automob.model.Executive;
import com.springboot.automob.model.User;
import com.springboot.automob.repository.ExecutiveRepository;
import com.springboot.automob.repository.UserRepository;

@Service
public class ExecutiveService {

    @Autowired
    private ExecutiveRepository executiveRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
 

    public Executive addExecutive(Executive executive) {
        User user = executive.getUser();
        user.setRoleType(RoleType.EXECUTIVE);
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        

        user = userRepository.save(user);
        

        executive.setUser(user);
        
       
        return executiveRepository.save(executive);
    }

}
