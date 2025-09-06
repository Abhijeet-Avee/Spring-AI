package com.spring.ollama.chatbot_demo_ollama.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

	@Bean(name = "ollamaChatClient")
	public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
		return ChatClient.builder(ollamaChatModel).build();
	}

	@Bean(name = "ollamaChatClientDefault")
	public ChatClient ollamaChatClientDefault(OllamaChatModel ollamaChatModel) {
		
		ChatOptions defaultOptions = ChatOptions.builder()
				.model("codellama:latest")
				.temperature(0.3)
				.build();
		
		return ChatClient.builder(ollamaChatModel)
				.defaultSystem("You are a coding assistant. Answer as concisely as possible.")
				.defaultOptions(defaultOptions).build();
	}

	// -- Similarly for multiple models, you can define more beans here
}
