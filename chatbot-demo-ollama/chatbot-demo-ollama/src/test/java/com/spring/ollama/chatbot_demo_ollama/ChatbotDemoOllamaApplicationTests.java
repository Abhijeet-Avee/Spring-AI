package com.spring.ollama.chatbot_demo_ollama;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.ollama.chatbot_demo_ollama.service.ChatService;

@SpringBootTest
class ChatbotDemoOllamaApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private ChatService chatService;
	
	@Test
	void testPromptTemplate() {
		System.out.println("Prompt Template Test");
		
//		var output = this.chatService.simplePromptTemplate();
		
//		var output = this.chatService.promptTemplateWithRoles();
		
//		var output = this.chatService.promptUsingFluentApi();
		
//		var output = this.chatService.redingPromptTemplateFromFileWithRoles();
		
		var output = this.chatService.readingPrmomptTemplateFromFileWithFluentApi();
		
		System.out.println(output);
		
	}

}
