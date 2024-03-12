package com.example.testWork.service;

import com.example.testWork.dto.UrlDTO;
import com.example.testWork.dto.UrlCreateDTO;
import com.example.testWork.dto.UrlUpdateDTO;
import com.example.testWork.dto.HashDTO;
import com.example.testWork.dto.HashCreateDTO;
import com.example.testWork.dto.HashUpdateDTO;
import com.example.testWork.dto.DataDto;
import com.example.testWork.exception.ResourceNotFoundException;
import com.example.testWork.mapper.HashMapper;
import com.example.testWork.mapper.UrlMapper;
import com.example.testWork.model.Hash;
import com.example.testWork.model.Url;
import com.example.testWork.repository.HashGenerateRepository;
import com.example.testWork.repository.HashRepository;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.repository.UserRepository;
import com.example.testWork.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class UrlService {
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



    public List<UrlDTO> index() {
        List<Url> urls = urlRepository.findAll();
        List<UrlDTO> result = new ArrayList<>();
        for (Url url : urls) {
            UrlDTO urlDTO = urlMapper.map(url);
            result.add(urlDTO);
        }
        return result;
    }

    public HashDTO create(DataDto data) throws MalformedURLException, Exception {
        System.out.println("dataUrl " + data.getUrlData().getUrl());
        System.out.println("dataHash " + data.getHashData());
        UrlCreateDTO urlCreateDTO = data.getUrlData();
        HashCreateDTO hashCreateDTO = data.getHashData();
        if (hashCreateDTO != null && urlCreateDTO != null) {

            if (urlCreateDTO != null && parse.validUrl(urlCreateDTO.getUrl()).equals(false)) {
                throw new MalformedURLException("Incorrect URL");
            }

            var currentUser = userUtils.getCurrentUser();
            long id = currentUser.getId();
            Optional<Hash> baseHash = hashRepository.findByName(hashCreateDTO.getName());
            if (baseHash.isPresent()) {
                throw new Exception("Hash is exist");
            }

            Optional<Url> url = urlRepository.findByUrl(urlCreateDTO.getUrl());

            if (url.isPresent()) {
                Optional<Hash> hash = hashRepository.findById(url.get().getId());
                if (hash.isPresent() && hashCreateDTO.getName().isEmpty()) {
                    return hashMapper.map(hash.get());
                } else {
                    HashUpdateDTO hashUpdateDTO = hashMapper.convertToHashUpdate(hashCreateDTO);
                    hashMapper.update(hashUpdateDTO, hash.get());
                    hashRepository.save(hash.get());
                    HashDTO dto = hashMapper.map(hash.get());
                    System.out.println("1111");
                    return dto;
                }

            } else {
                urlCreateDTO.setAssigneeId(id);
                Url url1 = urlMapper.map(urlCreateDTO);
                urlRepository.save(url1);
                Optional<Url> urlOriginal = urlRepository.findById(url1.getId());
                if (hashCreateDTO == null || hashCreateDTO.getName().isEmpty()) {
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
        return null;
    }



    public UrlDTO shortLink(String name) {
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


    public UrlDTO update(UrlUpdateDTO urlData, long id) {
        var url = urlRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Url not found"));
        urlMapper.update(urlData, url);
        urlRepository.save(url);
        var urlDTO = urlMapper.map(url);
        return urlDTO;

    }

    public void delete(long id) {
        var url = urlRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Url not found"));
        urlRepository.deleteById(id);
    }
}
