package com.example.customergptagent.service;

import com.example.customergptagent.memory.InMemoryConversationStore;
import com.example.customergptagent.repository.PromptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ConversationService {

    @Autowired
    private InMemoryConversationStore conversationStore;

    @Autowired
    private AIModelService aiModelService;

    @Autowired
    private PromptRepository promptRepository;

    public void triggerConversation() {
        // 从 PromptRepository 中随机选择一个客户智能体的 prompt
        String randomPrompt = promptRepository.getRandomPromptForCustomerAgent();

        // 假设从数据库中获取商品信息
        String productInfo = "Sample product info";

        // 组合 prompt 和商品信息，发起 GPT 请求
        String promptWithProductInfo = randomPrompt + "\n\nProduct Info:\n" + productInfo;
        String sessionId = generateRandomSessionId();

        // 发起对话，并将其存储在内存中
        aiModelService.generateResponse(promptWithProductInfo).subscribe(response -> {
            conversationStore.saveConversation(sessionId, "Customer", response);
            // 将消息发送到第三方聊天接口（假设这里有逻辑）
            sendMessageToChatInterface(response);
        });
    }

    // 简单生成 sessionId 的方法（可以用 UUID 替换）
    private String generateRandomSessionId() {
        return "session-" + new Random().nextInt(10000);
    }

    // 假设发送消息到聊天接口的逻辑
    private void sendMessageToChatInterface(String message) {
        // 这里可以集成聊天接口的发送逻辑
        System.out.println("Message sent to chat interface: " + message);
    }
}
