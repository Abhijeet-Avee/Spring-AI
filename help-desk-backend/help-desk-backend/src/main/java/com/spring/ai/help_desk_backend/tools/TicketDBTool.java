package com.spring.ai.help_desk_backend.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.spring.ai.help_desk_backend.entity.Ticket;
import com.spring.ai.help_desk_backend.service.TicketService;

@Component
public class TicketDBTool {

	private final TicketService ticketService;

	public TicketDBTool(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	// create a ticket
	@Tool(description = "This tools help to create a new ticket in the database")
	public Ticket createTicket(
			@ToolParam(description = "Ticket fields required to create a new ticket") Ticket ticket) {
		try {
			System.out.println("Creating ticket: " + ticket);
			return ticketService.createTicket(ticket);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// get ticket by user name
	@Tool(description = "This tools used to get a help desk ticket detail by username from the database")
	public Ticket getTicketByUserName(@ToolParam(description = "Username of the ticket owner") String userName) {
		return ticketService.getTicketByUserName(userName);
	}

	// update a ticket
	@Tool(description = "This tool is used to update a help desk ticket in the database")
	public Ticket updateTicket(
			@ToolParam(description = "updated ticket details based on existing ticket id") Ticket ticket) {
		return ticketService.updateTicket(ticket);
	}

	// get current date time
	@Tool(description = "This tool helps to get the current system date and time")
	public String getCurrentDateTime() {
		return String.valueOf(System.currentTimeMillis());
	}
}
