package com.example.service;

import com.example.mapper.ShopMapper;
import com.example.model.BusShop;
import com.example.model.BusShopWeeklyTaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
public class ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private GPTConversationService gptConversationService;


    public void processWeeklyTask() {
        // 查询每周店铺数据
        List<BusShop> shopList = shopMapper.findWeeklyShopData();

        // 获取当前周数
        int currentWeek = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear());

        for (BusShop shop : shopList) {
            // 确保该店铺数据在本周有更新
            if (!isUpdatedThisWeek(shop.getUpdateTime())) {
                continue;
            }

            // 检查是否已有记录，不存在则插入新记录
            int exists = shopMapper.checkWeeklyTaskLogExists(shop.getTenantId(), currentWeek);
            if (exists == 0) {
                shopMapper.insertWeeklyTaskLog(shop.getTenantId(), currentWeek, 0, 0, 0);
                System.out.println("新增任务记录：租户ID = " + shop.getTenantId() + ", 周数 = " + currentWeek);
            }

            // 获取最新的任务日志记录
            BusShopWeeklyTaskLog taskLog = shopMapper.findWeeklyTaskLog(shop.getTenantId(), currentWeek);

            // 如果任务未完成，则执行一次下单逻辑并更新任务记录
            if (!isTaskComplete(taskLog, shop)) {
                performAutoOrder(shop);

                // 更新任务记录
                shopMapper.updateTaskLog(shop.getTenantId(), currentWeek,
                        taskLog.getGptqty() + 1,
                        taskLog.getVipgptqty() + 1,
                        taskLog.getOrderqty() + 1);

                System.out.println("任务记录已更新：租户ID = " + shop.getTenantId() + ", 周数 = " + currentWeek);
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
        if (updateTime == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        LocalDate updateDate = updateTime.toLocalDate();
        return !updateDate.isBefore(startOfWeek) && !updateDate.isAfter(endOfWeek);
    }

    private void performAutoOrder(BusShop shop) {
        // 执行自动下单逻辑
        System.out.println("执行自动下单，租户ID：" + shop.getTenantId());
        //gptConversationService.handleConversation();
    }
}
