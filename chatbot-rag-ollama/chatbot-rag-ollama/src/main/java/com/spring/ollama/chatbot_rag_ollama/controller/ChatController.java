package com.spring.ollama.chatbot_rag_ollama.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ollama.chatbot_rag_ollama.service.ChatService;

@RestController
@RequestMapping("/chatapi")
public class ChatController {

	private ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}
	
	@GetMapping("/rag-chat")
	public ResponseEntity<String> vectorStoreRAGChat(@RequestParam(value = "q", required = true) String query,
			@RequestHeader(value="userId", required=true) String userId) {

		// Calling the service to get the response from vector store with RAG and DB chat memory
		String response = this.chatService.vectorStoreChatwithRAGandDBChatMemory(query, userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/rag-ques-advisor")
	public ResponseEntity<String> questionAdvisorRAGChat(@RequestParam(value = "q", required = true) String query,
			@RequestHeader(value="userId", required=true) String userId) {

		// Calling the service to get the response from vector store with RAG and QuestionAnswerAdvisor
		String response = this.chatService.ragChatWithQuestionAdvisor(query, userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/rag-ques-advisor-searchreq")
	public ResponseEntity<String> ragChatWithQuestionAdvisorSearchRequest(@RequestParam(value = "q", required = true) String query,
			@RequestHeader(value="userId", required=true) String userId) {

		// Calling the service to get the response from vector store with RAG and QuestionAnswerAdvisor with SearchRequest
		String response = this.chatService.ragChatWithQuestionAdvisorSearchRequest(query, userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/rag-ques-advisor-searchreq-dynamicfilter")
	public ResponseEntity<String> ragChatWithQuesAdvisorSearchReqDynamicFilter(@RequestParam(value = "q", required = true) String query,
			@RequestHeader(value="userId", required=true) String userId) {

		// Calling the service to get the response from vector store with RAG and QuestionAnswerAdvisor 
		//with SearchRequest and dynamic filtering using QuestionAnswerAdvisor.FILTER_EXPRESSION
		String response = this.chatService.ragChatWithQuesAdvisorSearchReqDynamicFilter(query, userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/rag-ques-custom-propmt-template")
	public ResponseEntity<String> ragChatWithCustomPromptTemplatesForContext(@RequestParam(value = "q", required = true) String query,
			@RequestHeader(value="userId", required=true) String userId) {

		// Calling the service to get the response from vector store with RAG and QuestionAnswerAdvisor 
		//with SearchRequest and dynamic filtering using QuestionAnswerAdvisor.FILTER_EXPRESSION
		String response = this.chatService.ragChatWithCustomPromptTemplates(query, userId);
		return ResponseEntity.ok(response);
	}
}
