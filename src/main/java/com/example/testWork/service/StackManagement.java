package com.example.testWork.service;

import com.example.testWork.dto.HashCreateDTO;
import com.example.testWork.dto.HashGenerateDTO;
import com.example.testWork.exception.ResourceNotFoundException;
import com.example.testWork.mapper.HashGenerateMapper;
import com.example.testWork.mapper.HashMapper;

import com.example.testWork.model.Hash;
import com.example.testWork.model.HashGenerate;
import com.example.testWork.repository.HashGenerateRepository;
import com.example.testWork.repository.HashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StackManagement {
    @Autowired
    private HashGenerateRepository hashGenerateRepository;
    @Autowired
    private HashRepository hashRepository;
    @Autowired
    private HashMapper hashMapper;
    @Autowired
    private HashGenerateMapper hashGenerateMapper;

    public HashCreateDTO pop() {
        HashGenerate topElement = hashGenerateRepository.findFirstByField()
                .orElseThrow(() -> new ResourceNotFoundException("hash not found"));

        HashGenerateDTO dto = new HashGenerateDTO();
        dto = hashGenerateMapper.map(topElement);
        hashGenerateRepository.delete(topElement);
        HashCreateDTO hashCreateDTO = new HashCreateDTO();
        hashCreateDTO.setName(topElement.getName());
        System.out.println("qqqqqqq" + " " + hashCreateDTO.getName());

        return hashCreateDTO;
    }
}
