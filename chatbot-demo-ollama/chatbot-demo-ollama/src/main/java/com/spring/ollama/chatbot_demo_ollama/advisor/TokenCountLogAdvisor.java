package com.spring.ollama.chatbot_demo_ollama.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;

import reactor.core.publisher.Flux;

public class TokenCountLogAdvisor implements CallAdvisor, StreamAdvisor {

	private Logger logger = LoggerFactory.getLogger(TokenCountLogAdvisor.class);

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest,
			StreamAdvisorChain streamAdvisorChain) {
		
		logger.info("TokenCountLogAdvisor called for streaming...");
		logger.info("TokenCountLogAdvisor - Requesting model: " + chatClientRequest);
		
		var response = streamAdvisorChain.nextStream(chatClientRequest);
		
		logger.info("TokenCountLogAdvisor - Receiving streaming response.");
		
		return response;
	}

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

		logger.info("TokenCountLogAdvisor called...");

		logger.info("TokenCountLogAdvisor - Requesting model: " + chatClientRequest);

		ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);

		logger.info("TokenCountLogAdvisor - Received response: " + response);

		// Extracting usage data from the response metadata
		Usage usageData = response.chatResponse().getMetadata().getUsage();

		if (usageData == null) {
			logger.warn("Usage data is not available in the response metadata.");
			return response;
		}

		// Logging token counts
		logger.info("Prompt Token Count: " + usageData.getPromptTokens());
		logger.info("Completion Token Count: " + usageData.getCompletionTokens());
		logger.info("Total Token Count: " + usageData.getTotalTokens());

		return response;
	}

}
