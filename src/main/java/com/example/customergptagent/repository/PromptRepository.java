package com.example.customergptagent.repository;

import com.example.customergptagent.model.AgentPrompt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface PromptRepository {

    // 根据 ID 获取 Prompt
    AgentPrompt getPromptById(@Param("id") int id);

    // 获取所有 Prompts
    List<AgentPrompt> getAllPrompts();

    // 根据类型获取 Prompts
    List<AgentPrompt> getPromptsByType(@Param("type") String type);
}
