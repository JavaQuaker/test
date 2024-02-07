package com.example.testWork.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class UserUpdateDTO {
    @NotNull
    private JsonNullable<String> name;
    @Email
    private JsonNullable<String> email;
    @NotNull
    @Size(min = 3)
    JsonNullable<String> password;

}
