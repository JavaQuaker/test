package com.example.testWork.controller;

import com.example.testWork.dto.UserCreateDTO;
import com.example.testWork.dto.UserUpdateDTO;
import com.example.testWork.model.User;
import com.example.testWork.repository.UrlRepository;
import com.example.testWork.repository.UserRepository;
import com.example.testWork.util.JWTUtils;
import com.example.testWork.util.ModelGenerator;
import com.example.testWork.util.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.instancio.Instancio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MockMvc;
import com.example.testWork.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserUtils userUtils;
    private User testUser;
    @Autowired
    private ModelGenerator modelGenerator;
    @Autowired
    private Faker faker;
    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setUp() {
        token = jwt().jwt(builder -> builder.subject("qwe@mail.ru"));
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);
    }
    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/api/users").with(jwt()))
                .andExpect(status().isOk());
    }
    @Test
    public void testCreateUser() throws Exception {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setName(faker.name().name());
        dto.setEmail(faker.internet().emailAddress());
        dto.setPassword(faker.internet().password(3, 100));

        var request = post("/api/users").with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var user = userRepository.findByEmail(testUser.getEmail()).get();

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getName()).isEqualTo(testUser.getName());
        Assertions.assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
    }
    @Test
    public void testUpdateUser() throws Exception {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setName(JsonNullable.of(faker.name().name()));
        dto.setEmail(JsonNullable.of(faker.internet().emailAddress()));
        dto.setPassword(JsonNullable.of(faker.internet().password(3, 12)));
        var request = put("/api/users/{id}", testUser.getId()).with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isOk());
        testUser = userRepository.findById(testUser.getId()).get();
        assertThat(testUser.getName()).isEqualTo(dto.getName().get());
        assertThat(testUser.getPassword()).isEqualTo(dto.getPassword().get());
        assertThat(testUser.getPassword()).isEqualTo(dto.getPassword().get());
    }
    @Test
    public void testShowUser() throws Exception {
        var request = get("/api/users/{id}", testUser.getId()).with(token);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("name").isEqualTo(testUser.getName()),
                v -> v.node("email").isEqualTo(testUser.getEmail())
        );
    }
    @Test
    public void testDeleteUser() throws Exception {
        token = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
        var request = delete("/api/users/{id}", testUser.getId()).with(token);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        assertThat(userRepository.existsById(testUser.getId())).isFalse();
    }

}
