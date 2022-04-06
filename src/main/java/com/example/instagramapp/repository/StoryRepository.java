package com.example.instagramapp.repository;

import com.example.instagramapp.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {
    List<Story> findAllByUser_Id(Integer id);
//    @Query(value = "select p from STORY p where users_id != id", nativeQuery = true)
List<Story> findAllByUser_IdNotContains(Integer id);;
}
