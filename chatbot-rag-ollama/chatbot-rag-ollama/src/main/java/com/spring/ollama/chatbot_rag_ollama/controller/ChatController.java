package com.spring.ollama.chatbot_rag_ollama.controller;

import java.util.List;

import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ollama.chatbot_rag_ollama.entity.BasicResponseModel;
import com.spring.ollama.chatbot_rag_ollama.service.ChatService;

@RestController
@RequestMapping("/chatapi")
public class ChatController {

	private ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}
}
