package com.spring.ollama.chatbot_demo_ollama.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.spring.ollama.chatbot_demo_ollama.service.ChatDBMemoryService;

@Service
public class ChatDBMemoryServiceImpl implements ChatDBMemoryService {
	
	private ChatClient chatClientAuto;
	
	private ChatClient chatClientManual;
	
	@Value("classpath:prompts/system-message.st")
	private Resource systemMessageResource;
	
	@Value("classpath:prompts/user-message.st")
	private Resource userMessageResource;
	
	public ChatDBMemoryServiceImpl(@Qualifier(value = "ollamaChatClient4") ChatClient ollamaChatClientAuto,
			@Qualifier(value = "ollamaChatClient5") ChatClient ollamaChatClientManual) {
		this.chatClientAuto = ollamaChatClientAuto;
		this.chatClientManual = ollamaChatClientManual;
	}

	// Session based chat using userId to maintain the session of chat memory
	@Override
	public String dbMemoryChatAuto(String query, String userId) {
		
		var chatResponse2 = this.chatClientAuto.prompt()
				// Passing conversationId to the advisor to maintain the session
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
				.system(this.systemMessageResource) // system message from file
				.user(user -> user.text(this.userMessageResource) // user message from file
						.param("concept", query))
				.call().content();

		return chatResponse2;
		
	}

	
	// Using Manual JdbcChatMemoryRepository with ChatMemoryAdvisor
	@Override
	public String dbMemoryChatManual(String query, String userId) {
		
		var chatResponse = this.chatClientManual.prompt()
				// Passing conversationId to the advisor to maintain the session
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
				.system(this.systemMessageResource) // system message from file
				.user(user -> user.text(this.userMessageResource) // user message from file
						.param("concept", query))
				.call().content();

		return chatResponse;
		
	}

}
