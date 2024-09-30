package com.example.customergptagent.memory;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class InMemoryConversationStore {

    private final Map<String, Conversation> conversationMap = new HashMap<>();

    // 保存对话
    public void saveConversation(String sessionId, String role, String message) {
        Objects.requireNonNull(sessionId, "sessionId cannot be null");
        Objects.requireNonNull(role, "role cannot be null");
        Objects.requireNonNull(message, "message cannot be null");

        Conversation conversation = conversationMap.computeIfAbsent(sessionId, k -> new Conversation());
        conversation.addMessage(role, message);
    }

    // 获取对话
    public Conversation getConversation(String sessionId) {
        Objects.requireNonNull(sessionId, "sessionId cannot be null");
        return conversationMap.get(sessionId);
    }

    // 删除对话
    public void removeConversation(String sessionId) {
        Objects.requireNonNull(sessionId, "sessionId cannot be null");
        conversationMap.remove(sessionId);
    }
}
