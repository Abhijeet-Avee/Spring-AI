package com.spring.ollama.chatbot_rag_ollama.service;

public interface ChatDBMemoryService {

	String dbMemoryChatAuto(String query, String userId);
	
	String dbMemoryChatManual(String query, String userId);
}
