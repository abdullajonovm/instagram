package com.example.instagramapp.controller;

import com.example.instagramapp.aop.CurrentUser;
import com.example.instagramapp.entity.User;
import com.example.instagramapp.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    final JwtProvider jwtProvider;


    @GetMapping
    public String test(@CurrentUser User user) {

        System.out.println(user);
        return "Zo'r ishladi!";
    }

    @PostMapping("/add")
    public ResponseEntity saveTest() {
//        String token = request.getHeader("Authorization");
//        token = token.substring(7); //Bearer so'zini qirqib oldik
//        if (jwtProvider.validateToken(token)) {
//            if (jwtProvider.expireToken(token)) {
//                String usernameFromToken = jwtProvider.getUsernameFromToken(token);
//                return ResponseEntity.ok().body("Chotki");
//            }
//        }
//        return ResponseEntity.ok().body("Adashdiz!");


        return ResponseEntity.ok().body("Zo'r");
    }
}
