package com.example.customergptagent.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GPTService implements AIModelService {

    private final WebClient webClient;

    @Value("${gpt.api.url}")
    private String gptApiUrl;

    @Value("${gpt.api.key}")
    private String gptApiKey;

    public GPTService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(gptApiUrl).build();
    }

    @Override
    public Mono<String> generateResponse(String prompt) {
        JsonNode requestBody = createRequestBody(prompt);

        return webClient.post()
                .uri("/v1/completions")
                .header("Authorization", "Bearer " + gptApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> response.get("choices").get(0).get("text").asText())
                .onErrorResume(e -> Mono.just("Error in GPT response: " + e.getMessage()));
    }

    private JsonNode createRequestBody(String prompt) {
        return new com.fasterxml.jackson.databind.ObjectMapper().createObjectNode()
                .put("model", "text-davinci-003")
                .put("prompt", prompt)
                .put("max_tokens", 1000);
    }
}
