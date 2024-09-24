package com.springboot.automob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.automob.model.Executive;

public interface ExecutiveRepository extends JpaRepository<Executive, Integer> {
}
