package com.example.instagramapp.service;

import com.example.instagramapp.entity.Attachment;
import com.example.instagramapp.entity.Story;
import com.example.instagramapp.entity.User;
import com.example.instagramapp.playload.ApiResponse;
import com.example.instagramapp.playload.PostDTO;
import com.example.instagramapp.repository.AttachmentRepository;
import com.example.instagramapp.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryService {
    @Autowired
    StoryRepository storyRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    public List<Story> getAllCurrentUser(User user){
        return storyRepository.findAllByUser_Id(user.getId());
    }

    public List<Story> getAllExceptCurrentUser(User user){
        return storyRepository.findAllByUser_IdNotContains(user.getId());
    }

    public ApiResponse getDetail(Integer id){
        Optional<Story> post = storyRepository.findById(id);
        if (!post.isPresent()){
            return new ApiResponse("post not found", false);
        }
        return new ApiResponse("post details", true, post.get());
    }

    public ApiResponse add(PostDTO dto, User user){
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(dto.getAttachmentId());
        Attachment attachment = attachmentOptional.get();

        Story post = new Story();
        post.setAttachment(attachment);
        post.setUser(user);
        storyRepository.save(post);
        return new ApiResponse("added", true, post);
    }

    public ApiResponse delete(Integer id, User user){

        Optional<Story> post = storyRepository.findById(id);
        if(!post.isPresent()){
            return new ApiResponse("post not found", false, true);
        }
        if (post.get().getUser()!=user){
            return new ApiResponse("you are not owner", false);
        }
        storyRepository.delete(post.get());
        return new ApiResponse("deleted", true);
    }
}
