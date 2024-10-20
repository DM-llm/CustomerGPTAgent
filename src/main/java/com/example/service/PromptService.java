package com.example.service;

import com.example.mapper.PromptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

    @Autowired
    private PromptMapper promptMapper;

    // 从数据库中查询 customer_agent 类型的随机 prompt
    public String getCustomerAgentPrompt() {
        return promptMapper.selectCustomerAgentPrompt();
    }
}
