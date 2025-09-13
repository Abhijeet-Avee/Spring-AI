package com.spring.ollama.chatbot_demo_ollama.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ollama.chatbot_demo_ollama.service.AdvisorChatService;

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
}
