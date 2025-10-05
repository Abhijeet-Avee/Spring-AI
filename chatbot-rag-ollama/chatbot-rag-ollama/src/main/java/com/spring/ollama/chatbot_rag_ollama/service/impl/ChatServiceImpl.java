package com.spring.ollama.chatbot_rag_ollama.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.Filter.ExpressionType;
import org.springframework.ai.vectorstore.filter.Filter.Key;
import org.springframework.ai.vectorstore.filter.Filter.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.spring.ollama.chatbot_rag_ollama.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	
	private ChatClient chatClient;

	// Injecting the vector store
	private VectorStore vectorStore;
	
	@org.springframework.beans.factory.annotation.Value("classpath:prompts/system-message.st")
	private Resource systemMessageResource;
	
	@org.springframework.beans.factory.annotation.Value("classpath:prompts/user-message.st")
	private Resource userMessageResource;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public ChatServiceImpl(@Qualifier(value = "ollamaChatClient6") ChatClient ollamaChatClient,
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

	@Override
	public String vectorStoreChatwithRAGandDBChatMemory(String query, String userId) {
		
		// Searching the vector store for similar documents
		SearchRequest searchRequest = SearchRequest.builder()
				.topK(3)
				.similarityThreshold(0.6)
				.query(query).build();
		
		// Getting the similar documents from vector store
		List<Document> documents = this.vectorStore.similaritySearch(searchRequest);
		
		// Extracting the text from the documents
		List<String> documentsList = documents.stream().map(Document::getText).toList();
		// Joining the documents to form a context data
		String contextData = String.join(", ", documentsList);
		
		logger.info("Context data: {}", contextData);
		
		var chatResponse = this.chatClient.prompt()
				// Passing conversationId to the advisor to maintain the session
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
				.system(system -> system.text(systemMessageResource)
						.param("documents", contextData)) // system message from file
				.user(user -> user.text(this.userMessageResource) // user message from file
						.param("query", query))
				.call().content();

		return chatResponse;
		
	}

	@Override
	public String ragChatWithQuestionAdvisor(String query, String userId) {
		
		// Here we are using QuestionAnswerAdvisor to get the context from vector store
		var chatResponse = this.chatClient.prompt()
				.advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
				.user(user -> user.text(this.userMessageResource) // user message from file
						.param("query", query))
				.call().content();

		return chatResponse;
		
	}

	@Override
	public String ragChatWithQuestionAdvisorSearchRequest(String query, String userId) {
		
		// Here we are using QuestionAnswerAdvisor with custom SearchRequest to get the context from vector store
		QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
			    .searchRequest(SearchRequest.builder()
			        .topK(2)
			        .similarityThreshold(0.5)
			        .build())
			    .build();

		// Using ChatMemory param to maintain the session
			var chatResponse = this.chatClient.prompt()
			    .advisors(advisorSpec -> advisorSpec
			        .advisors(questionAnswerAdvisor)                   // Attach QuestionAnswerAdvisor
			        .param(ChatMemory.CONVERSATION_ID, userId)         // Attach ChatMemory param
			    )
			    .user(user -> user
			        .text(this.userMessageResource)                    // User message from file
			        .param("query", query))                            // Dynamic variable for query
			    .call()
			    .content();

			return chatResponse;
	}
	
	// Saving data with metadata to vector store
	@Override
	public void saveDataWithMetadata(List<String> data) {
		
		List<Document> documents = new ArrayList<>();

        for (String text : data) {
            Map<String, Object> metadata = new HashMap<>();

            // Detect and tag language/type dynamically
            if (text.toLowerCase().contains("java")) {
                metadata.put("type", "Java");
            } else if (text.toLowerCase().contains("python")) {
                metadata.put("type", "Python");
            } else {
                metadata.put("type", "General");
            }

            metadata.put("source", "training_dataset");
            metadata.put("category", "programming");

            // Create document with content + metadata
            documents.add(new Document(text, metadata));
        }

        // Save to vector store (automatically generates embeddings)
        vectorStore.add(documents);

        System.out.println("Total documents saved: " + documents.size());
		
	}


	@Override
	public String ragChatWithQuesAdvisorSearchReqDynamicFilter(String query, String userId) {
		
		// Here we are using QuestionAnswerAdvisor with custom SearchRequest and dynamic filter expression
		QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
			    .searchRequest(SearchRequest.builder()
			        .topK(3)
			        .similarityThreshold(0.5)
			        .build())
			    .build();

		// Passing dynamic filter expression using QuestionAnswerAdvisor.FILTER_EXPRESSION param
			var chatResponse = this.chatClient.prompt()
			    .advisors(advisorSpec -> advisorSpec
			        .advisors(questionAnswerAdvisor) // Attach QuestionAnswerAdvisor
			        .params(Map.of(ChatMemory.CONVERSATION_ID, userId, // Attach ChatMemory param
			        		// Dynamic filter expression against metadata field 'type' in vector store
			        		QuestionAnswerAdvisor.FILTER_EXPRESSION, "type == 'Java'"))        
			    )
			    .user(user -> user
			        .text(this.userMessageResource)                    // User message from file
			        .param("query", query))                            // Dynamic variable for query
			    .call()
			    .content();

			return chatResponse;
		
	}

	@Override
	public String ragChatWithCustomPromptTemplates(String query, String userId) {
		
		// Custom prompt template with custom delimiters for context variable from vector store
		PromptTemplate customPromptTemplate = PromptTemplate.builder()
				// Customizing the template delimiters to <>
			    .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
			    .template("""
			            <query>

			            Context information is below.

						---------------------
						<question_answer_context>
						---------------------

						Given the context information and no prior knowledge, answer the query.

						Follow these rules:

						1. If the answer is not in the context, just say that you don't know.
						2. Avoid statements like "Based on the context..." or "The provided information...".
			            """)
			    .build();
		
		// Here we are using QuestionAnswerAdvisor with custom SearchRequest and dynamic filter expression
		QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
								.promptTemplate(customPromptTemplate) // Attaching custom prompt template
								.searchRequest(SearchRequest.builder()
										.topK(3)
										.similarityThreshold(0.5)
										.build())
								.build();

		// Passing dynamic filter expression using
		// QuestionAnswerAdvisor.FILTER_EXPRESSION param
		var chatResponse = this.chatClient.prompt()
				.advisors(
						advisorSpec -> advisorSpec.advisors(questionAnswerAdvisor) // Attach QuestionAnswerAdvisor
				.params(Map.of(ChatMemory.CONVERSATION_ID, userId, // Attach ChatMemory param
						// Dynamic filter expression against metadata field 'type' in vector store
						QuestionAnswerAdvisor.FILTER_EXPRESSION, "type == 'Java'")))
				.user(user -> user.text(this.userMessageResource) // User message from file
						.param("query", query)) // Dynamic variable for query
				.call().content();

		return chatResponse;
	}

	@Override
	public String naiveRagRetrievalAugmentationAdvisor(String query, String userId) {
		
		// Here we are using RetrievalAugmentationAdvisor with VectorStoreDocumentRetriever
		Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
		        .documentRetriever(VectorStoreDocumentRetriever.builder()
		        		.topK(3)
		                .similarityThreshold(0.5)
		                .vectorStore(vectorStore)
		                	// country == "BG"* new Expression(EQ, new Key("country"), new Value("BG"));
		                .filterExpression(new Expression(ExpressionType.EQ, new Key("type"), new Value("Java")))
		                .build())
		        .build();
		
		var chatResponse = this.chatClient.prompt()
				.advisors(
						advisorSpec -> advisorSpec.advisors(retrievalAugmentationAdvisor)
				.param(ChatMemory.CONVERSATION_ID, userId))
				.user(user -> user.text(this.userMessageResource) // User message from file
						.param("query", query)) // Dynamic variable for query
				.call().content();

		return chatResponse;
	}


}
