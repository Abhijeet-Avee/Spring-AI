package com.spring.ollama.chatbot_rag_ollama.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.spring.ollama.chatbot_rag_ollama.service.AdvanceRAGService;

@Service
public class AdvanceRAGServiceImpl implements AdvanceRAGService {

	private ChatClient chatClient;

	// Injecting the vector store
	private VectorStore vectorStore;
	
	@org.springframework.beans.factory.annotation.Value("classpath:prompts/system-message.st")
	private Resource systemMessageResource;
	
	@org.springframework.beans.factory.annotation.Value("classpath:prompts/user-message.st")
	private Resource userMessageResource;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public AdvanceRAGServiceImpl(@Qualifier(value = "ollamaChatClient6") ChatClient chatClient,
			VectorStore vectorStore) {
		this.chatClient = chatClient;
		this.vectorStore = vectorStore;
	}

	@Override
	public String advanceRagPipeline(String userQuery, String userId) {
		
		
		Advisor ragAdvisor = RetrievalAugmentationAdvisor.builder()
				// Pre-Retrieval
				.queryTransformers(
						RewriteQueryTransformer.builder()
						.chatClientBuilder(this.chatClient.mutate().clone())
						.build(),
						TranslationQueryTransformer.builder()
						.chatClientBuilder(this.chatClient.mutate().clone())
						.targetLanguage("English")
						.build())
				.queryExpander(
						MultiQueryExpander.builder()
						.chatClientBuilder(this.chatClient.mutate().clone())
						.includeOriginal(false)
						.numberOfQueries(3)
						.build())
				// Retrieval
				.documentRetriever(
						VectorStoreDocumentRetriever.builder()
						.vectorStore(this.vectorStore)
						.topK(3)
						.similarityThreshold(0.6)
						.filterExpression(()-> new FilterExpressionBuilder()
								.eq("type", "Java")
								.build())
						.build())
				.documentJoiner(new 
						ConcatenationDocumentJoiner())
				// Post-Retrieval
				//.documentPostProcessors(null) // Implement if needed
				// Generation
				.queryAugmenter(
						ContextualQueryAugmenter.builder()
						.allowEmptyContext(false)
						.build())
				.build();
		

		
		var chatResponse = this.chatClient.prompt()
				.advisors(ragAdvisor)
				.user(userQuery) // Direct user message
				.call()
				.content();
		
		return chatResponse;
	}
	
}
