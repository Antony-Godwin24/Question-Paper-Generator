package com.example.qpg.service;

import com.example.qpg.model.*;
import com.example.qpg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaperService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionPaperRepository paperRepository;

    @Autowired
    private GenerationHistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeminiService geminiService;

    @Transactional
    public QuestionPaper generatePaper(String username, String subject, String difficulty, int count, String title) {
        System.out.println("Generating paper for user: " + username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Generate Questions via Gemini
        List<Question> questions = geminiService.generateQuestions(subject, difficulty, count);
        
        if (questions.isEmpty()) {
            throw new RuntimeException("Failed to generate questions. Please try again or check API configuration.");
        }
        
        // 2. Save Questions
        questions = questionRepository.saveAll(questions);

        // 3. Create Paper
        QuestionPaper paper = new QuestionPaper();
        paper.setTitle(title);
        paper.setSubject(subject);
        paper.setCreatedBy(user);
        paper.setCreatedAt(LocalDateTime.now());
        paper.setQuestions(questions);

        paper = paperRepository.save(paper);
        System.out.println("Paper saved with ID: " + paper.getId());

        // 4. Save History
        GenerationHistory history = new GenerationHistory();
        history.setQuestionPaper(paper);
        history.setGeneratedBy(user);
        history.setTimestamp(LocalDateTime.now());
        historyRepository.save(history);
        System.out.println("History saved for paper ID: " + paper.getId());

        return paper;
    }

    public List<QuestionPaper> getAllPapers() {
        return paperRepository.findAll();
    }

    public List<QuestionPaper> getMyPapers(String username) {
        System.out.println("Fetching papers for user: " + username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<QuestionPaper> papers = paperRepository.findByCreatedBy(user);
        System.out.println("Found " + papers.size() + " papers for user " + username);
        return papers;
    }

    public QuestionPaper getPaperById(String id) {
        return paperRepository.findById(id).orElseThrow(() -> new RuntimeException("Paper not found"));
    }
}
