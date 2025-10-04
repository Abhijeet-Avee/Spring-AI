package com.spring.ollama.chatbot_rag_ollama;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.ollama.chatbot_rag_ollama.helper.Helper;
import com.spring.ollama.chatbot_rag_ollama.service.ChatService;

@SpringBootTest
class ChatbotDemoOllamaApplicationTests {

	@Autowired
	private ChatService chatService;

	@Test
	void saveDataToVectorStore() {

		System.out.println("Saving data to vector store");
		
		try {
			this.chatService.saveData(Helper.getData());
		}catch (Exception e) {
			System.err.println("Error saving data to vector store: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		
		
		System.out.println("Data saved to vector store successfully");
	}

}
