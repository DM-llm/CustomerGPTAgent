package com.example.customergptagent.service;

import reactor.core.publisher.Mono;

public interface AIModelService {
    Mono<String> generateResponse(String prompt);
}
