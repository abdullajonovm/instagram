package com.example.instagramapp.controller;
import com.example.instagramapp.entity.User;
import com.example.instagramapp.playload.ApiResponse;
import com.example.instagramapp.service.FollowersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/followers")
@RequiredArgsConstructor
public class FollowersController {
    final FollowersService followersService;

    @GetMapping("/{id}")
    public HttpEntity<?> findAllFollowers(@PathVariable Integer id, User user){
        ApiResponse apiResponse = followersService.findAllFollowers(id, user);
        return ResponseEntity.ok().body(apiResponse);
    }

}
