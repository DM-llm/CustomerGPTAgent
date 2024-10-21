package com.example.mapper;

import com.example.model.TradeOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TradeOrderMapper {

    // 查询状态为 20 的订单（已完成）
    @Select("SELECT * FROM trade_order WHERE status = #{status}")
    List<TradeOrder> findOrdersByStatus(int status);
}
