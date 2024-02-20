package com.example.userservice.Service;

import com.example.userservice.Models.Token;
import com.example.userservice.Models.User;
import com.example.userservice.Repository.TokenRepository;
import com.example.userservice.Repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Token login(String email, String password){

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            System.out.println("User is not there");
            return null;
        }

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())){
//            Throw password not match exception
            System.out.println("password does not match");
            return null;
        }

        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Token savedToken = tokenRepository.save(token);

        return savedToken;
    }

    public User signup(String email, String password, String name){
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setHashedPassword(bCryptPasswordEncoder.encode(password));
        User user = userRepository.save(newUser);
        return user;
    }

    public void logout(String token){
        System.out.println("token " + token);
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(token, false);
        if(optionalToken.isEmpty()){
            System.out.println("invalid token, token not present");
            return;
        }

        Token userToken = optionalToken.get();

        userToken.setDeleted(true);
        tokenRepository.save(userToken);
    }

    public User validateToken(String token){
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(token,false, new Date());
        if(optionalToken.isEmpty()){
            System.out.println("invalid token, token not present");
            return null;
        }

        Token userToken = optionalToken.get();
        return userToken.getUser();
    }
}
