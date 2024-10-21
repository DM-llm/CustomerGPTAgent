package com.example.service;

import com.example.mapper.PromptMapper;
import com.example.mapper.TradeOrderMapper;
import com.example.model.TradeOrder;
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
    private PromptMapper promptMapper;

    // 自动评论的逻辑
    public void handleAutoComment() {
        // 从数据库查询订单状态为 20（已完成下单）的订单
        List<TradeOrder> completedOrders = getCompletedOrders();

        // 如果有已完成的订单，则执行自动评论逻辑
        if (completedOrders != null && !completedOrders.isEmpty()) {
            for (TradeOrder order : completedOrders) {
                // 查询 product_review_agent 类型的 prompt
                String prompt = getProductReviewAgentPrompt();
                System.out.println("prompt:"+prompt);
                // 获取商品详情，实际逻辑你已经实现
                String productDetails = getProductDetails(order);

                // 发送 prompt 和商品详情给 GPT
                String gptResponse = sendRequestToGPT(prompt, productDetails);

                // 调用接口填写评论
                postComment(gptResponse, order);
            }
        }
    }

    // 查询订单状态为 20 的订单列表
    private List<TradeOrder> getCompletedOrders() {
        // 调用 TradeOrderMapper 查询订单状态为 20 的订单
        return tradeOrderMapper.findOrdersByStatus(20);
    }

    // 查询 product_review_agent 类型的 prompt
    private String getProductReviewAgentPrompt() {
        // 使用 PromptService 查询类型为 product_review_agent 的 prompt，复用之前的逻辑

        return promptService.selectProductReviewAgentPrompt();
    }

    // 调用 GPT API 并返回 GPT 的回复
    private String sendRequestToGPT(String prompt, String productDetails) {
        // 构建 GPT 请求体，包含 prompt 和商品详情
        Map<String, Object> conversationRequest = new HashMap<>();
        conversationRequest.put("model", "gpt-3.5-turbo");
        conversationRequest.put("messages", List.of(
                Map.of("role", "system", "content", prompt),
                Map.of("role", "system", "content", "Product Details: " + productDetails)
        ));

        // 通过 GPTRequestService 发送请求
        return gptRequestService.sendRequestToGPT(conversationRequest);
    }

    // 模拟获取商品详情，实际查询逻辑已由你实现
    private String getProductDetails(TradeOrder order) {
        // 这里调用商品查询部分的代码
        return "Product details for order " + order.getNo(); // 示例返回
    }

    // 模拟将评论发送到评论接口，实际逻辑由你实现
    private void postComment(String gptResponse, TradeOrder order) {
        // 调用第二个接口，填写评论
        System.out.println("向订单 " + order.getNo() + " 添加评论: " + gptResponse);
        // 实际可以通过 HTTP 调用发送评论信息
    }
}
