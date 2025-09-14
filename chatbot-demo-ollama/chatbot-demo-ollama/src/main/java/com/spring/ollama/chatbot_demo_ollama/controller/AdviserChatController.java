package com.spring.ollama.chatbot_demo_ollama.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ollama.chatbot_demo_ollama.service.AdvisorChatService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/adivsor-chat-api")
public class AdviserChatController {

	private AdvisorChatService adviserChatService;
	
	public AdviserChatController(AdvisorChatService adviserChatService) {
		this.adviserChatService = adviserChatService;
	}
	
	
	@GetMapping("/simplechat")
	public ResponseEntity<String> simpleChat(@RequestParam(value = "q", required = true) String query) {

		// Returning a simple chat using just the user query
		// Using advisor in SPRING AI
		String response = this.adviserChatService.advisorChat(query);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/streamchat")
	public ResponseEntity<Flux<String>> streamChat(@RequestParam(value = "q", required = true) String query) {
		
		// Returning a streaming chat using just the user query
		Flux<String> response = this.adviserChatService.advisorStreamChat(query);
		return ResponseEntity.ok(response);
		
	}
	
	// Session based chat using userId to maintain the session of chat memory
	@GetMapping("/sessionchat")
	public ResponseEntity<String> sessionChat(@RequestParam(value = "q", required = true) String query,
			@RequestHeader(value="userId", required=true) String userId) {

		// Returning a session chat using userId to maintain the session
		// userId is basically the conversationId passed on to the ChatMemory
		// ChatMemory.CONVERSATION_ID
		String response = this.adviserChatService.advisorSessionChat(query, userId);
		return ResponseEntity.ok(response);
	}
}
