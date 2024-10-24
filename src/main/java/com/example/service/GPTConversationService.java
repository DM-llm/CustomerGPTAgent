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

    // 最大对话轮数，GPT 回复 4 轮，商家回复最后一轮
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
        Map<String, String> systemMessage1 = new HashMap<>();
        systemMessage1.put("role", "system");
        systemMessage1.put("content", prompt);

        Map<String, String> systemMessage2 = new HashMap<>();
        systemMessage2.put("role", "system");
        systemMessage2.put("content", "Product Details: " + productDetails);

        conversationHistory.add(systemMessage1);
        conversationHistory.add(systemMessage2);

        // 调用 GPT API 获取首次回复
        String gptResponse = sendRequestToGPT();
        System.out.println("GPT 回复: " + gptResponse);

        // 开始用户与 GPT 的对话，GPT 回复 4 轮，商家回复最后一轮
        for (int round = 1; round < MAX_ROUNDS - 1; round++) {
            // 获取用户输入
            String userInput = getLatestChatInput();
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", userInput);

            conversationHistory.add(userMessage);

            // 调用 GPT API 获取回复
            gptResponse = sendRequestToGPT();
            System.out.println("GPT 回复: " + gptResponse);
        }

        // 最后一轮：商家回复
        String merchantReply = getLatestChatInput(); // 商家手动输入
        Map<String, String> merchantMessage = new HashMap<>();
        merchantMessage.put("role", "merchant");
        merchantMessage.put("content", merchantReply);

        conversationHistory.add(merchantMessage); // 记录商家回复
        System.out.println("商家回复: " + merchantReply);

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
        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "assistant");
        assistantMessage.put("content", gptResponse);

        conversationHistory.add(assistantMessage);

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
