package com.spring.ollama.chatbot_demo_ollama.config;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
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
	
	@Bean(name = "ollamaChatClient2")
	public ChatClient ollamaChatClient2(OllamaChatModel ollamaChatModel) {
		return ChatClient.builder(ollamaChatModel)
				//Enabling custom advisor globally for the client
				.defaultAdvisors( 
						new TokenCountLogAdvisor()) // custom advisor to log token count					
				.build();
	}

	// -- Similarly for multiple models, you can define more beans here
}
