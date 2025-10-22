package com.spring.ai.help_desk_backend.service.impl;

import org.springframework.stereotype.Service;

import com.spring.ai.help_desk_backend.entity.Ticket;
import com.spring.ai.help_desk_backend.repository.TicketRepository;
import com.spring.ai.help_desk_backend.service.TicketService;

import jakarta.transaction.Transactional;

@Service
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;

	public TicketServiceImpl(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@Transactional
	@Override
	public Ticket createTicket(Ticket ticket) {
//		ticket.setTicketId(null); // Ensure the ID is null for creation
		return ticketRepository.save(ticket);
	}

	@Override
	public Ticket getTicketById(Long ticketId) {
		return ticketRepository.findByTicketId(ticketId).orElse(null);
	}

	@Override
	public Ticket getTicketByUserName(String userName) {
		return ticketRepository.findByUserName(userName).orElse(null);
	}

	@Transactional
	@Override
	public Ticket updateTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

}
