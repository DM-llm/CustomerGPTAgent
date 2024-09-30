package com.example.customergptagent.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OtherAIModelService implements AIModelService {

    @Override
    public Mono<String> generateResponse(String prompt) {
        // 模拟其他模型的响应逻辑
        return Mono.just("Response from Other AI Model for prompt: " + prompt);
    }
}
