package com.example.customergptagent.controller;

import com.example.customergptagent.util.DynamicSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduler")
public class DynamicSchedulerController {

    @Autowired
    private DynamicSchedulerService dynamicSchedulerService;

    // 动态启动定时任务，允许用户指定延迟（毫秒）
    @PostMapping("/start")
    public String startSchedulerWithCustomDelay(@RequestParam long delay) {
        dynamicSchedulerService.startScheduledTask(delay);
        return "Scheduler started with delay: " + delay + "ms";
    }

    // 停止定时任务
    @PostMapping("/stop")
    public String stopScheduler() {
        dynamicSchedulerService.stopScheduledTask();
        return "Scheduler stopped.";
    }
}
