package com.example.instagramapp.repository;

import com.example.instagramapp.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following, Integer> {
}