package com.example.customergptagent.service;


import com.example.customergptagent.model.BusShop;
import com.example.customergptagent.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            // 记录每周任务执行情况到冗余表
            shopMapper.insertWeeklyTaskLog(shop.getId(), currentWeek, shop.getGptqty(), shop.getVipgptqty(), shop.getOrderqty());

            // 动态计算阈值
            int gptThreshold = calculateGptThreshold(shop);
            int vipThreshold = calculateVipGptThreshold(shop);
            int orderThreshold = calculateOrderThreshold(shop);

            // 检查是否有任何字段未达标
            int thresholdMet = shopMapper.checkThresholdMet(shop.getId(), gptThreshold, vipThreshold, orderThreshold);

            // 如果没有达标，则执行自动下单
            if (thresholdMet > 0) {
                performAutoOrder(shop);
                shopMapper.updateBrowseCount(shop.getId());
                shopMapper.updateTaskCompleteStatus(shop.getId(), currentWeek);
            }
        }
    }

    private int calculateGptThreshold(BusShop shop) {
        return shop.getOrderqty() * 10;  // 示例：根据订单量动态调整 gpt 阈值
    }

    private int calculateVipGptThreshold(BusShop shop) {
        return shop.getOrderqty() * 5;  // 示例：根据订单量动态调整 VIP gpt 阈值
    }

    private int calculateOrderThreshold(BusShop shop) {
        return 100;  // 示例：订单阈值为 100
    }

    private void performAutoOrder(BusShop shop) {
        // 执行自动下单的逻辑
        // ...
    }
}
