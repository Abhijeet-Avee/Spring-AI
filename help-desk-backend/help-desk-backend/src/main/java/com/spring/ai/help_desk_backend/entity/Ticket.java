package com.spring.ai.help_desk_backend.entity;

import java.time.LocalDateTime;

import com.spring.ai.help_desk_backend.enums.Priority;
import com.spring.ai.help_desk_backend.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "help_desk_tickets")
@Builder
@RequiredArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ticketId;

	@Lob
	private String summary;

	@Column(length = 1000)
	private String description;

	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Column(length = 500)
	private String category;

	@Column(length = 100)
	private String userName;

	@Enumerated(EnumType.STRING)
	private Status status;

	private LocalDateTime createDate;

	private LocalDateTime updateDate;

	@PrePersist
	void preSave() {
		this.createDate = LocalDateTime.now();
		this.updateDate = LocalDateTime.now();
	}

	@PreUpdate
	void preUpdate() {
		this.updateDate = LocalDateTime.now();
	}

	public Ticket() {
		super();
	}

	public Ticket(Long ticketId, String summary, String description, Priority priority, String category,
			String userName, Status status, LocalDateTime createDate, LocalDateTime updateDate) {
		super();
		this.ticketId = ticketId;
		this.summary = summary;
		this.description = description;
		this.priority = priority;
		this.category = category;
		this.userName = userName;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

}
