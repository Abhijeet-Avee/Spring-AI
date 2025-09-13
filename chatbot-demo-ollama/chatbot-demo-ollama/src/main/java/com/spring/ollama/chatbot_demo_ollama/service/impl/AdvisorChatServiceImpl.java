package com.spring.ollama.chatbot_demo_ollama.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.spring.ollama.chatbot_demo_ollama.service.AdvisorChatService;

@Service
public class AdvisorChatServiceImpl implements AdvisorChatService {

	private ChatClient chatClient;
	
	@Value("classpath:prompts/system-message.st")
	private Resource systemMessageResource;
	
	@Value("classpath:prompts/user-message.st")
	private Resource userMessageResource;

	public AdvisorChatServiceImpl(@Qualifier(value = "ollamaChatClient2") ChatClient ollamaChatClient) {
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

}
