package com.example.customergptagent.util;

import com.example.customergptagent.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerConfig {

    @Autowired
    private ConversationService conversationService;

    // 每隔5分钟执行一次
    @Scheduled(fixedRate = 300000) // 300000毫秒 = 5分钟
    public void autoStartConversation() {
        startConversation();
    }

    // 手动调用对话启动逻辑
    public void startConversation() {
        conversationService.triggerConversation();
    }
}
