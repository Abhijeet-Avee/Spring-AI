package com.spring.ollama.chatbot_demo_ollama.service;

import reactor.core.publisher.Flux;

public interface AdvisorChatService {

	String advisorChat(String query);
	
	Flux<String> advisorStreamChat(String query);

	String advisorSessionChat(String query, String userId);
}
