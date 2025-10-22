package com.spring.ai.help_desk_backend.service;

public interface AIService {

	String generateResponseFromAssistant(String query, String userName);
}
