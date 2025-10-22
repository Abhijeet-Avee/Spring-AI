package com.spring.ai.help_desk_backend.service.impl;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.spring.ai.help_desk_backend.service.AIService;
import com.spring.ai.help_desk_backend.tools.TicketDBTool;

@Service
public class AIServiceImpl implements AIService {

	private final ChatClient chatClient;
	private final TicketDBTool ticketDBTool;

	@Value("classpath:prompts/helpdesk-system.st")
	private Resource systemPrompt;
	
	@Value("classpath:prompts/user-message.st")
	private Resource userMessageResource;
	
	public AIServiceImpl(ChatClient chatClient, TicketDBTool ticketDBTool) {
		this.chatClient = chatClient;
		this.ticketDBTool = ticketDBTool;
	}

	@Override
	public String generateResponseFromAssistant(String query, String userName) {
		
		return this.chatClient
				.prompt()
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userName))
				.toolCallbacks(ToolCallbacks.from(ticketDBTool))
				.system(systemPrompt)
				.user(user -> user.text(this.userMessageResource) // user message from file
						.params(Map.of("user_message", query, "user_name", userName)))
				.call()
				.content();
	}

}
