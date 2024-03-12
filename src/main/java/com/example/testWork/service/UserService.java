package com.example.testWork.service;

import com.example.testWork.dto.UserDTO;
import com.example.testWork.dto.UserCreateDTO;
import com.example.testWork.dto.UserUpdateDTO;
import com.example.testWork.exception.ResourceNotFoundException;

import com.example.testWork.mapper.UrlMapper;
import com.example.testWork.mapper.UserMapper;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.repository.UserRepository;
import com.example.testWork.util.UserUtils;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UrlMapper urlMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StackManagement stackManagement;

    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private ParseClass parse;
    public List<UserDTO> getAll() {
        var users = userRepository.findAll();
        return users.stream()
                .map(userMapper::map)
                .toList();
    }
    public UserDTO findById(long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.map(user);
    }
    public UserDTO create(UserCreateDTO userData) {
        var user = userMapper.map(userData);
        userRepository.save(user);
        var userDTO = userMapper.map(user);
        return userDTO;
    }
    public UserDTO update(UserUpdateDTO userData, long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userMapper.update(userData, user);
        userRepository.save(user);
        var userDTO = userMapper.map(user);
        return userDTO;
    }
    public void delete(long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}