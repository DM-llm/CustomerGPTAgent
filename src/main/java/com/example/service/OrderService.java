package com.example.service;

import com.example.model.Product;
import com.example.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class OrderService {

    @Autowired
    private ProductMapper productMapper;

    // 自动下单逻辑，并返回下单的商品信息
    public Product autoPlaceOrder() {
        // 获取 optype = 1 的 spu_no 列表
        List<String> spuNos = productMapper.selectAutoPurchaseSpuNos();

        if (spuNos != null && !spuNos.isEmpty()) {
            // 随机选择一个 spu_no
            Random random = new Random();
            String selectedSpuNo = spuNos.get(random.nextInt(spuNos.size()));

            // 根据 spu_no 查询商品信息
            Product product = productMapper.selectProductBySpuNo(selectedSpuNo);

            if (product != null) {
                System.out.println("商品ID: " + product.getId());
                System.out.println("商品名称: " + product.getName());
                System.out.println("商品简介: " + product.getIntroduction());
                System.out.println("商品详情: " + product.getDescription());

                // 执行下单逻辑并返回商品信息
                return placeOrder(product);
            } else {
                System.out.println("未找到商品信息。");
                return null;
            }
        } else {
            System.out.println("未找到符合条件的 spu_no。");
            return null;
        }
    }

    // 示例下单操作逻辑
    private Product placeOrder(Product product) {
        // 在此处执行实际的下单操作逻辑
        System.out.println("为商品 " + product.getName() + " 自动下单。");
        return product; // 返回下单的商品信息
    }
}
