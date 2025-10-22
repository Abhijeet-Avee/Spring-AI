package com.spring.ai.help_desk_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ai.help_desk_backend.service.AIService;

@RestController
@RequestMapping("/api/v1/ai")
public class AIController {

	private final AIService aiService;

	public AIController(AIService aiService) {
		this.aiService = aiService;
	}

	@PostMapping("/get-response")
	public ResponseEntity<String> getAIResponse(@RequestBody String query,
			@RequestHeader(name = "userName", required = true) String userName) {
		String response = aiService.generateResponseFromAssistant(query, userName);
		return ResponseEntity.ok(response);
	}
}
