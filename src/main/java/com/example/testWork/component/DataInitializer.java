package com.example.testWork.component;

import com.example.testWork.dto.UserCreateDTO;
import com.example.testWork.mapper.UserMapper;
import com.example.testWork.model.User;
import com.example.testWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        defaultUser();
    }
    public void defaultUser() {
        UserCreateDTO userData = new UserCreateDTO();
        userData.setName("Alexey");
        userData.setEmail("qwe@mail.ru");
        userData.setPassword("qwerty");
        User user = userMapper.map(userData);
        userRepository.save(user);
    }
}
