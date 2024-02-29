package com.example.testWork.controller;

import com.example.testWork.dto.*;
import com.example.testWork.exception.ResourceNotFoundException;
import com.example.testWork.mapper.HashMapper;
import com.example.testWork.mapper.UrlMapper;
import com.example.testWork.model.Hash;
import com.example.testWork.model.Url;
import com.example.testWork.repository.HashGenerateRepository;
import com.example.testWork.repository.HashRepository;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.repository.UserRepository;
import com.example.testWork.service.ParseClass;
import com.example.testWork.service.StackManagement;
import com.example.testWork.util.UserUtils;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/urls")
public class UrlController {
//    private static final String ONLY_OWNER_BY_ID = """
//            @userRepository.findById(#id).get().getEmail() == authentication.name
//        """;

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashGenerateRepository hashGenerateRepository;
    @Autowired
    private HashRepository hashRepository;
    @Autowired
    private UrlMapper urlMapper;
    @Autowired
    private HashMapper hashMapper;
    @Autowired
    private StackManagement stackManagement;

    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserUtils userUtils;

    private ParseClass parse;






    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<UrlDTO> index() {
        List<Url> urls = urlRepository.findAll();
        List<UrlDTO> result = new ArrayList<>();
        for (Url url : urls) {
            UrlDTO urlDTO = urlMapper.map(url);
            result.add(urlDTO);
        }
        return result;
    }
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public HashDTO create(@Valid @RequestBody DataDto data) {
        UrlCreateDTO urlCreateDTO = data.getUrlData();
        HashCreateDTO hashCreateDTO = data.getHashData();
        var currentUser = userUtils.getCurrentUser();
        long id = currentUser.getId();
        Optional<Url> url = urlRepository.findByUrl(urlCreateDTO.getUrl());
        if (url.isPresent()) {
            Optional<Hash> hash = hashRepository.findById(url.get().getId());
            HashDTO dto = hashMapper.map(hash.get());
            return dto;

        } else {
            urlCreateDTO.setAssigneeId(id);
            Url url1 = urlMapper.map(urlCreateDTO);
            urlRepository.save(url1);
            var urlOriginal = urlRepository.findById(url1.getId());
            if (hashCreateDTO == null) {
                hashCreateDTO = stackManagement.pop();
                hashCreateDTO.setNameHashId(urlOriginal.get().getId());
                System.out.println("urls_id " + urlOriginal.get().getId());

                Hash hash = hashMapper.map(hashCreateDTO);
                hashRepository.save(hash);
                System.out.println("testRepoHash " + " " + hash.getName());
                HashDTO hashDTO = hashMapper.map(hash);
                return hashDTO;
            } else {
                hashCreateDTO.setName(hashCreateDTO.getName());
                hashCreateDTO.setNameHashId(urlOriginal.get().getId());
                Hash hash = hashMapper.map(hashCreateDTO);
                System.out.println("nameWrite " + hashCreateDTO.getName());
                hashRepository.save(hash);
                HashDTO dto = hashMapper.map(hash);
                return dto;
            }
        }
    }

/*
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public HashDTO create(@Valid @RequestBody UrlCreateDTO urlData) {
       var currentUser = userUtils.getCurrentUser();
       long id = currentUser.getId();
       Optional<Url> url1 = urlRepository.findByUrl(urlData.getUrl());

       if (url1.isPresent()) {
           Optional<Hash> hash = hashRepository.findById(url1.get().getId());
           HashDTO dto = hashMapper.map(hash.get());
           return dto;
       } else {
           urlData.setAssigneeId(id);
           Url url = urlMapper.map(urlData);
           urlRepository.save(url);
           var urlOriginal = urlRepository.findById(url.getId());

           HashCreateDTO hashCreateDTO = stackManagement.pop();
           hashCreateDTO.setNameHashId(urlOriginal.get().getId());
           System.out.println("urls_id " + urlOriginal.get().getId());

           Hash hash = hashMapper.map(hashCreateDTO);
           hashRepository.save(hash);
           System.out.println("testRepoHash " + " " + hash.getName());
           HashDTO hashDTO = hashMapper.map(hash);
           return hashDTO;
       }
   }
*/

//    @GetMapping(path = "/{id}")
//    public UrlDTO show(@Valid @PathVariable long id) {
//        var url = urlRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Url not found"));
//        return urlMapper.map(url);
//    }

    @GetMapping(path = "/{name}")
    public UrlDTO shortLink(@PathVariable String name) {
        Hash hash = hashRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Hash not found"));
        HashDTO hashDTO = hashMapper.map(hash);
        long hashId = hashDTO.getNameHashId();
        System.out.println("hashId " + hashId);
        Url url = urlRepository.findById(hashId)
                .orElseThrow(() -> new ResourceNotFoundException("Url not found"));
        UrlDTO urlDTO = urlMapper.map(url);
        return urlDTO;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UrlDTO update(@RequestBody UrlUpdateDTO urlData, @PathVariable long id) {
        var url = urlRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Url not found"));
        urlMapper.update(urlData, url);
        urlRepository.save(url);
        var urlDTO = urlMapper.map(url);
        return urlDTO;

    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        var url = urlRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Url not found"));
        urlRepository.deleteById(id);
    }

}
