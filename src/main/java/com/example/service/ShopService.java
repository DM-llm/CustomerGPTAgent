package com.example.service;

import com.example.mapper.ShopMapper;
import com.example.model.BusShop;
import com.example.model.BusShopWeeklyTaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
public class ShopService {

    @Autowired
    private ShopMapper shopMapper;

    public void processWeeklyTask() {
        // 查询每周店铺数据
        List<BusShop> shopList = shopMapper.findWeeklyShopData();

        // 获取当前周数
        int currentWeek = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear());

        for (BusShop shop : shopList) {
            if (!isUpdatedThisWeek(shop.getUpdateTime())) {
                continue;
            }

            int exists = shopMapper.checkWeeklyTaskLogExists(shop.getTenantId(), currentWeek);
            if (exists == 0) {
                shopMapper.insertWeeklyTaskLog(shop.getTenantId(), currentWeek, 0, 0, 0);
            }

            BusShopWeeklyTaskLog taskLog = shopMapper.findWeeklyTaskLog(shop.getTenantId(), currentWeek);

            if (!isTaskComplete(taskLog, shop)) {
                performAutoOrder(shop);

                // 更新任务记录
                shopMapper.updateTaskLog(shop.getTenantId(), currentWeek,
                        taskLog.getGptqty() + 1,
                        taskLog.getVipgptqty() + 1,
                        taskLog.getOrderqty() + 1);

                // 每次更新后重新查询 taskLog，获取最新数据
                taskLog = shopMapper.findWeeklyTaskLog(shop.getTenantId(), currentWeek);
            }
        }
    }

    private boolean isTaskComplete(BusShopWeeklyTaskLog taskLog, BusShop shop) {
        // 检查任务是否达成要求
        return taskLog.getGptqty() >= shop.getGptqty() &&
                taskLog.getVipgptqty() >= shop.getVipgptqty() &&
                taskLog.getOrderqty() >= shop.getOrderqty();
    }

    private boolean isUpdatedThisWeek(LocalDateTime updateTime) {
        // 检查更新时间是否为本周
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek());
        return !updateTime.toLocalDate().isBefore(startOfWeek);
    }

    private void performAutoOrder(BusShop shop) {
        // 执行自动下单逻辑
        System.out.println("执行自动下单，租户ID：" + shop.getTenantId());
    }
}
