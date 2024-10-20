package com.example.customergptagent.mapper;


import com.example.customergptagent.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    // 查询 optype = 1 的 spu_no 列表
    List<String> selectAutoPurchaseSpuNos();

    // 根据 spu_no 查询商品信息
    Product selectProductBySpuNo(String spuNo);
}
