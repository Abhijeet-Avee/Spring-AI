package com.spring.ollama.chatbot_demo_ollama.service.impl;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
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

}
