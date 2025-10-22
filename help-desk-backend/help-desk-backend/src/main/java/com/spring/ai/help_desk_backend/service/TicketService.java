package com.spring.ai.help_desk_backend.service;

import com.spring.ai.help_desk_backend.entity.Ticket;

public interface TicketService {

	// create ticket
	Ticket createTicket(Ticket ticket);
	
	// update ticket
	Ticket updateTicket(Ticket ticket);
	
	// get ticket by id
	Ticket getTicketById(Long ticketId);
	
	// get ticket by username
	Ticket getTicketByUserName(String userName);
	
}
