package com.example.customergptagent.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class GPTServiceTest {

    @Autowired
    private GPTService gptService;

    @Test
    public void testGenerateResponse() {
        String prompt = "Hello, how are you?";

        // 假设模型会返回以下文本
        Mono<String> response = gptService.generateResponse(prompt);

        StepVerifier.create(response)
                .expectNextMatches(result -> result.contains("Hello"))
                .verifyComplete();
    }
}
