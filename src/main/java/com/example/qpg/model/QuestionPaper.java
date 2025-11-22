package com.example.qpg.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "question_papers")
public class QuestionPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subject;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "paper_questions",
        joinColumns = @JoinColumn(name = "paper_id"),
        inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
