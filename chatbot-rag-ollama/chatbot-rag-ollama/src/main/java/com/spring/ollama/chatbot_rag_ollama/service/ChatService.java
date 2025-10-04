package com.spring.ollama.chatbot_rag_ollama.service;

import java.util.List;

import org.springframework.ai.chat.metadata.ChatResponseMetadata;

import com.spring.ollama.chatbot_rag_ollama.entity.BasicResponseModel;

public interface ChatService {

	void saveData(List<String> data);
	
}
