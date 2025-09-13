package com.spring.ollama.chatbot_demo_ollama.controller;

import java.util.List;

import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ollama.chatbot_demo_ollama.entity.BasicResponseModel;
import com.spring.ollama.chatbot_demo_ollama.service.ChatService;

@RestController
@RequestMapping("/chatapi")
public class ChatController {

	private ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping("/simplechat")
	public ResponseEntity<String> simpleChat(@RequestParam(value = "q", required = true) String query) {

		// Returning a simple chat using just the user query
		String response = this.chatService.simpleStringChat(query);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/prompt-based-chat")
	public ResponseEntity<String> promptBasedChat(@RequestParam(value = "q", required = true) String query) {

		// Returning a chat using system and user prompts
		String response = this.chatService.systemAndUserPromptBasedChat(query);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/prompt-class-chat")
	public ResponseEntity<String> promptClassChat(@RequestParam(value = "q", required = true) String query) {

		// Returning a chat using created prompt by using the Prompt class
		String response = this.chatService.promptClassChat(query);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/chatResponseOutput")
	public ResponseEntity<String> chatResponseOutput(@RequestParam(value = "q", required = true) String query) {

		// Returning a chat response by accessing the response object ChatResponse
		String response = this.chatService.chatResponseOutput(query);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getMetaDataUsingChatResponse")
	public ResponseEntity<ChatResponseMetadata> getMetaDataUsingChatResponse(
			@RequestParam(value = "q", required = true) String query) {

		// Returning metadata information from the response object ChatResponse
		ChatResponseMetadata response = this.chatService.getMetaDataUsingChatResponse(query);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/entityAsResponse")
	public ResponseEntity<BasicResponseModel> entityAsResponse(
			@RequestParam(value = "q", required = true) String query) {

		// Returning response as a custom entity
		BasicResponseModel response = this.chatService.entityAsResponse(query);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/entityResultListAsResponse")
	public ResponseEntity<List<BasicResponseModel>> getEntityResultListAsResponse(
			@RequestParam(value = "q", required = true) String query) {

		// Returning response as a list of custom entity
		List<BasicResponseModel> response = this.chatService.getEntityResultListAsResponse(query);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/promptDefaultOptions")
	public ResponseEntity<String> getChatUsingPromptDefaultOptions(
			@RequestParam(value = "q", required = true) String query) {

		// Returning a simple chat using just the user query with default prompt options
		String response = this.chatService.simpleStringChatWithPromptDefault(query);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/propmptUserSpec")
	public ResponseEntity<String> getResponsUsingPromptUserSpec(
			@RequestParam(value = "q", required = true) String query) {

		// Returning a simple chat using just the user query with PromptUserSpec options
		String response = this.chatService.simpleStringChatWithPromptUserSpec(query);
		return ResponseEntity.ok(response);
	}
}
