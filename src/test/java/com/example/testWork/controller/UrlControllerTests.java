package com.example.testWork.controller;

import com.example.testWork.dto.UrlCreateDTO;
import com.example.testWork.mapper.UrlMapper;
import com.example.testWork.model.Url;
import com.example.testWork.model.User;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.repository.UserRepository;
import com.example.testWork.util.ModelGenerator;
import com.example.testWork.util.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UrlMapper urlMapper;
    @Autowired
    private ModelGenerator modelGenerator;
    @Autowired
    private UserUtils userUtils;
    private Url testUrl;

    @Autowired
    private Faker faker;
    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void SetUp() {
        token = jwt().jwt(builder -> builder.subject("qwe@mail.ru"));
        var user = userRepository.findByEmail("qwe@mail.ru")
                .orElseThrow(() -> new RuntimeException("User not exist"));
        testUrl = Instancio.of(modelGenerator.getUrlModel()).create();
        testUrl.setAssignee(user);
        urlRepository.save(testUrl);
    }
    public void clear() {
        urlRepository.deleteById(testUrl.getId());
    }
    @Test
    public void testIndexUrl() throws Exception {
        mockMvc.perform(get("/api/urls").with(jwt()))
                .andExpect(status().isOk());

    }
    @Test
    public void testCreateUrl() throws Exception {

        UrlCreateDTO dto = new UrlCreateDTO();
        dto.setUrl("http://mail.ru");
        dto.setAssigneeId(1L);
        System.out.println("url " + dto.getUrl());
        System.out.println("Assign " + dto.getAssigneeId());
        var request = post("/api/urls").with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        Optional<Url> url = urlRepository.findByUrl(dto.getUrl());
        Assertions.assertThat(url).isNotNull();
        Assertions.assertThat(url.get().getUrl()).isEqualTo(dto.getUrl());
        Assertions.assertThat(url.get().getAssignee()).isEqualTo(dto.getAssigneeId());
        clear();
    }
}
