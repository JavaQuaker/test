package com.example.testWork.mapper;


import com.example.testWork.dto.HashCreateDTO;
import com.example.testWork.dto.HashDTO;
import com.example.testWork.dto.HashUpdateDTO;
import com.example.testWork.model.Hash;
import com.example.testWork.repository.HashRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingTarget;
import org.openapitools.jackson.nullable.JsonNullable;
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
