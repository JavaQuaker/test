package com.example.testWork.repository;

import com.example.testWork.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

//    Optional<Url> findByOriginalUrl(String originalUrl);
    Optional<Url> findByUrl(String url);
}
