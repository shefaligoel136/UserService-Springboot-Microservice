package com.example.userservice.Security.Models;

import com.example.userservice.Models.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;

    public CustomGrantedAuthority(){}

    public CustomGrantedAuthority(Role role){
        this.authority = role.getName();
    }
    @Override
    public String getAuthority() {
        return authority;

    }
}
