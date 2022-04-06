package com.example.instagramapp.repository;

import com.example.instagramapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByUser_Id(Integer id);
//    @Query(value = "select p from Post p where users_id != id", nativeQuery = true)
    List<Post> findAllByUser_IdNotContains(Integer id);
}
