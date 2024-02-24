package com.example.userservice.Security.Models;

import com.example.userservice.Models.Role;
import com.example.userservice.Models.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
public class CustomUserDetails implements UserDetails {

    private List<CustomGrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public CustomUserDetails(){}

    public CustomUserDetails(User user){
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.username = user.getEmail();
        this.password = user.getHashedPassword();

        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role role: user.getRoles()){
            grantedAuthorities.add(new CustomGrantedAuthority(role));
        }

        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for(Role role: user.getRoles()){
//            grantedAuthorities.add(new CustomGrantedAuthority(role));
//        }
//
//        return grantedAuthorities;
        return authorities;
    }

    @Override
    public String getPassword() {
//        return user.getHashedPassword();
        return password;
    }

    @Override
    public String getUsername() {
//        return user.getEmail();
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
//        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
//        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return true;
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
//        return true;
        return enabled;
    }
}
