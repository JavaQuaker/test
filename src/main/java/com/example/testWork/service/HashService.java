package com.example.testWork.service;

import com.example.testWork.dto.HashDTO;
import com.example.testWork.exception.ResourceNotFoundException;
import com.example.testWork.mapper.HashMapper;
import com.example.testWork.mapper.UrlMapper;
import com.example.testWork.model.Hash;
import com.example.testWork.repository.HashRepository;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HashService {
    @Autowired
    private HashRepository hashRepository;
    @Autowired
    private HashMapper hashMapper;
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UrlMapper urlMapper;
    @Autowired
    private UserUtils userUtils;


    public List<HashDTO> index() {
        List<Hash> hashes = hashRepository.findAll();
        List<HashDTO> result = new ArrayList<>();
        for (Hash hash : hashes) {
            HashDTO dto = hashMapper.map(hash);
            result.add(dto);
        }
        return result;
    }

    public void remove(long id) {
        Hash hash = hashRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hash not found"));
        hashRepository.deleteById(id);
    }

}
