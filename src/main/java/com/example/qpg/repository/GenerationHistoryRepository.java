package com.example.qpg.repository;

import com.example.qpg.model.GenerationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationHistoryRepository extends JpaRepository<GenerationHistory, Long> {
}
