package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final String API_KEY = ""; // 替换为你的 OpenAI API 密钥

    public String sendRequestToGPT(Map<String, Object> requestBody) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(GPT_API_URL, HttpMethod.POST, entity, String.class);

        // 解析 GPT 的回复
        String gptResponse = extractGPTResponse(response.getBody());
        return gptResponse;
    }

    // 解析 GPT API 的返回值，提取其中的回复内容
    private String extractGPTResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            // 提取 GPT 回复内容，choices[0].message.content
            return jsonNode.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to parse GPT response";
        }
    }
}
