package com.example.service;

import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class GPTConversationService {

    @Autowired
    private PromptService promptService;

    @Autowired
    private GPTRequestService gptRequestService;

    @Autowired
    private OrderService orderService;

    // 用来保存整个对话的上下文（系统消息、GPT回复、用户输入）
    private List<Map<String, String>> conversationHistory = new ArrayList<>();

    // 商品详情
    private String productDetails; // 商品详情字段

    // 最大对话轮数
    private static final int MAX_ROUNDS = 5;

    // 处理对话逻辑
    public void handleConversation() {
        // 从数据库查询类型为 customer_agent 的随机 prompt
        String prompt = promptService.getCustomerAgentPrompt();
        System.out.println(prompt);

        // 获取商品详情
        productDetails = getProductDetails();
        System.out.println("商品详情: " + productDetails);

        // 首次发送 prompt 和商品详情
        conversationHistory.add(Map.of("role", "system", "content", prompt));
        conversationHistory.add(Map.of("role", "system", "content", "Product Details: " + productDetails));

        // 调用 GPT API 获取首次回复
        String gptResponse = sendRequestToGPT();
        System.out.println("GPT 回复: " + gptResponse);

        // 开始用户与 GPT 的对话，进行 4 轮用户输入 + GPT 回复
        for (int round = 1; round < MAX_ROUNDS; round++) {
            // 获取用户输入
            String userInput = getLatestChatInput();
            conversationHistory.add(Map.of("role", "user", "content", userInput));

            // 调用 GPT API 获取回复
            gptResponse = sendRequestToGPT();
            System.out.println("GPT 回复: " + gptResponse);
        }

        System.out.println("对话结束。");


    }

    // 调用 GPT API 并返回 GPT 的回复
    private String sendRequestToGPT() {
        // 构建 GPT 请求体，包含当前的对话历史
        Map<String, Object> conversationRequest = new HashMap<>();
        conversationRequest.put("model", "gpt-3.5-turbo");
        conversationRequest.put("messages", conversationHistory);

        // 通过 GPTRequestService 发送请求
        String gptResponse = gptRequestService.sendRequestToGPT(conversationRequest);

        // 将 GPT 的回复加入对话历史
        conversationHistory.add(Map.of("role", "assistant", "content", gptResponse));

        return gptResponse;
    }

    // 获取商品详情
    public String getProductDetails() {
        // 从 OrderService 获取已下单的商品信息
        Product product = orderService.autoPlaceOrder();

        if (product != null) {
            // 返回商品详情
            return "Product Name: " + product.getName() + ", Introduction: " + product.getIntroduction() + ", Description: " + product.getDescription();
        } else {
            return "No product details found.";
        }
    }

    // 商家回复消息接口
    private String getLatestChatInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入聊天信息：");
        return scanner.nextLine();
    }
}
