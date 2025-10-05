package com.spring.ollama.chatbot_rag_ollama;

import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.ollama.chatbot_rag_ollama.helper.Helper;
import com.spring.ollama.chatbot_rag_ollama.service.ChatService;
import com.spring.ollama.chatbot_rag_ollama.service.DataLoader;
import com.spring.ollama.chatbot_rag_ollama.service.DataTransformer;

@SpringBootTest
class ChatbotDemoOllamaApplicationTests {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private DataLoader dataLoader;
	
	@Autowired
	private DataTransformer dataTransformer;
	
	@Autowired
	private VectorStore vectorStore;

//	@Test
//	void saveDataToVectorStore() {
//
//		System.out.println("Saving data to vector store");
//		
//		try {
//			this.chatService.saveData(Helper.getData());
//		}catch (Exception e) {
//			System.err.println("Error saving data to vector store: " + e.getMessage());
//			e.printStackTrace();
//			return;
//		}
//		
//		
//		System.out.println("Data saved to vector store successfully");
//	}
	
//	@Test
//	void saveDataToVectorStore() {
//	    System.out.println("Saving data to vector store...");
//
//	    try {
//	        // Pass the list of text content to your chatService for vector storage
//	        this.chatService.saveDataWithMetadata(Helper.getData());
//	        System.out.println("Data saved to vector store successfully");
//	    } catch (Exception e) {
//	        System.err.println("Error saving data to vector store: " + e.getMessage());
//	        e.printStackTrace();
//	    }
//	}
	
	@Test
	void loadDataFromJSON() {
	
		System.out.println("Loading data from JSON...");

	    try {
	        // Load documents from JSON using the DataLoader service
	        var documents = this.dataLoader.loadDocumentsFromJSON();
	        
	        System.out.println("Documents loaded: " + documents.size());
	        
	        for(var doc : documents) {
	        	System.out.println(doc);
	        }
	        
	        System.out.println("Data loaded from JSON successfully");
	    } catch (Exception e) {
	        System.err.println("Error loading data from JSON: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	@Test
	void loadDataFromPDF() {
	
		System.out.println("Loading data from PDF...");

	    try {
	        // Load documents from PDF using the DataLoader service
	        var documents = this.dataLoader.loadDocumentsFromPDF();
	        
	        System.out.println("Documents loaded: " + documents.size());
	        
	        for(var doc : documents) {
	        	System.out.println(doc);
	        	System.out.println("------------------------");
	        }
	        
	        System.out.println("Data loaded from PDF successfully");
	        
	        System.out.println("------------------------");
	        System.out.println("Transforming documents...");
	        
	        // Transforming documents using the DataTransformer service
	        var transformedDocs = this.dataTransformer.transformDocuments(documents);
	        System.out.println("Transformed Documents count: " + transformedDocs.size());
	        System.out.println("------------------------");
	        for(var doc : transformedDocs) {
	        	System.out.println(doc);
	        	System.out.println("------------------------");
	        }
	        
	        // Saving transformed documents to vector store
	        System.out.println("Saving transformed documents to vector store...");
	        this.vectorStore.add(transformedDocs);
	        System.out.println("------------------------");
	        System.out.println("Transformed documents saved to vector store successfully");
	        
	    } catch (Exception e) {
	        System.err.println("Error loading data from PDF: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}
