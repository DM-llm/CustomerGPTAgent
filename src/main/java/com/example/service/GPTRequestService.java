package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Service
public class GPTRequestService {

    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions"; // 替换为 GPT 的具体 API 端点
    private static final String API_KEY = "your-openai-api-key"; // 替换为你的 OpenAI API 密钥

    public String sendRequestToGPT(Map<String, Object> requestBody) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(GPT_API_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}
