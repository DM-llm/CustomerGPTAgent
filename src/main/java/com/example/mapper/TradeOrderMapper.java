package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TradeOrderMapper {

    // 查询状态为 20 的订单编号
    List<String> selectOrderNosByStatus();

    // 根据订单编号查询 spu_id
    List<Long> selectSpuIdByOrderNo(String orderNo);
}
