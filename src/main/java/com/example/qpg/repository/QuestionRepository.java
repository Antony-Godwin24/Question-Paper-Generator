package com.example.qpg.repository;

import com.example.qpg.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findBySubject(String subject);
}
