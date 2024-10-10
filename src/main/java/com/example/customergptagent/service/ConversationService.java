package com.example.customergptagent.service;

import com.example.customergptagent.model.AgentPrompt;
import com.example.customergptagent.repository.PromptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Service
public class ConversationService {

    @Autowired
    private GPTService gptService;

    @Autowired
    private PromptRepository promptRepository;

    /**
     * 开始新的对话流程，模拟客户提问并向 GPT 请求响应
     */
    public void startConversation() {
        // 随机选择一个 Prompt
        AgentPrompt randomPrompt = getRandomPrompt();
        String promptText = randomPrompt.getPromptText();

        // 调用 GPT 服务生成响应
        Mono<String> gptResponseMono = gptService.generateResponse(promptText);

        // 处理 GPT 响应并打印输出（或发送到其他接口）
        gptResponseMono.subscribe(gptResponse -> {
            System.out.println("Prompt: " + promptText);
            System.out.println("GPT Response: " + gptResponse);
        });
    }

    /**
     * 随机从 PromptRepository 获取一个 Prompt
     *
     * @return AgentPrompt 随机选中的 Prompt 对象
     */
    private AgentPrompt getRandomPrompt() {
        // 从 PromptRepository 获取所有 prompts
        List<AgentPrompt> prompts = promptRepository.findAll();

        // 若 prompt 列表为空，抛出异常或返回默认提示
        if (prompts.isEmpty()) {
            throw new IllegalStateException("No prompts found in the database!");
        }

        // 随机选择一个 Prompt
        int randomIndex = new Random().nextInt(prompts.size());
        return prompts.get(randomIndex);
    }
}
