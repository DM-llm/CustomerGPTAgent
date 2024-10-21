package com.example;

import com.example.service.GPTAutoCommentService;
import com.example.service.GPTConversationService;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YourApplication implements CommandLineRunner {

	@Autowired
	private GPTConversationService gptConversationService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private GPTAutoCommentService gptAutoCommentService;



	public static void main(String[] args) {
		SpringApplication.run(YourApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 查prompt 启动 GPT 对话
		//gptConversationService.handleConversation();

		// 查询商品信息 下单
		//orderService.autoPlaceOrder();

		// 自动评论
		gptAutoCommentService.handleAutoComment();
	}
}
