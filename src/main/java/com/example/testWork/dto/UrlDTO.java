package com.example.testWork.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UrlDTO {
    private long id;
    private String url;
    private long assigneeId;
    private LocalDate createdAt;
}
