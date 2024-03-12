package com.example.testWork.controller;

import com.example.testWork.dto.HashDTO;
import com.example.testWork.mapper.HashMapper;
import com.example.testWork.mapper.UrlMapper;
import com.example.testWork.model.Hash;

import com.example.testWork.repository.HashRepository;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.service.HashService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@RestController
@RequestMapping("/api/hash")
public class HashController {
    @Autowired
    private HashRepository hashRepository;
    @Autowired
    private HashMapper hashMapper;
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UrlMapper urlMapper;
    @Autowired
    private HashService hashService;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<HashDTO> index() {
        List<Hash> hashes = hashRepository.findAll();
        return hashService.index();
    }
    @DeleteMapping(path = "")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable long id) {
        hashService.remove(id);
    }
}
