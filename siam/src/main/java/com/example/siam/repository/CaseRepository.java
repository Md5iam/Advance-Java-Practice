package com.example.siam.repository;

import com.example.siam.model.Case;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CaseRepository extends MongoRepository<Case , String> {
    Case getById(String id);
    Optional<Case> findById(String id);
}
