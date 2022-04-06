package com.example.instagramapp.controller;

import com.example.instagramapp.entity.Comment;
import com.example.instagramapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    final CommentService commentService;

    @GetMapping("/{id}")
    public HttpEntity<?> getCommit(@PathVariable Integer id){
        List<Comment> commet = commentService.getCommet(id);
        return ResponseEntity.ok().body(commet);
    }

    @PostMapping("/{id}")
    public HttpEntity<?> addComment(@PathVariable Integer id, @RequestParam Integer userId, @RequestParam String message){
        List<Comment> commentList = commentService.addComment(id, userId, message);
        return ResponseEntity.ok().body(commentList);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deletComment(@PathVariable Integer id, @RequestParam Integer commentId){
        List<Comment> commentList = commentService.delete(commentId, id);
        return ResponseEntity.ok().body(commentList);
    }
}
