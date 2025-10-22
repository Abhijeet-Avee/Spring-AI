package com.spring.ai.help_desk_backend.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.PostgresChatMemoryRepositoryDialect;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AIConfig {

	
	@Bean
	public ChatClient chatClient(OllamaChatModel ollamaChatModel, JdbcTemplate jdbcTemplate) {
		
		// Here we are creating JdbcChatMemoryRepository instance manually
		ChatMemoryRepository chatMemoryRepository = JdbcChatMemoryRepository.builder()
				.jdbcTemplate(jdbcTemplate)
				.dialect(new PostgresChatMemoryRepositoryDialect())
				.build();
		
		// Creating MessageWindowChatMemory instance with max 20 messages
		ChatMemory chatMemory = MessageWindowChatMemory.builder()
				.chatMemoryRepository(chatMemoryRepository)
				.maxMessages(20).build();

		// Passing the created chat memory instance to the advisor
		MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
		
		
		return ChatClient.builder(ollamaChatModel)
				.defaultSystem("Summarize the response in a concise manner within 200 words.")
				.defaultAdvisors(memoryAdvisor, new SimpleLoggerAdvisor())
				.build();
	}

}
