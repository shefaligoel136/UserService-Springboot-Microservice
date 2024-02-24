package com.example.userservice.Controllers;

import com.example.userservice.Dtos.LoginRequestDTO;
import com.example.userservice.Dtos.LogoutRequestDTO;
import com.example.userservice.Dtos.SignupRequestDTO;
import com.example.userservice.Dtos.UserDTO;
import com.example.userservice.Models.Token;
import com.example.userservice.Models.User;
import com.example.userservice.Service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Token token = userService.login(email,password);
        return token.getValue();
    }

    @PostMapping("/signup")
    public UserDTO signUp(@RequestBody SignupRequestDTO signupRequest){
        String email = signupRequest.getEmail();
        String password = signupRequest.getPassword();
        String name = signupRequest.getName();
        return UserDTO.from(userService.signup(email,password,name));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO logoutRequest){
        String token = logoutRequest.getToken();
        userService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public UserDTO validateToken(@PathVariable("token") @NonNull String token){
        return UserDTO.from(userService.validateToken(token));
    }


}
