package com.example.qpg.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;

@Document(collection = "generation_history")
public class GenerationHistory {
    @Id
    private String id;

    @DBRef
    private QuestionPaper questionPaper;

    @DBRef
    private User generatedBy;

    private LocalDateTime timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuestionPaper getQuestionPaper() {
        return questionPaper;
    }

    public void setQuestionPaper(QuestionPaper questionPaper) {
        this.questionPaper = questionPaper;
    }

    public User getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(User generatedBy) {
        this.generatedBy = generatedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
