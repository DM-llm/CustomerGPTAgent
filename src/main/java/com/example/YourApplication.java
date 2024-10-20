package com.example;

import com.example.service.GPTConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YourApplication implements CommandLineRunner {

	@Autowired
	private GPTConversationService gptConversationService;



	public static void main(String[] args) {
		SpringApplication.run(YourApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 启动 GPT 对话
		gptConversationService.handleConversation();
	}
}
