package com.wkrzywiec.spring.libraryrest.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(
		generator=ObjectIdGenerators.PropertyGenerator.class,
		property="id",
		scope= Long.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="username", unique=true)
	private String username;
	
	@Column(name="password")
	@JsonIgnore
	private String password;
	
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
	
	@OneToMany(mappedBy="user",
			cascade= {CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH},
				fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Reserved> reservedBooks; 
	
	@OneToMany(	mappedBy="user",
				cascade= {CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH},
				fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Borrowed> borrowedBooks;
	
	@ManyToMany(fetch=FetchType.EAGER,
			cascade= {CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="book_penalty",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="book_id"))
	private List<BookPenalty> penalties;
	
	@OneToMany(	mappedBy="user",
			cascade= {CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH},
			fetch=FetchType.LAZY)
	private List<UserLog> userLogs;
	
	@OneToMany(	mappedBy="user",
			cascade= {CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH},
			fetch=FetchType.LAZY)
	private List<LibraryLog> libraryLogs;
	
	public User(String username, String email, boolean enable, String firstName, String lastName, String password) {
		super();
		this.username = username;
		this.email = email;
		this.enable = enable;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	public void addRole(Role role){
		
		if (roles == null){
			roles = new HashSet<Role>();
		}
		
		roles.add(role);
	}
}
