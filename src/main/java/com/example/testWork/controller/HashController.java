package com.example.testWork.controller;

import com.example.testWork.dto.HashCreateDTO;
import com.example.testWork.dto.HashDTO;
import com.example.testWork.mapper.HashMapper;
import com.example.testWork.model.Hash;
import com.example.testWork.repository.HashRepository;
import com.example.testWork.service.StackManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/hash")
public class HashController {
    @Autowired
    private HashRepository hashRepository;
    @Autowired
    private HashMapper hashMapper;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<HashDTO> index() {
        List<Hash> hashes = hashRepository.findAll();
        List<HashDTO> result = new ArrayList<>();
        for (Hash hash : hashes) {
            HashDTO dto = hashMapper.map(hash);
            result.add(dto);
        }
        return result;
    }

}
