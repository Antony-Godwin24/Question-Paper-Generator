package com.example.qpg.repository;

import com.example.qpg.model.QuestionPaper;
import com.example.qpg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long> {
    List<QuestionPaper> findByCreatedBy(User user);
}
