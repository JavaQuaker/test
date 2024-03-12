package com.example.testWork.component;

import com.example.testWork.dto.UserCreateDTO;
import com.example.testWork.dto.HashGenerateCreateDTO;
import com.example.testWork.mapper.HashGenerateMapper;
import com.example.testWork.mapper.HashMapper;
import com.example.testWork.mapper.UserMapper;
import com.example.testWork.model.HashGenerate;
import com.example.testWork.model.User;
import com.example.testWork.repository.HashGenerateRepository;
import com.example.testWork.repository.UserRepository;

import com.example.testWork.service.StackManagement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import com.example.testWork.service.Converter;
import org.springframework.stereotype.Component;




@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final HashMapper hashMapper;
    @Autowired
    private HashGenerateMapper hashGenerateMapper;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final HashGenerateRepository hashGenerateRepository;
    private final StackManagement stackManagement;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        defaultUser();
        addHash();
//        stackManagement.pop(); метод для проверки работы запроса на выборку из базы stack
    }
    public void defaultUser() {
        UserCreateDTO userData = new UserCreateDTO();
        userData.setName("Alexey");
        userData.setEmail("qwe@mail.ru");
        userData.setPassword("qwerty");
        User user = userMapper.map(userData);
        System.out.println("userTEST " + user.getName());
        System.out.println("userTest " + user.getEmail());
        System.out.println("userPassword " + user.getPassword());
        userRepository.save(user);
    }
    public void addHash() {
        for (int i = 0; i < 100; i++) {
            HashGenerateCreateDTO dto = new HashGenerateCreateDTO();
            dto.setName(Converter.convert());
            HashGenerate hash = hashGenerateMapper.map(dto);
            hashGenerateRepository.save(hash);
        }
    }
}
