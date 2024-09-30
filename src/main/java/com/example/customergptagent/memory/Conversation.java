package com.example.customergptagent.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 会话类，用于保存多轮对话
 */
public class Conversation {

    // 保存每轮的对话记录，List 中每个元素是一条对话消息
    private final List<Message> messages = new ArrayList<>();

    /**
     * 添加对话记录
     *
     * @param role 角色（User 或 AI）
     * @param content 对话内容
     */
    public void addMessage(String role, String content) {
        messages.add(new Message(role, content));
    }

    /**
     * 获取所有对话记录
     *
     * @return 对话记录的List
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * 内部类，表示单条对话消息
     */
    public static class Message {
        private final String role;  // 角色（User 或 AI）
        private final String content;  // 对话内容

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }
    }
}
