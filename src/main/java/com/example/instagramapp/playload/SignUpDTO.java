package com.example.instagramapp.playload;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class SignUpDTO {
    @Email
    private String gmail;
    private String fullName;
    private String userName;
    private String password;
    private Integer code;

}
