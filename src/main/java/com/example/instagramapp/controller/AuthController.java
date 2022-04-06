package com.example.instagramapp.controller;

import com.example.instagramapp.config.EmailConfig;
import com.example.instagramapp.entity.Code;
import com.example.instagramapp.entity.User;
import com.example.instagramapp.playload.ApiResponse;
import com.example.instagramapp.playload.LoginDTO;
import com.example.instagramapp.playload.SignUpDTO;
import com.example.instagramapp.repository.CodeRepository;
import com.example.instagramapp.repository.UserRepository;
import com.example.instagramapp.security.JwtProvider;
import com.example.instagramapp.service.AuthService;
import com.example.instagramapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    final JwtProvider jwtProvider;
    final AuthService authService;
    final EmailConfig emailConfig;
    final UserService userService;
    final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    final CodeRepository codeRepository;

    int i;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        //login qiladi Tizimda bor bo'lsa token generate qilishimz kerak

        ApiResponse responce;

        if (Objects.isNull(loginDTO.getName()) || Objects.isNull(loginDTO.getPassword())) {
            return ResponseEntity.badRequest().body("fields cannot be empty");
        }

        if (loginDTO.getName().endsWith("@signUpDTO.getGmail().com")) {
            responce = authService.loadUserByEmail(loginDTO.getName(), (loginDTO.getPassword()));
        } else if (loginDTO.getName().length() == 13 && loginDTO.getName().charAt(0) == '+') {
            responce = authService.loadUserByPhone(loginDTO.getName(), loginDTO.getPassword());
        } else {
            responce = authService.loadUserByUsername(loginDTO.getName(), (loginDTO.getPassword()));
        }


        if (!responce.getSuccess()) {
            return ResponseEntity.badRequest().body(new ApiResponse("bad credentials", false));
        }

        //UserDetails userDetails = authService.loadUserByUsername(loginDTO.getName());
        String token = jwtProvider.generateToken(loginDTO.getName());


        return ResponseEntity.ok().body(token);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpDTO signUpDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        i = (int) ((Math.random() + 1) * 1000000);
        message.setText("Hi,\n" +
                "\n" +
                "Someone tried to sign up for an Instagram account with " + signUpDTO.getGmail() + ". If it was you, enter this confirmation code in the app: " + i);
        message.setSubject("GO GO");
        message.setSentDate(new Date());
        message.setTo(signUpDTO.getGmail());
        JavaMailSender mailSender = emailConfig.send();
        mailSender.send(message);

        System.out.println("\n\n\n\n\n" + i);
        Code code = new Code();
        code.setCode(String.valueOf(i));
        code.setPassword(signUpDTO.getPassword());
        code.setUserName(signUpDTO.getUserName());
        code.setGmail(signUpDTO.getGmail());
        code.setFullName(signUpDTO.getFullName());
        codeRepository.save(code);

        return ResponseEntity.ok().body(new ApiResponse("Mana", true, signUpDTO));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String code) {

        Optional<Code> code1 = codeRepository.findByCode(code);

        if(!code1.isPresent()){
            return ResponseEntity.badRequest().body(new ApiResponse("incorrect code", false));
        }



        User user = new User();
        user.setPassword(code1.get().getPassword());
        user.setEmail(code1.get().getGmail());
        user.setName(code1.get().getFullName());
        user.setUserName(code1.get().getUserName());
        userRepository.save(user);
        return ResponseEntity.ok().body(new ApiResponse("code is true", true));

        }



//
//        if (i == id) {
//            User user = new User();
//            user.setEmail(signUpDTO.getGmail());
//            user.setUserName(signUpDTO.getUserName());
//            user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
//            userRepository.save(user);
//
//            System.out.println("\n\n\n\n\n" + i);
//        }
//        return ResponseEntity.status(405).body("This code is faled");
    }


//    //400 xatolik bo'lganda  aynan shu(MethodArgumentNotValidException) toifali xatolikni ushlaydi
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }

