package com.example.testWork.controller;

import com.example.testWork.dto.DataDto;
import com.example.testWork.dto.HashCreateDTO;
import com.example.testWork.dto.UrlCreateDTO;
import com.example.testWork.exception.ResourceNotFoundException;
import com.example.testWork.mapper.HashMapper;
import com.example.testWork.mapper.UrlMapper;
import com.example.testWork.model.Hash;
import com.example.testWork.model.Url;
import com.example.testWork.repository.HashRepository;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.repository.UserRepository;
import com.example.testWork.service.ParseClass;
import com.example.testWork.service.StackManagement;
import com.example.testWork.util.ModelGenerator;
import com.example.testWork.util.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import java.net.MalformedURLException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private HashRepository hashRepository;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UrlMapper urlMapper;
    @Autowired
    private ModelGenerator modelGenerator;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private StackManagement stackManagement;
    @Autowired
    private HashMapper hashMapper;
    @Autowired
    private ParseClass parse;
    private Url testUrl;
    private Hash testHash;

    @Autowired
    private Faker faker;
    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setUp() {
        token = jwt().jwt(builder -> builder.subject("qwe@mail.ru"));
        var user = userRepository.findByEmail("qwe@mail.ru")
                .orElseThrow(() -> new RuntimeException("User not exist"));
        testUrl = Instancio.of(modelGenerator.getUrlModel()).create();
        testUrl.setAssignee(user);
        urlRepository.save(testUrl);
        HashCreateDTO hashCreateDTO = new HashCreateDTO();
        hashCreateDTO = stackManagement.pop();
        Hash hash = hashMapper.map(hashCreateDTO);
        hashRepository.save(hash);

    }
    public void clear() {
        urlRepository.deleteAll();
    }
    @Test
    public void testIndexUrl() throws Exception {
        mockMvc.perform(get("/api/urls").with(jwt()))
                .andExpect(status().isOk());

    }
    @Test
    public void testCreateUrlWithOutHash() throws Exception {

        DataDto dto = new DataDto();
        UrlCreateDTO urlCreateDTO = new UrlCreateDTO();
        HashCreateDTO hashCreateDTO = new HashCreateDTO();
        urlCreateDTO.setUrl("http://mail.ru");
        urlCreateDTO.setAssigneeId(userUtils.getTestUser().getId());
        hashCreateDTO.setName("");
        dto.setUrlData(urlCreateDTO);
        dto.setHashData(hashCreateDTO);

        var request = post("/api/urls").with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        Url url = urlRepository.findByUrl(dto.getUrlData().getUrl())
                        .orElseThrow(() -> new ResourceNotFoundException("url not found"));

        assertThat(url).isNotNull();
        assertThat(url.getUrl()).isEqualTo(dto.getUrlData().getUrl());
        assertThat(url.getAssignee().getId()).isEqualTo(dto.getUrlData().getAssigneeId());
        Hash baseHash = hashRepository.findById(url.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("hash not found"));

        assertThat(baseHash.getNameHash().getId()).isEqualTo(url.getId());
    }
    @Test
    public void testCreateUrlWithHash() throws Exception {
        DataDto dto = new DataDto();
        UrlCreateDTO urlCreateDTO = new UrlCreateDTO();
        HashCreateDTO hashCreateDTO = new HashCreateDTO();
        urlCreateDTO.setUrl("http://mail.ru");
        urlCreateDTO.setAssigneeId(userUtils.getTestUser().getId());
        hashCreateDTO.setName("AsdfgH");
        dto.setUrlData(urlCreateDTO);
        dto.setHashData(hashCreateDTO);

        var request = post("/api/urls").with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        Url url = urlRepository.findByUrl(dto.getUrlData().getUrl())
                .orElseThrow(() -> new ResourceNotFoundException("url not found"));
        assertThat(url).isNotNull();
        assertThat(url.getUrl()).isEqualTo("http://mail.ru");
        assertThat(url.getAssignee().getId()).isEqualTo(dto.getUrlData().getAssigneeId());

        Hash baseHash = hashRepository.findById(url.getId())
                .orElseThrow(() -> new ResourceNotFoundException("hash not found"));
        assertThat(baseHash.getName()).isEqualTo(dto.getHashData().getName());
    }
    @Test
    public void testCreateDoubleUrl() throws Exception {
        DataDto dto = new DataDto();
        UrlCreateDTO urlCreateDTO = new UrlCreateDTO();
        HashCreateDTO hashCreateDTO = new HashCreateDTO();
        urlCreateDTO.setUrl("http://mail.ru");
        urlCreateDTO.setAssigneeId(userUtils.getTestUser().getId());
        hashCreateDTO.setName("newHash");
        dto.setUrlData(urlCreateDTO);
        dto.setHashData(hashCreateDTO);

        var request = post("/api/urls").with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        Url url = urlRepository.findByUrl(dto.getUrlData().getUrl())
                .orElseThrow(() -> new ResourceNotFoundException("url not found"));

        assertThat(url).isNotNull();
        assertThat(url.getUrl()).isEqualTo("http://mail.ru");
        assertThat(url.getId()).isEqualTo(2);

        Hash baseHash = hashRepository.findById(url.getId())
                .orElseThrow(() -> new ResourceNotFoundException("hash not found"));
        assertThat(baseHash.getName()).isEqualTo("newHash");

    }
    @Test
    public void whenExceptionThrownAssertionSucceeds() {
        Exception exception = assertThrows(MalformedURLException.class, () -> {
            DataDto dto = new DataDto();
            UrlCreateDTO urlCreateDTO = new UrlCreateDTO();
            HashCreateDTO hashCreateDTO = new HashCreateDTO();
            urlCreateDTO.setUrl("ya.ru");
            urlCreateDTO.setAssigneeId(userUtils.getTestUser().getId());
            hashCreateDTO.setName("");
            dto.setUrlData(urlCreateDTO);
            dto.setHashData(hashCreateDTO);

            var request = post("/api/urls").with(token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsString(dto));
            mockMvc.perform(request)
                    .andExpect(status().isCreated());
        });

        String expectedMessage = "Incorrect URL";
        String actualMessage = exception.getMessage();
        assertTrue(expectedMessage.contains(actualMessage));
    }
}
