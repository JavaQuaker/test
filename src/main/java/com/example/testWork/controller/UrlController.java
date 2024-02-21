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
import com.example.testWork.service.StackManagement;
import com.example.testWork.util.UserUtils;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
//    @PostMapping(path = "")
//    @ResponseStatus(HttpStatus.CREATED)
//    public UrlDTO create(@RequestBody UrlCreateDTO urlData) {
//        Url url = urlMapper.map(urlData);
//        urlRepository.save(url);
//        UrlDTO urlDTO = urlMapper.map(url);
//        return urlDTO;
//    }
//    @PreAuthorize(ONLY_OWNER_BY_ID)
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public HashDTO create(@Valid @RequestBody UrlCreateDTO urlData) {
       var currentUser = userUtils.getCurrentUser();
       long id = currentUser.getId();
        System.out.println("id " + id);

        var user = userRepository.findById(id);

        urlData.setAssigneeId(id);
        Url url = urlMapper.map(urlData);
        System.out.println("urlAssignee " + urlData.getAssigneeId());

        var original = urlData.getUrl();

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
