package com.example.service;

import com.example.mapper.ProductMapper;
import com.example.mapper.PromptMapper;
import com.example.mapper.TradeOrderMapper;
import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GPTAutoCommentService {

    @Autowired
    private PromptService promptService;

    @Autowired
    private GPTRequestService gptRequestService;

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PromptMapper promptMapper;

    // 自动评论的逻辑
    public void handleAutoComment() {
        // 从数据库查询订单状态为 20（已完成）的订单编号
        List<String> completedOrderNos = getCompletedOrderNos();

        // 如果有已完成的订单，则执行自动评论逻辑
        if (completedOrderNos != null && !completedOrderNos.isEmpty()) {
            for (String orderNo : completedOrderNos) {
                // 查询 product_review_agent 类型的 prompt
                String prompt = getProductReviewAgentPrompt();
                System.out.println("prompt:" + prompt);

                // 获取商品详情
                String productDetails = getProductDetails(orderNo);
                System.out.println("productDetails:" + productDetails);
                // 发送 prompt 和商品详情给 GPT
                String gptResponse = sendRequestToGPT(prompt, productDetails);

                // 调用接口填写评论
                postComment(gptResponse, orderNo);
            }
        }
    }

    // 查询订单状态为 20 的订单编号
    private List<String> getCompletedOrderNos() {
        return tradeOrderMapper.selectOrderNosByStatus();
    }

    // 查询 product_review_agent 类型的 prompt
    private String getProductReviewAgentPrompt() {
        return promptService.selectProductReviewAgentPrompt();
    }

    // 调用 GPT API 并返回 GPT 的回复
    private String sendRequestToGPT(String prompt, String productDetails) {
        Map<String, Object> conversationRequest = new HashMap<>();
        conversationRequest.put("model", "gpt-3.5-turbo");
        conversationRequest.put("messages", List.of(
                Map.of("role", "system", "content", prompt),
                Map.of("role", "system", "content", "Product Details: " + productDetails)
        ));

        return gptRequestService.sendRequestToGPT(conversationRequest);
    }

    // 获取商品详情，实际查询逻辑
    private String getProductDetails(String orderNo) {
        // 根据订单编号查询关联的 spu_id 列表
        List<Long> spuIds = tradeOrderMapper.selectSpuIdByOrderNo(orderNo);

        StringBuilder productDetails = new StringBuilder();

        // 循环每个 spu_id，并根据 spu_id 查询商品信息
        for (Long spuId : spuIds) {
            Product product = productMapper.selectProductBySpuNo(String.valueOf(spuId));
            productDetails.append("Product: ").append(product.getName())
                    .append(", Description: ").append(product.getDescription())
                    .append(", Introduction: ").append(product.getIntroduction())
                    .append("; ");
        }

        return productDetails.toString();
    }

    // 模拟将评论发送到评论接口
    private void postComment(String gptResponse, String orderNo) {
        System.out.println("向订单 " + orderNo + " 添加评论: " + gptResponse);
    }
}
