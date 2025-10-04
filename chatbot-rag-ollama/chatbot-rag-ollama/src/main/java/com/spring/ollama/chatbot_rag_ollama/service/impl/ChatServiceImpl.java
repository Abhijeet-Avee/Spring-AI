package com.spring.ollama.chatbot_rag_ollama.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.spring.ollama.chatbot_rag_ollama.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	private ChatClient chatClient;

	// Injecting the vector store
	private VectorStore vectorStore;

	public ChatServiceImpl(@Qualifier(value = "ollamaChatClient") ChatClient ollamaChatClient,
			VectorStore vectorStore) {
		this.chatClient = ollamaChatClient;
		this.vectorStore = vectorStore;
	}

	// Dumping the data into vector store
	@Override
	public void saveData(List<String> data) {

//		List<Document> docListt = data.stream().map(item -> new Document(item)).collect(Collectors.toList());

		// Converting the list of strings to list of Document objects
		List<Document> docList = data.stream().map(Document::new).collect(Collectors.toList());
		// Adding the documents to vector store
		this.vectorStore.add(docList);

	}

}
