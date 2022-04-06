package com.example.instagramapp.playload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    //    @NotEmpty  //
//    @NotBlank //
//    @Email(regexp = "(.+)@gmail\\.com$", message = "Email emas!") //sabr tilayman gmail.com tekshirildi
//    @Email
//    @NotBlank(message = "Nomini kiritish shart")
    //String lar un
    @Size(min = 3, message = "Nomi 3tadn kam bo'lmasin")
    private String name;


    //field berilmasayam bo'sh berilsayam ishladi
    @NotBlank(message = "Parolni togri kiriting") @Length(min = 2, message = "Uzunlik man aytgandek emas")
    private String password;

   // @Pattern(regexp = "^(\\+\\d{3}[- .]?){2}\\d{4}$", message = "incorrect number")


}
