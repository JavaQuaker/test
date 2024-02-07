package com.example.testWork.mapper;

import com.example.testWork.model.BaseEntity;
import jakarta.persistence.EntityManager;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ReferenceMapper {
    @Autowired
    private EntityManager entityManager;

    public <T extends BaseEntity> T toEntity(Long id, @TargetType Class<T> entityClass) {
        return id != null ? entityManager.find(entityClass, id) : null;
    }
}
