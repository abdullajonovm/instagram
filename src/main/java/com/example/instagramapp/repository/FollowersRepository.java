package com.example.instagramapp.repository;

import com.example.instagramapp.entity.Followers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowersRepository extends JpaRepository<Followers, Integer> {
//    List<Followers> findByUserId(Integer user_id);
    Optional<Followers> findByUserId(Integer user_id);

}