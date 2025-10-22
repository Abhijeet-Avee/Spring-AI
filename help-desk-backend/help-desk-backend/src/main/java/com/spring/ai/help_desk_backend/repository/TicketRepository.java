package com.spring.ai.help_desk_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.ai.help_desk_backend.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	Optional<Ticket> findByTicketId(Long ticketId);

	Optional<Ticket> findByUserName(String userName);

}
