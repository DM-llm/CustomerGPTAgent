package com.example.customergptagent.model;

public class AgentPrompt {

    private int id;
    private String type;     // 客户或评论类型
    private String content;  // prompt具体内容

    // 构造函数、getter 和 setter

    public AgentPrompt() {}

    public AgentPrompt(int id, String type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AgentPrompt{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
