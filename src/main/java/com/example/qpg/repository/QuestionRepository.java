package com.example.qpg.repository;

import com.example.qpg.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySubject(String subject);
}
