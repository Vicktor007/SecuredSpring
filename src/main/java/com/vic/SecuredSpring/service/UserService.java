package com.vic.SecuredSpring.service;

import com.vic.SecuredSpring.Repository.UserRepo;
import com.vic.SecuredSpring.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
       repo.save(user);
        return user;
    }

    public String verify(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }
}