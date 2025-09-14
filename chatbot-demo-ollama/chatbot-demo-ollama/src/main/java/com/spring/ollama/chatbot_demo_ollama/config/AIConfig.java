package com.spring.ollama.chatbot_demo_ollama.config;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.ollama.chatbot_demo_ollama.advisor.TokenCountLogAdvisor;

@Configuration
public class AIConfig {

	@Bean(name = "ollamaChatClient")
	public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
		return ChatClient.builder(ollamaChatModel)
				.defaultAdvisors( 
						//Enabling advisors globally for the client
						new SimpleLoggerAdvisor(), // example advisor to log the messages
						new SafeGuardAdvisor(List.of("games"))) // example advisor to block certain content
				.build();
	}

	@Bean(name = "ollamaChatClientDefault")
	public ChatClient ollamaChatClientDefault(OllamaChatModel ollamaChatModel) {
		
		ChatOptions defaultOptions = ChatOptions.builder()
				.model("codellama:latest")
				.temperature(0.3)
				.build();
		
		return ChatClient.builder(ollamaChatModel)
				//Enabling advisors globally for the client
				.defaultAdvisors( 
						// SimpleLoggerAdvisor with custom log level (order of execution)
						new SimpleLoggerAdvisor(0),
						// SafeGuardAdvisor with custom message and severity level (order
						new SafeGuardAdvisor(List.of("games"), "I cannot reply to this query", 1)) 
				.defaultSystem("You are a coding assistant. Answer as concisely as possible.")
				.defaultOptions(defaultOptions).build();
	}
	
	
	// Example of using ChatMemoryAdvisor with ChatMemory
	@Bean(name = "ollamaChatClient2")
	public ChatClient ollamaChatClient2(OllamaChatModel ollamaChatModel, ChatMemory chatMemory) {
		
		// Here chat memory is injected by Spring AI
		// It is providing MessageWindowChatMemory implementation by default
		// Enabling chat memory advisor globally for the client
		MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
																			.build();
		
		return ChatClient.builder(ollamaChatModel)
				// Enabling multiple advisors globally for the client
				.defaultAdvisors( memoryAdvisor, // chat memory advisor
						new TokenCountLogAdvisor()) // custom advisor to log token counts			
				.build();
	}
	
	// Example of using ChatMemoryAdvisor with ChatMemory with manually created instance
	@Bean(name = "ollamaChatClient3")
	public ChatClient ollamaChatClient3(OllamaChatModel ollamaChatModel) {

		// Here we are creating MessageWindowChatMemory instance with max 10 messages
		MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.builder()
																			.maxMessages(10)
																			.build();
		// Passing the created chat memory instance to the advisor
		MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(messageWindowChatMemory).build();

		return ChatClient.builder(ollamaChatModel)
				// Enabling multiple advisors globally for the client
				.defaultAdvisors(memoryAdvisor, // chat memory advisor
						new TokenCountLogAdvisor()) // custom advisor to log token counts
				.build();
	}

	// -- Similarly for multiple models, you can define more beans here
}
