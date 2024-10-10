package com.example.customergptagent.util;

import com.example.customergptagent.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DynamicSchedulerService {

    @Autowired
    private ConversationService conversationService;

    // 手动触发方法
    public void triggerManually() {
        // 调用对话服务开始执行定时任务的逻辑
        conversationService.startConversation();
    }

    // 每天早上10点触发任务
    @Scheduled(cron = "0 0 10 * * ?")
    public void triggerScheduledTask() {
        // 定时自动触发任务
        conversationService.startConversation();
    }
}
