package com.springboot.automob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.automob.JwtUtil;
import com.springboot.automob.enums.RoleType;
import com.springboot.automob.model.User;
import com.springboot.automob.repository.UserRepository;
import com.springboot.automob.service.MyUserDetailsService;

@RestController
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Autowired
    private MyUserDetailsService userDetailsService;
 
    @Autowired
    private JwtUtil jwtUtil;
    
   
    
    @PostMapping("/auth/admin/token")
    public String createAdminAuthenticationToken(@RequestBody User authenticationRequest) throws Exception {
 
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect admin username or password", e);
        }
 
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        if (userDetails.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            throw new Exception("Unauthorized! Not an Admin");
        }
        
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
 
        return jwt;
    }

    
    // Admin Signup
    @PostMapping("/auth/admin/signup")
    public void adminSignup(@RequestBody User userInfo) {
        userInfo.setRoleType(RoleType.ADMIN);  // Set role as ADMIN
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
    }

    @GetMapping("/user/hello")
    public String userHello() {
        return "Hello, User!";
    }
 
    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello, Admin!";
    }
    
}