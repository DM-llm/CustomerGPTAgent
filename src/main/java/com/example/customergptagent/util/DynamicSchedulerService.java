package com.example.customergptagent.util;

import com.example.customergptagent.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicSchedulerService {

    @Autowired
    private ConversationService conversationService;

    private final ThreadPoolTaskScheduler taskScheduler;
    private ScheduledFuture<?> futureTask;

    public DynamicSchedulerService() {
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.initialize();
    }

    // 启动定时任务
    public void startScheduledTask(long delay) {
        stopScheduledTask(); // 先停止之前的任务
        futureTask = taskScheduler.scheduleWithFixedDelay(
                this::triggerConversation,
                delay
        );
    }

    // 停止定时任务
    public void stopScheduledTask() {
        if (futureTask != null && !futureTask.isCancelled()) {
            futureTask.cancel(true);
        }
    }

    // 触发对话
    private void triggerConversation() {
        conversationService.triggerConversation();
    }
}
