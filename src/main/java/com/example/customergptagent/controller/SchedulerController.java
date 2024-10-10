package com.example.customergptagent.controller;

import com.example.customergptagent.util.DynamicSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerController {

    @Autowired
    private DynamicSchedulerService schedulerService;

    // 手动启动定时任务
    @PostMapping("/scheduler/start")
    public String startScheduler() {
        schedulerService.triggerManually();  // 手动触发
        return "Scheduler triggered manually!";
    }

}
