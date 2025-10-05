package com.spring.ollama.chatbot_rag_ollama.service;

import java.util.List;

import org.springframework.ai.document.Document;

public interface DataLoader {

	List<Document> loadDocumentsFromJSON();
	
	List<Document> loadDocumentsFromPDF();
	
}
