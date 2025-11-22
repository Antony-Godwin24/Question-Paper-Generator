package com.example.qpg.controller;

import com.example.qpg.model.QuestionPaper;
import com.example.qpg.service.PaperService;
import com.example.qpg.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/papers")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<QuestionPaper> generatePaper(@RequestBody Map<String, Object> payload, Authentication authentication) {
        String subject = (String) payload.get("subject");
        String difficulty = (String) payload.get("difficulty");
        int count = Integer.parseInt(payload.get("count").toString());
        String title = (String) payload.get("title");
        
        QuestionPaper paper = paperService.generatePaper(authentication.getName(), subject, difficulty, count, title);
        return ResponseEntity.ok(paper);
    }

    @GetMapping("/my-history")
    public ResponseEntity<List<QuestionPaper>> getMyHistory(Authentication authentication) {
        return ResponseEntity.ok(paperService.getMyPapers(authentication.getName()));
    }

    @GetMapping("/all-history")
    public ResponseEntity<List<QuestionPaper>> getAllHistory() {
        return ResponseEntity.ok(paperService.getAllPapers());
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        QuestionPaper paper = paperService.getPaperById(id);
        byte[] pdfBytes = pdfService.generatePdf(paper);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=paper_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
