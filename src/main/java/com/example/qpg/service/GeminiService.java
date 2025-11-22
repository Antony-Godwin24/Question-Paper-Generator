package com.example.qpg.service;

import com.example.qpg.model.Question;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Question> generateQuestions(String subject, String difficulty, int count) {
        String prompt = String.format("Generate %d %s level questions for the subject '%s'. " +
                "Format the output as a JSON array of objects with fields: 'content' (the question text), " +
                "'type' (e.g., MCQ, Descriptive), 'difficulty' (Easy, Medium, Hard), and 'subject'. " +
                "Do not include any markdown formatting or code blocks, just the raw JSON array.", 
                count, difficulty, subject);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}", prompt);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl + "?key=" + apiKey,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            System.out.println("Gemini API Response Code: " + response.getStatusCode());
            System.out.println("Gemini API Raw Body: " + response.getBody());

            return parseGeminiResponse(response.getBody(), subject, difficulty);
        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Question> parseGeminiResponse(String responseBody, String subject, String difficulty) {
        List<Question> questions = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                String text = candidates.get(0).path("content").path("parts").get(0).path("text").asText();
                
                // Use regex to find the JSON array
                Pattern pattern = Pattern.compile("\\[.*\\]", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(text);
                
                if (matcher.find()) {
                    String jsonArray = matcher.group();
                    JsonNode questionsNode = objectMapper.readTree(jsonArray);
                    
                    if (questionsNode.isArray()) {
                        for (JsonNode node : questionsNode) {
                            Question q = new Question();
                            q.setContent(node.path("content").asText());
                            q.setType(node.path("type").asText("Descriptive"));
                            q.setDifficulty(node.path("difficulty").asText(difficulty));
                            q.setSubject(node.path("subject").asText(subject));
                            questions.add(q);
                        }
                    }
                } else {
                    System.err.println("No JSON array found in Gemini response: " + text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
}
