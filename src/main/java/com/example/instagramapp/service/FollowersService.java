package com.example.instagramapp.service;

import com.example.instagramapp.entity.Followers;
import com.example.instagramapp.entity.User;
import com.example.instagramapp.playload.ApiResponse;
import com.example.instagramapp.repository.FollowersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowersService {

    final FollowersRepository followersRepository;

    public ApiResponse findAllFollowers(Integer id, User user) {
        Optional<Followers> followersList = followersRepository.findByUserId(user.getId());
        return new ApiResponse("This followers", true, followersList);
    }
}
