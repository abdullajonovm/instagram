package com.example.instagramapp.repository;

import com.example.instagramapp.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Integer> {

    Optional<Code> findByCode(String code);
}
