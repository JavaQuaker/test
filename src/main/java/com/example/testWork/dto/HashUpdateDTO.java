package com.example.testWork.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class HashUpdateDTO {
    @NotNull
    private JsonNullable<String> name;
}
