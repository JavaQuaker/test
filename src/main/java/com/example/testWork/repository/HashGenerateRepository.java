package com.example.testWork.repository;


import com.example.testWork.model.HashGenerate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface HashGenerateRepository extends JpaRepository<HashGenerate, Long> {


    @Query(value = "SELECT * FROM stack e ORDER BY e.name LIMIT 1", nativeQuery = true)
     Optional<HashGenerate> findFirstByField();
}
