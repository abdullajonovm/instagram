package com.example.instagramapp.controller;

import com.example.instagramapp.aop.CurrentUser;
import com.example.instagramapp.entity.Story;
import com.example.instagramapp.entity.User;
import com.example.instagramapp.playload.PostDTO;
import com.example.instagramapp.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    @GetMapping("/allUser")
    public HttpEntity<?> getAllUserPost(@CurrentUser User user){
        List<Story> all = storyService.getAllCurrentUser(user);
        return ResponseEntity.ok().body(all);

    }

    @GetMapping("/allNotUser")
    public HttpEntity<?> getAllNotUserPost(@CurrentUser User user){
        List<Story> all = storyService.getAllExceptCurrentUser(user);
        return ResponseEntity.ok().body(all);

    }

    @PostMapping()
    public HttpEntity<?> add(@RequestBody PostDTO postDTO, @CurrentUser User user){
        return ResponseEntity.ok().body(storyService.add(postDTO, user));

    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id, @CurrentUser User user){
        return ResponseEntity.ok().body(storyService.delete(id, user));
    }

    @GetMapping("/{id}")
    public  HttpEntity<?> postDetail(@PathVariable Integer id){
        return ResponseEntity.ok().body(storyService.getDetail(id));
    }
}