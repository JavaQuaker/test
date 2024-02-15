package com.example.testWork.repository;

import com.example.testWork.model.Hash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashGenerateRepository extends JpaRepository<Hash, Long> {
    @Override
    Optional<Hash> findById(Long aLong);
}
