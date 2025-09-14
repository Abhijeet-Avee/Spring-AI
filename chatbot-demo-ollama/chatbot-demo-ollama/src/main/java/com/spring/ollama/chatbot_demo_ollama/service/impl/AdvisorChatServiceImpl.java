package com.spring.ollama.chatbot_demo_ollama.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.spring.ollama.chatbot_demo_ollama.service.AdvisorChatService;

import reactor.core.publisher.Flux;

@Service
public class AdvisorChatServiceImpl implements AdvisorChatService {

	private ChatClient chatClient;
	
	@Value("classpath:prompts/system-message.st")
	private Resource systemMessageResource;
	
	@Value("classpath:prompts/user-message.st")
	private Resource userMessageResource;

	public AdvisorChatServiceImpl(@Qualifier(value = "ollamaChatClient3") ChatClient ollamaChatClient) {
		this.chatClient = ollamaChatClient;
	}

	// Using advisor in SPRING AI
	@Override
	public String advisorChat(String query) {

		// Using Fluent API to create prompts from file resources
		var chatResponse2 = this.chatClient.prompt()
				.advisors(new SimpleLoggerAdvisor()) // advisor to log the messages
				.system(this.systemMessageResource) // system message from file
				.user(user -> user.text(this.userMessageResource) // user message from file
						.param("concept", query))
				.call().content();

		return chatResponse2;

	}

	@Override
	public Flux<String> advisorStreamChat(String query) {
		
		// Streaming response using advisor in SPRING AI
		var streamResponse = this.chatClient.prompt()
				.system(this.systemMessageResource) // system message from file
				.user(user -> user.text(this.userMessageResource) // user message from file
						.param("concept", query))
				.stream().content();
		
		return streamResponse;
	}

	// Session based chat using userId to maintain the session of chat memory
	@Override
	public String advisorSessionChat(String query, String userId) {
		
		var chatResponse2 = this.chatClient.prompt()
				// Passing conversationId to the advisor to maintain the session
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
				.system(this.systemMessageResource) // system message from file
				.user(user -> user.text(this.userMessageResource) // user message from file
						.param("concept", query))
				.call().content();

		return chatResponse2;

	}

}
