package com.example.testWork.mapper;


import com.example.testWork.dto.HashCreateDTO;
import com.example.testWork.dto.HashDTO;
import com.example.testWork.dto.HashUpdateDTO;
import com.example.testWork.model.Hash;
import com.example.testWork.repository.HashRepository;
import org.mapstruct.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

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
//    @Mapping(target = "userId", source = "user.id")
    public abstract HashDTO map(Hash model);
    @Mapping(target = "nameHash", source = "nameHashId")
//    @Mapping(target = "user", source = "userId")
    public abstract Hash map(HashCreateDTO dto);
    public abstract void update(HashUpdateDTO hashUpdateDTO, @MappingTarget Hash model);

    public HashUpdateDTO convertToHashUpdate(HashCreateDTO hashCreateDTO) {
        if (hashCreateDTO != null || !hashCreateDTO.getName().isEmpty()) {
            String result = hashCreateDTO.getName();
            HashUpdateDTO hashUpdateDTO = new HashUpdateDTO();
            hashUpdateDTO.setName(JsonNullable.of(result));
            return hashUpdateDTO;
        }
        return null;
    }
}
