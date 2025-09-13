package com.spring.ollama.chatbot_demo_ollama.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.spring.ollama.chatbot_demo_ollama.entity.BasicResponseModel;
import com.spring.ollama.chatbot_demo_ollama.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	private ChatClient chatClient;

	public ChatServiceImpl(@Qualifier(value = "ollamaChatClient") ChatClient ollamaChatClient) {
		this.chatClient = ollamaChatClient;
	}

	@Override
	public String simpleStringChat(String query) {

		String chatResponse = this.chatClient.prompt(query).call().content();

		return chatResponse;
	}

	@Override
	public String systemAndUserPromptBasedChat(String query) {
		
		String chatResponse = this.chatClient
				.prompt()
				.user(query)
				.system("Limit your response to 100 words")
				.call()
				.content();
		
		return chatResponse;
	}

	@Override
	public String promptClassChat(String query) {
		
		Prompt customPrompt = new Prompt(query);
		
		String chatResponse = this.chatClient
				.prompt(customPrompt)
				.call()
				.content();
		
		return chatResponse;
		
	}

	@Override
	public String chatResponseOutput(String query) {
		
		Prompt customPrompt = new Prompt(query);
		
		String chatResponse = this.chatClient
				.prompt(customPrompt)
				.call()
				.chatResponse()
				.getResult()
				.getOutput()
				.getText();
		
		return chatResponse;
	}

	@Override
	public ChatResponseMetadata getMetaDataUsingChatResponse(String query) {
		
		Prompt customPrompt = new Prompt(query);
		
		var chatResponse = this.chatClient
				.prompt(customPrompt)
				.call()
				.chatResponse()
				.getMetadata();
				
		return chatResponse;
	}

	@Override
	public BasicResponseModel entityAsResponse(String query) {
		
		Prompt customPrompt = new Prompt(query);
		
		var chatResponse = this.chatClient
				.prompt(customPrompt)
				.call()
				.entity(BasicResponseModel.class);

				
		return chatResponse;

	}

	@Override
	public List<BasicResponseModel> getEntityResultListAsResponse(String query) {
		
		Prompt customPrompt = new Prompt(query);
		
		var chatResponse = this.chatClient
				.prompt(customPrompt)
				.call()
				.entity(new ParameterizedTypeReference<List<BasicResponseModel>>() {
						});

				
		return chatResponse;
	}

	@Override
	public String simpleStringChatWithPromptDefault(String query) {
		
		// Prompt(String contents, @Nullable ChatOptions chatOptions) 
//		Prompt customPrompt = new Prompt(query, ChatOptions.builder()
//				.model("codellama:latest")
//				.temperature(0.3)
//				.maxTokens(512)
//				.build() );
		
		// Or using OllamaOptions
		Prompt customPrompt = new Prompt(query, OllamaOptions.builder()
				.model("codellama:latest")
				.temperature(0.3)
				.build() );
		
		var chatResponse = this.chatClient
				.prompt(customPrompt)
				.call()
				.content();
				
		return chatResponse;
	}

	@Override
	public String simpleStringChatWithPromptUserSpec(String query) {
		
		// Using PromptUserSpec to create dynamic prompts with parameters
		String queryStr = "As an expert in coding and programming. Always write program in java. Now answer this question: {query}";
		
		var chatResponse = this.chatClient
				.prompt()
				.user(u -> u.text(queryStr).param("query", query)) 
				// PromptUserSpec u -> u.text(String text).param(String name, Object value)
				.call()
				.content();

				
		return chatResponse;
		

	}

	@Override
	public String simplePromptTemplate() {
		
		// Using PromptTemplate to create reusable prompt templates with parameters
		PromptTemplate template = PromptTemplate.builder().template("What is {techName}? Tell me about {techFeature} in breif.")
				.build();
		
		// Rendering the template with actual values
		String chatQuery = template.render(Map.of(
											"techName", "Spring", 
											"techFeature", "Spring AI"
						));
		
		// Now using the rendered chatQuery to get the response
		var chatResponse = this.chatClient.prompt(chatQuery)
				.call()
				.content();
		
		return chatResponse;
	}

	@Override
	public String promptTemplateWithRoles() {
		
		// Using SystemPromptTemplate to create system messages
		SystemPromptTemplate systemPromptTemplate = SystemPromptTemplate.builder()
				.template("You are a helpful coding assistant. You are an expert in Coding and Programming.")
				.build();
		
		// Creates a system message from the template
		Message systemMessage = systemPromptTemplate.createMessage(); 
		
		// Using PromptTemplate to create user messages
		PromptTemplate userPromptTemplate = PromptTemplate.builder()
				.template("What is {techName}? Tell me about {techFeature} in breif.")
				.build();
		
		// Creates a user message from the template with actual values
		Message userMessage = userPromptTemplate.createMessage(Map.of(
				"techName", "Spring", 
				"techFeature", "Spring AI"
		));
		
		// Creating a prompt with system and user messages
		Prompt prompt = new Prompt(systemMessage, userMessage);
		
		// Now using the created prompt to get the response
		var chatResponse = this.chatClient
				.prompt(prompt)
				.call()
				.content();
				
		return chatResponse;
	}

	@Override
	public String promptUsingFluentApi() {
		
		// Using Fluent API to create prompts
		var chatResponse = this.chatClient
				.prompt()
				.system("You are a helpful coding assistant. You are an expert in Coding and Programming.")
				.user(u -> u.text("What is {techName}? Tell me about {techFeature} in breif.")
						.param("techName", "Spring")
						.param("techFeature", "Spring AI"))
				.call()
				.content();
		
		// Or using Map for parameters
		var chatResponse2 = this.chatClient
				.prompt()
				.system(system-> system.text("You are a helpful coding assistant. You are an expert in Coding and Programming."))
				.user(user -> user.text("What is {techName}? Tell me about {techFeature} in breif.")
						.params(Map.of(
								"techName", "Spring", 
								"techFeature", "Spring AI")))
				.call()
				.content();
		
		return chatResponse2;
	}

	
	@Value("classpath:prompts/user-message.st")
	private Resource userMessageResource;
	
	@Value("classpath:prompts/system-message.st")
	private Resource systemMessageResource;
	
	@Override
	public String redingPromptTemplateFromFileWithRoles() {

		// Using SystemPromptTemplate to create system messages from file resource
		SystemPromptTemplate systemPromptTemplate = SystemPromptTemplate.builder()
				.resource(this.systemMessageResource)
				.build();

		// Creates a system message from the template
		Message systemMessage = systemPromptTemplate.createMessage();

		// Using PromptTemplate to create user messages from file resource
		PromptTemplate userPromptTemplate = PromptTemplate.builder()
				.resource(this.userMessageResource)
				.build();

		// Creates a user message from the template with actual values
		Message userMessage = userPromptTemplate
				.createMessage(Map.of("techName", "Spring", "techFeature", "Spring AI"));

		// Creating a prompt with system and user messages
		Prompt prompt = new Prompt(systemMessage, userMessage);

		// Now using the created prompt to get the response
		var chatResponse = this.chatClient.prompt(prompt).call().content();

		return chatResponse;

	}

	@Override
	public String readingPrmomptTemplateFromFileWithFluentApi() {
		
		// Using Fluent API to create prompts from file resources
		var chatResponse2 = this.chatClient
				.prompt()
				.system(this.systemMessageResource) // system message from file
				.user(user -> user.text(this.userMessageResource) // user message from file
						.params(Map.of(
								"techName", "Spring", 
								"techFeature", "Spring AI")))
				.call()
				.content();
		
		return chatResponse2;

	}

}
