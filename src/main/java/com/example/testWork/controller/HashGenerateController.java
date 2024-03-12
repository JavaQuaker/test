package com.example.testWork.controller;


import com.example.testWork.dto.HashGenerateDTO;
import com.example.testWork.mapper.HashGenerateMapper;
import com.example.testWork.mapper.HashMapper;
import com.example.testWork.model.HashGenerate;
import com.example.testWork.repository.HashGenerateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/generate")
public class HashGenerateController {
    @Autowired
    private HashGenerateRepository hashGenerateRepository;
    @Autowired
    private HashMapper hashMapper;
    @Autowired
    private HashGenerateMapper hashGenerateMapper;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<HashGenerateDTO> index() {
        List<HashGenerateDTO> result = new ArrayList<>();
        List<HashGenerate> hashes = hashGenerateRepository.findAll();
        for (HashGenerate hash : hashes) {
            HashGenerateDTO dto = hashGenerateMapper.map(hash);
            result.add(dto);
        }
        return result;
    }
}
