package com.example.userservice.Dtos;

import com.example.userservice.Models.Role;
import com.example.userservice.Models.User;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
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
