package com.example.testWork.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class UrlUpdateDTO {
    @NotNull
    private JsonNullable<Long> assigneeId;
//    @NotNull
//    JsonNullable<Long> numberId;
    @NotBlank
    private JsonNullable<String> url;

}
