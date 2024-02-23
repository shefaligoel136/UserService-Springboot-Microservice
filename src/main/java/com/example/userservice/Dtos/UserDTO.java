package com.example.userservice.Dtos;

import com.example.userservice.Models.Role;
import com.example.userservice.Models.User;
import jakarta.persistence.ManyToMany;

import java.util.List;

public class UserDTO {private String name;
    private String email;
    private String hashedPassword;
    private List<Role> roles;
    private boolean isEmailVerified;

    public static UserDTO from(User user){

        if(user == null){
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.email = user.getEmail();
        userDTO.name = user.getName();
        userDTO.roles = user.getRoles();
        userDTO.isEmailVerified = user.isEmailVerified();
        return userDTO;
    }

}
