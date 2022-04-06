package com.example.instagramapp.service;


import com.example.instagramapp.entity.Attachment;
import com.example.instagramapp.entity.Post;
import com.example.instagramapp.entity.User;
import com.example.instagramapp.playload.ApiResponse;
import com.example.instagramapp.playload.PostDTO;
import com.example.instagramapp.repository.AttachmentRepository;
import com.example.instagramapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    public List<Post> getAllCurrentUser(User user){
        return postRepository.findAllByUser_Id(user.getId());
    }

    public List<Post> getAllExeptCurrentUser(User user){
        return postRepository.findAllByUser_IdNotContains(user.getId());
    }

    public ApiResponse getDetail(Integer id){
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()){
            return new ApiResponse("post not found", false);
        }
        return new ApiResponse("post details", true, post.get());
    }

    public ApiResponse add(PostDTO dto, User user){
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(dto.getAttachmentId());
        Attachment attachment = attachmentOptional.get();

        Post post = new Post();
        post.setAttachment(attachment);
        post.setUser(user);
        post.setDescription(dto.getDescription());
        postRepository.save(post);
        return new ApiResponse("added", true, post);
    }

    public ApiResponse delete(Integer id, User user){

        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            return new ApiResponse("post not found", false, true);
        }
        if (post.get().getUser()!=user){
            return new ApiResponse("you are not owner", false);
        }
        postRepository.delete(post.get());
        return new ApiResponse("deleted", true);
    }


    public ApiResponse edit(Integer id, PostDTO postDTO, User user){
        Optional<Post> postRepo = postRepository.findById(id);
        if (postRepo.isEmpty()){
            return new ApiResponse("post not found", false);
        }
        if (postRepo.get().getUser()!=user){
            return new ApiResponse("you are not owner", false);
        }

        Post post = postRepo.get();
        post.setDescription(postDTO.getDescription());
        postRepository.save(post);
        return new ApiResponse("edited", true, post);
    }

}


