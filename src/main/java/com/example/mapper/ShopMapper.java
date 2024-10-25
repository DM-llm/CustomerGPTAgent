package com.example.mapper;

import com.example.model.BusShop;
import com.example.model.BusShopWeeklyTaskLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {
    // 查询每周店铺数据
    List<BusShop> findWeeklyShopData();

    // 插入冗余表
    void insertWeeklyTaskLog(@Param("tenantId") int tenantId, @Param("weekNumber") int weekNumber,
                             @Param("gptqty") int gptqty, @Param("vipgptqty") int vipgptqty,
                             @Param("orderqty") int orderqty);

    // 查询每周任务记录
    BusShopWeeklyTaskLog findWeeklyTaskLog(@Param("tenantId") int tenantId, @Param("weekNumber") int weekNumber);

    // 检查每周任务记录是否存在
    int checkWeeklyTaskLogExists(@Param("tenantId") int tenantId, @Param("weekNumber") int weekNumber);

    // 更新任务日志
    void updateTaskLog(@Param("tenantId") int tenantId, @Param("weekNumber") int weekNumber,
                       @Param("gptqty") int gptqty, @Param("vipgptqty") int vipgptqty,
                       @Param("orderqty") int orderqty);
}
