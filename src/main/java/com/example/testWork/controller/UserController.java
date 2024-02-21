package com.example.testWork.controller;

import com.example.testWork.dto.UserCreateDTO;
import com.example.testWork.dto.UserDTO;
import com.example.testWork.dto.UserUpdateDTO;
import com.example.testWork.exception.ResourceNotFoundException;
import com.example.testWork.mapper.UserMapper;
import com.example.testWork.model.User;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.repository.UserRepository;
import com.example.testWork.util.UserUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> index() {
        List<User> users = userRepository.findAll();
        List<UserDTO> result = users.stream()
                .map(userMapper::map)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(result);
    }
    @GetMapping(path = "/{id}")
    public UserDTO show(@PathVariable long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.map(user);
    }
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO userData) {
        var user = userMapper.map(userData);
//        var password = passwordEncoder.encode(user.getPassword());
//        user.setPasswordDigest(password);
        userRepository.save(user);
        var userDTO = userMapper.map(user);
        return userDTO;
    }
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO update(@RequestBody UserUpdateDTO userData, @PathVariable long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userMapper.update(userData, user);
        userRepository.save(user);
        var userDTO = userMapper.map(user);
        return userDTO;
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
