package com.example.testWork.controller;

import com.example.testWork.dto.UrlDTO;
import com.example.testWork.dto.HashDTO;
import com.example.testWork.dto.UrlUpdateDTO;
import com.example.testWork.dto.DataDto;
import com.example.testWork.mapper.HashMapper;
import com.example.testWork.mapper.UrlMapper;
import com.example.testWork.repository.HashGenerateRepository;
import com.example.testWork.repository.HashRepository;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.repository.UserRepository;
import com.example.testWork.service.ParseClass;
import com.example.testWork.service.StackManagement;
import com.example.testWork.service.UrlService;
import com.example.testWork.util.UserUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;
import java.util.List;


@RestController
@RequestMapping("/api/urls")
public class UrlController {

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
    @Autowired
    private ParseClass parse;
    @Autowired
    private UrlService urlService;


    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<UrlDTO> index() {
        return urlService.index();
    }
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public HashDTO create(@Valid @RequestBody DataDto data) throws MalformedURLException, Exception {
        return urlService.create(data);
    }

    @GetMapping(path = "/{name}")
    @Cacheable(value = "urls", key = "#name", sync = true)
    public UrlDTO shortLink(@PathVariable String name) {
        return urlService.shortLink(name);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UrlDTO update(@RequestBody UrlUpdateDTO urlData, @PathVariable long id) {
        return urlService.update(urlData, id);

    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        urlService.delete(id);
    }
}
