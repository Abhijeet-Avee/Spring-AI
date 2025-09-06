package com.spring.ollama.chatbot_demo_ollama.service;

import java.util.List;

import org.springframework.ai.chat.metadata.ChatResponseMetadata;

import com.spring.ollama.chatbot_demo_ollama.entity.BasicResponseModel;

public interface ChatService {

	String simpleStringChat(String query);

	String systemAndUserPromptBasedChat(String query);

	String promptClassChat(String query);
	
	String chatResponseOutput(String query);
	
	ChatResponseMetadata getMetaDataUsingChatResponse(String query);
	
	BasicResponseModel entityAsResponse(String query);
	
	List<BasicResponseModel> getEntityResultListAsResponse(String query);
	
	String simpleStringChatWithPromptDefault(String query);
}
