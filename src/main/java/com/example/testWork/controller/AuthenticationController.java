package com.example.testWork.controller;

import com.example.testWork.dto.AuthRequest;
import com.example.testWork.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String create(@RequestBody AuthRequest authRequest) {
        var authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(authRequest.getUsername());
    }
}

