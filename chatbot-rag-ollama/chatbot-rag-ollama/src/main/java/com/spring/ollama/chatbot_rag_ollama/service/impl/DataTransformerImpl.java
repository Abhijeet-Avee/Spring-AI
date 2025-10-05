package com.spring.ollama.chatbot_rag_ollama.service.impl;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import com.spring.ollama.chatbot_rag_ollama.service.DataTransformer;

@Service
public class DataTransformerImpl implements DataTransformer {

	@Override
	public List<Document> transformDocuments(List<Document> docList) {
		
		// Using TokenTextSplitter to split documents
		// into smaller chunks with default configuration
		var splitter = new TokenTextSplitter();
		List<Document> transformedDocList = splitter.transform(docList);
		
		// Using chunk size of 300 tokens,
		var splitter2 = new TokenTextSplitter(300, 400, 10, 5000, true);
		List<Document> transformedDocList2 = splitter2.transform(docList);
		
		return transformedDocList2;
	}

	
}
