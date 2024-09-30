package com.example.customergptagent.controller;

import com.example.customergptagent.util.SchedulerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerController {

    @Autowired
    private SchedulerConfig schedulerConfig;

    // 手动启动定时任务，触发与 GPT 的对话
    @GetMapping("/scheduler/start")
    public String startSchedulerManually() {
        schedulerConfig.startConversation();
        return "Scheduler started manually!";
    }
}
