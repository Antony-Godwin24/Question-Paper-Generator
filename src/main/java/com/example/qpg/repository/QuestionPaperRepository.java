package com.example.qpg.repository;

import com.example.qpg.model.QuestionPaper;
import com.example.qpg.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface QuestionPaperRepository extends MongoRepository<QuestionPaper, String> {
    List<QuestionPaper> findByCreatedBy(User user);
}
