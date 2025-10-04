package com.spring.ollama.chatbot_rag_ollama.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ollama.chatbot_rag_ollama.service.ChatDBMemoryService;

@RestController
@RequestMapping("/chat-db-memory-api")
public class ChatDBMemoryController {
	
private ChatDBMemoryService chatDBMemoryService;
	
	public ChatDBMemoryController(ChatDBMemoryService chatDBMemoryService) {
		this.chatDBMemoryService = chatDBMemoryService;
	}

	@GetMapping("/auto-config")
	public ResponseEntity<String> autoConfigChat(@RequestParam(value = "q", required = true) String query,
			@RequestHeader(value="userId", required=true) String userId) {

		// Returning a session chat using userId to maintain the session
		String response = this.chatDBMemoryService.dbMemoryChatAuto(query, userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/manual-config")
	public ResponseEntity<String> manualConfigChat(@RequestParam(value = "q", required = true) String query,
			@RequestHeader(value="userId", required=true) String userId) {

		// Returning a session chat using userId to maintain the session
		String response = this.chatDBMemoryService.dbMemoryChatManual(query, userId);
		return ResponseEntity.ok(response);
	}
}
