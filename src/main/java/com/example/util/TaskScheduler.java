package com.example.util;


import com.example.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduler {

    @Autowired
    private ShopService shopService;

    // 每周触发一次任务执行
    @Scheduled(cron = "0 0 0 ? * MON")  // 每周一凌晨执行
    public void scheduleWeeklyTask() {
        shopService.processWeeklyTask();
    }
}
