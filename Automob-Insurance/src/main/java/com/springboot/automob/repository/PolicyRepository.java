package com.springboot.automob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.automob.model.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {

}
