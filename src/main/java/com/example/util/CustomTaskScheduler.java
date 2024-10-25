package com.example.util;

import com.example.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CustomTaskScheduler {

    @Autowired
    private ShopService shopService;

    // 每10分钟触发一次任务执行
    @Scheduled(cron = "0 */10 * * * ?")
    public void scheduleTask() {
        shopService.processWeeklyTask();
    }
}
