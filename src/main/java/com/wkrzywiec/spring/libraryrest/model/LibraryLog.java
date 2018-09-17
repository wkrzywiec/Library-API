package com.wkrzywiec.spring.libraryrest.model;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name="library_logs")
public class LibraryLog {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="level")
	private String level;
	
	@Column(name="message")
	private String message;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="book_id", insertable=false, updatable=false)
	@JsonBackReference
	private Book book;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	@JsonBackReference
	private User user;
	
	@Column(name="dated")
	private Timestamp dated;

	@Column(name="changed_by_username")
	private String changedByUsername;
}