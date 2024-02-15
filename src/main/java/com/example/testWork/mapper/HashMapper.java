package com.example.testWork.mapper;


import com.example.testWork.dto.HashCreateDTO;
import com.example.testWork.dto.HashDTO;
import com.example.testWork.model.Hash;
import com.example.testWork.repository.HashRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public abstract class HashMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HashRepository hashRepository;
    @Mapping(target = "nameHashId", source = "nameHash.id")
    public abstract HashDTO map(Hash model);
    @Mapping(target = "nameHash", source = "nameHashId")
    public abstract Hash map(HashCreateDTO dto);

}
