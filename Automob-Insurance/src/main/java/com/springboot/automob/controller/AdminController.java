package com.springboot.automob.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.automob.model.Executive;
import com.springboot.automob.model.Vehicle;
import com.springboot.automob.service.ExecutiveService;
import com.springboot.automob.service.PolicyService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	 @Autowired
	 private ExecutiveService executiveService;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
    
	 @PostMapping("/createPolicy")
	    public ResponseEntity<?> createPolicy(@RequestBody Vehicle vehicle, @RequestParam String policyType) {
	        try {
	            policyService.calculatePremium(vehicle, policyType);
	            return new ResponseEntity<>("Policy created successfully", HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
	    }

    /*@PostMapping("/assign-executive")
    @PreAuthorize("hasRole('ADMIN')")
    public String assignExecutive() {
        // Logic to assign executive
        return "Executive assigned by Admin";
    }*/
    
	 @PostMapping("/add/executive")
	 public ResponseEntity<Executive> addExecutive(@RequestBody Executive executive) {
	     Executive createdExecutive = executiveService.addExecutive(executive);
	     return new ResponseEntity<>(createdExecutive, HttpStatus.CREATED);
	 }
    
    @PutMapping(" /{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }
}
