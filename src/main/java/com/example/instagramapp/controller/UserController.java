package com.example.instagramapp.controller;


import com.example.instagramapp.entity.User;
import com.example.instagramapp.playload.UserDTO;
import com.example.instagramapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        List<User> all = userService.getAll();
        return ResponseEntity.ok().body(all);
    }


    @PostMapping()
    public HttpEntity<?> add(@RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.ok().body(userService.add(userDTO));
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.ok().body(userService.edit(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok().body(userService.delete(id));
    }

    @GetMapping("/{id}")
    public  HttpEntity<?> userDetail(@PathVariable Integer id){
        return ResponseEntity.ok().body(userService.getDetail(id));
    }


}
