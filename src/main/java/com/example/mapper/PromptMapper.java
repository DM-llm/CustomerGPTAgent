package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromptMapper {

    // SQL 查询随机的 customer_agent 类型 prompt
    String selectCustomerAgentPrompt();

    // 查询自动评论智能体product_review_agent类型 prompt
    String selectProductReviewAgentPrompt();

}
