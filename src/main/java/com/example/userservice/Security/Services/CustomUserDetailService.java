package com.example.userservice.Security.Services;

import com.example.userservice.Models.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Security.Models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    CustomUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail(username);

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with email " + username + " not found.");
        }

        User user = optionalUser.get();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return userDetails;
    }
}
