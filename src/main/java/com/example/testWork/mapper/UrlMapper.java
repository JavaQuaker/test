package com.example.testWork.mapper;

import com.example.testWork.dto.UrlCreateDTO;
import com.example.testWork.dto.UrlDTO;
import com.example.testWork.dto.UrlUpdateDTO;
import com.example.testWork.model.Url;
import com.example.testWork.repository.UrlRepository;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UrlMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UrlRepository urlRepository;

    @Mapping(target = "assigneeId", source = "assignee.id")
//    @Mapping(target = "numberId", source = "number.id")
    public abstract UrlDTO map(Url model);
    @Mapping(target = "assignee", source = "assigneeId")
//    @Mapping(target = "number", source = "numberId")
    public abstract Url map(UrlCreateDTO dto);
    @Mapping(target = "assignee", source = "assigneeId")
//    @Mapping(target = "number", source = "numberId")
    public abstract void update(UrlUpdateDTO dto, @MappingTarget Url model);
}
