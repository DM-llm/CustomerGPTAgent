package com.example;

import com.example.service.GPTAutoCommentService;
import com.example.service.GPTConversationService;
import com.example.service.OrderService;
import com.example.service.ShopService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class YourApplication implements CommandLineRunner {

	@Autowired
	private GPTConversationService gptConversationService;

	@Autowired
	private OrderService OrderService;

	@Autowired
	private GPTAutoCommentService gptAutoCommentService;

	@Autowired
	private ShopService shopService;

	@Autowired



	public static void main(String[] args) {
		SpringApplication.run(YourApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 查prompt 启动 GPT 对话
		//gptConversationService.handleConversation();

		// 查询商品信息 下单
		//OrderService.autoPlaceOrder();

		// 自动评论
		//gptAutoCommentService.handleAutoComment();

		//虚拟浏览

		shopService.processWeeklyTask();

	}
}
