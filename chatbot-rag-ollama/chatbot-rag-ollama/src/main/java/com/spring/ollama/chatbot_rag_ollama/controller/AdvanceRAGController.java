package com.spring.ollama.chatbot_rag_ollama.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ollama.chatbot_rag_ollama.service.AdvanceRAGService;

@RestController
@RequestMapping("/advance-rag")
public class AdvanceRAGController {

	private AdvanceRAGService advanceRAGService;
	
	public AdvanceRAGController(AdvanceRAGService advanceRAGService) {
		this.advanceRAGService = advanceRAGService;
	}
	
	@GetMapping("/advanceRagPipeline")
	public ResponseEntity<String> advanceRagPipeline(@RequestParam(value = "q", required = true) String query,
			@RequestHeader(value="userId", required=true) String userId) {

		// Advance RAG pipeline flow
		String response = this.advanceRAGService.advanceRagPipeline(query, userId);
		return ResponseEntity.ok(response);
	}
	
}
