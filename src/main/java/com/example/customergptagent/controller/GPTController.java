package com.example.customergptagent.controller;

import com.example.customergptagent.factory.AIModelFactory;
import com.example.customergptagent.memory.InMemoryConversationStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GPTController {

    @Autowired
    private AIModelFactory aiModelFactory;

    @Autowired
    private InMemoryConversationStore conversationStore;

    /**
     * 处理与GPT对话的请求
     *
     * @param sessionId 用户会话ID，用于存储和检索对话历史
     * @param prompt 客户输入的提示信息
     * @param modelType 使用的AI模型类型（例如 GPT 或 Other）
     * @return Mono<String> GPT生成的响应
     */
    @GetMapping("/ai/chat")
    public Mono<String> chatWithAI(@RequestParam String sessionId,
                                   @RequestParam String prompt,
                                   @RequestParam String modelType) {
        // 获取相应的AI模型
        AIModelService aiModel = aiModelFactory.getAIModel(modelType);

        // 调用AI模型生成响应
        return aiModel.generateResponse(prompt).flatMap(response -> {
            // 保存用户输入和GPT响应到对话历史
            conversationStore.saveConversation(sessionId, "User", prompt);
            conversationStore.saveConversation(sessionId, "AI", response);

            // 判断对话是否已经达到上限，超过则结束会话
            if (conversationStore.getConversation(sessionId).getMessages().size() >= 6) {
                conversationStore.removeConversation(sessionId);
            }

            return Mono.just(response); // 返回GPT响应
        });
    }
}
