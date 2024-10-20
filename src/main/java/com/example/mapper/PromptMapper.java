package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PromptMapper {

    // SQL 查询随机的 customer_agent 类型 prompt
    @Select("SELECT prompt_text FROM agent_prompts WHERE prompt_type = 'customer_agent' ORDER BY RAND() LIMIT 1")
    String selectCustomerAgentPrompt();
}
