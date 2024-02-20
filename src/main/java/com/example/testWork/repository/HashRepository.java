package com.example.testWork.repository;

import com.example.testWork.model.Hash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface HashRepository extends JpaRepository<Hash, Long> {
    @Override
    Optional<Hash> findById(Long aLong);
    Optional<Hash> findByName(String name);
}
