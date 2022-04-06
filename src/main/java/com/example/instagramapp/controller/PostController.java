package com.example.instagramapp.controller;


import com.example.instagramapp.aop.CurrentUser;
import com.example.instagramapp.entity.Post;
import com.example.instagramapp.entity.User;
import com.example.instagramapp.playload.PostDTO;
import com.example.instagramapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/allUser")
    public HttpEntity<?> getAllUserPost(@CurrentUser User user){
        List<Post> all = postService.getAllCurrentUser(user);
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/allNotUser")
    public HttpEntity<?> getAllNotUserPost(@CurrentUser User user){
        List<Post> all = postService.getAllExeptCurrentUser(user);
        return ResponseEntity.ok().body(all);

    }

    @PostMapping()
    public HttpEntity<?> add(@RequestBody  PostDTO postDTO, @CurrentUser User user){
        return ResponseEntity.ok().body(postService.add(postDTO, user));

    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody  PostDTO postDTO, @CurrentUser User user){
        return ResponseEntity.ok().body(postService.edit(id, postDTO, user));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id, @CurrentUser User user){
        return ResponseEntity.ok().body(postService.delete(id, user));
    }

    @GetMapping("/{id}")
    public  HttpEntity<?> postDetail(@PathVariable Integer id){
        return ResponseEntity.ok().body(postService.getDetail(id));
    }
}
