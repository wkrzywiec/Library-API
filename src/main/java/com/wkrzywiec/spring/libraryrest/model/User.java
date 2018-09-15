package com.wkrzywiec.spring.libraryrest.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString(callSuper=true, exclude="password")
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="username", unique=true)
	private String username;
	
	@Column(name="email", unique=true)
	private String email;
	
	@Column(name="enable")
	private boolean enable;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="birthday")
	private Date birthday;
	
	@Column(name="address")
	private String address;

	@Column(name="postal")
	private String postalCode;
	
	@Column(name="city")
	private String city;
	
	@Column(name="record_created")
	private Timestamp recordCreated;

	@ManyToMany(fetch=FetchType.EAGER,
				cascade= {CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
				name="user_role",
				joinColumns=@JoinColumn(name="user_id"),
				inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles;
	/*
	@OneToMany(	mappedBy="user",
				cascade= {CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH},
				fetch=FetchType.LAZY)
	private Set<Reserved> reservedBooks; 
	
	@OneToMany(	mappedBy="user",
				cascade= {CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH},
				fetch=FetchType.LAZY)
	private Set<Borrowed> borrowedBooks;
	
	@ManyToMany(fetch=FetchType.EAGER,
			cascade= {CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="book_penalty",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="book_id"))
	private Set<BookPenalty> penalties;
	
	@OneToMany(	mappedBy="user",
			cascade= {CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH},
			fetch=FetchType.LAZY)
	private Set<UserLog> userLogs;
	
	@OneToMany(	mappedBy="user",
			cascade= {CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH},
			fetch=FetchType.LAZY)
	private Set<LibraryLog> libraryLogs;*/
	
	public User(String username, String email, boolean enable, String firstName, String lastName) {
		super();
		this.username = username;
		this.email = email;
		this.enable = enable;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public void addRole(Role role){
		
		if (roles == null){
			roles = new HashSet<Role>();
		}
		
		roles.add(role);
	}
}
