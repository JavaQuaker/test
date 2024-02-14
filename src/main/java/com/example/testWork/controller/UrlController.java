package com.example.testWork.controller;

import com.example.testWork.dto.UrlCreateDTO;
import com.example.testWork.dto.UrlDTO;
import com.example.testWork.dto.UrlUpdateDTO;
import com.example.testWork.exception.ResourceNotFoundException;
import com.example.testWork.mapper.UrlMapper;
import com.example.testWork.model.Url;
import com.example.testWork.repository.UrlRepository;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/urls")
public class UrlController {
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UrlMapper urlMapper;

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
    public UrlDTO create(@RequestBody UrlCreateDTO urlData) {
        Url url = urlMapper.map(urlData);
        urlRepository.save(url);
        UrlDTO urlDTO = urlMapper.map(url);
        return urlDTO;
    }
    @GetMapping(path = "/{id}")
    public UrlDTO show(@Valid @PathVariable long id) {
        var url = urlRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Url not found"));
        return urlMapper.map(url);
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
