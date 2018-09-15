package com.wkrzywiec.spring.libraryrest.model;

import java.sql.Timestamp;

import javax.persistence.*;

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
@Table(name="user_logs")
public class UserLog {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="level")
	private String level;
	
	@Column(name="dated")
	private Timestamp dated;

	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;
	
	@Column(name="field")
	private String field;
	
	@Column(name="from_value")
	private String fromValue;
	
	@Column(name="to_value")
	private String toValue;
	
	@Column(name="message")
	private String message;
	
	@Column(name="changed_by_username")
	private String changedByUsername;
}
