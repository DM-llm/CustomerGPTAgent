package com.example.mapper;


import com.example.model.BusShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {
    // 查询每周店铺数据
    List<BusShop> findWeeklyShopData();

    // 插入冗余表
    void insertWeeklyTaskLog(@Param("shopId") int shopId, @Param("weekNumber") int weekNumber,
                             @Param("gptqty") int gptqty, @Param("vipgptqty") int vipgptqty,
                             @Param("orderqty") int orderqty);

    // 检查是否达标
    int checkThresholdMet(@Param("shopId") int shopId, @Param("gptqtyThreshold") int gptqtyThreshold,
                          @Param("vipgptqtyThreshold") int vipgptqtyThreshold,
                          @Param("orderqtyThreshold") int orderqtyThreshold);

    // 更新浏览次数
    void updateBrowseCount(@Param("shopId") int shopId);

    // 更新任务完成状态
    void updateTaskCompleteStatus(@Param("shopId") int shopId, @Param("weekNumber") int weekNumber);
}
