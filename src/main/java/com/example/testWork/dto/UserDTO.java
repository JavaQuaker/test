package com.example.testWork.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private LocalDate createdDate;
}
