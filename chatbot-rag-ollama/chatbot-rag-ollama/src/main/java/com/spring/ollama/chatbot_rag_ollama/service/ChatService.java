package com.spring.ollama.chatbot_rag_ollama.service;

import java.util.List;

public interface ChatService {
	
	void saveData(List<String> data);
	
	String vectorStoreChatwithRAGandDBChatMemory(String query, String userId);
	
	String ragChatWithQuestionAdvisor(String query, String userId);
	
	String ragChatWithQuestionAdvisorSearchRequest(String query, String userId);
	
	void saveDataWithMetadata(List<String> data);
	
	String ragChatWithQuesAdvisorSearchReqDynamicFilter(String query, String userId);
	
	String ragChatWithCustomPromptTemplates(String query, String userId);
	
	String naiveRagRetrievalAugmentationAdvisor(String query, String userId);
}
