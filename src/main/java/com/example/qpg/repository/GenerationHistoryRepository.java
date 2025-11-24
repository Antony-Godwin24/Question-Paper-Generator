package com.example.qpg.repository;

import com.example.qpg.model.GenerationHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenerationHistoryRepository extends MongoRepository<GenerationHistory, String> {
}
