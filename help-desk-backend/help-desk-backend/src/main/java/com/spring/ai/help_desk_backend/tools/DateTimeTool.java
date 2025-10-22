package com.spring.ai.help_desk_backend.tools;

import java.time.format.DateTimeFormatter;

import org.springframework.ai.tool.annotation.Tool;

public class DateTimeTool {

	@Tool(description = "Get the current date and time in ISO-8601 format")
	public String getCurrentDateTime() {
		return java.time.LocalDateTime.now()
				.format(DateTimeFormatter.ISO_DATE_TIME)
				.toString();
	}
	
}
