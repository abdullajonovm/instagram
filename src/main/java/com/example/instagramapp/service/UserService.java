package com.example.instagramapp.service;

import com.example.instagramapp.entity.User;
import com.example.instagramapp.playload.ApiResponse;
import com.example.instagramapp.playload.UserDTO;
import com.example.instagramapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public ApiResponse add(UserDTO dto){
        Optional<User> user = userRepository.findByUserName(dto.getUserName());
        if (user.isPresent()){
            return new ApiResponse("this username already exists", false);
        }
        User user1 = new User();
        user1.setUserName(dto.getUserName());
        user1.setBio(dto.getBio());
        user1.setEmail(dto.getEmail());
        user1.setGender(dto.getGender());
        user1.setPhoneNumber(dto.getPhoneNumber());
        user1.setName(dto.getName());
        user1.setPassword(passwordEncoder.encode(dto.getPassword()));

        return new ApiResponse("added", true, userRepository.save(user1));

    }

    public ApiResponse getDetail(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            return new ApiResponse("user not found", false);
        }
        return new ApiResponse("user details", true, user.get());
    }

    public ApiResponse edit (Integer id, UserDTO userDTO){
        Optional<User> userRepo = userRepository.findById(id);
        if (!userRepo.isPresent()){
            return new ApiResponse("user not found", false);
        }
        User user = userRepo.get();
        user.setUserName(userDTO.getUserName());
        user.setName(userDTO.getName());
        user.setGender(userDTO.getGender());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setBio(userDTO.getBio());
        return new ApiResponse("edited", true, user);
    }

    public ApiResponse delete(Integer id){
        Optional<User> userRepo = userRepository.findById(id);
        if (!userRepo.isPresent()){
            return new ApiResponse("user not found", false);
        }
        userRepository.delete(userRepo.get());
        return  new ApiResponse("deleted", true);
    }


}
