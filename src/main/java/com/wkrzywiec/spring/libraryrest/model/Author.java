package com.wkrzywiec.spring.libraryrest.model;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString()
@NoArgsConstructor
@Entity
@Table(name="author")
public class Author {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", unique=true)
	private String name;
}
