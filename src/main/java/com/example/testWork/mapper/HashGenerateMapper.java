package com.example.testWork.mapper;

import com.example.testWork.dto.HashGenerateCreateDTO;
import com.example.testWork.dto.HashGenerateDTO;
import com.example.testWork.model.HashGenerate;
import com.example.testWork.repository.HashGenerateRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public abstract class HashGenerateMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HashGenerateRepository hashGenerateRepository;


    @Mapping(target = "name", source = "name")
    public abstract HashGenerateDTO map(HashGenerate model);
    @Mapping(target = "name", source = "name")
    public abstract HashGenerate map(HashGenerateCreateDTO dto);

}
