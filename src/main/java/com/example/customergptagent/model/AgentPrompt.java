package com.example.customergptagent.model;

public class AgentPrompt {

    private Long id;         // Prompt 的唯一标识符
    private String type;     // Prompt 的类型，例如 "customer" 或 "review"
    private String promptText;  // Prompt 的具体内容（存放 prompt 的文本）

    // 构造函数
    public AgentPrompt() {}

    public AgentPrompt(Long id, String type, String promptText) {
        this.id = id;
        this.type = type;
        this.promptText = promptText;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    @Override
    public String toString() {
        return "AgentPrompt{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", promptText='" + promptText + '\'' +
                '}';
    }
}
