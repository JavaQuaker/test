package com.example.testWork.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Locale;

@Getter
@Setter
public class UserCreateDTO {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotNull
    @Size(min = 3)
    private String password;

    private LocalDate createdDate;
}
