package com.wkrzywiec.spring.libraryrest.model;

import java.util.List;

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
import javax.persistence.OneToOne;
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
@ToString
@NoArgsConstructor
@Entity
@Table(name="book")
@JsonIdentityInfo(
		generator=ObjectIdGenerators.PropertyGenerator.class,
		property="id",
		scope= Long.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="google_id")
	private String googleId;
	
	@Column(name="title")
	private String title;
	
	@ManyToMany(cascade= { CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="book_author",
			joinColumns=@JoinColumn(name="book_id"),
			inverseJoinColumns=@JoinColumn(name="author_id"))
	private List<Author> authors;
	
	@Column(name="publisher")
	private String publisher;
	
	@Column(name="published_date")
	private String publishedDate;
	
	@OneToOne(fetch=FetchType.EAGER,
			cascade= {CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH,
						CascadeType.REMOVE})
	@JoinColumn(name="isbn_id")
	private Isbn isbn;
	
	@Column(name="page_count")
	private int pageCount;
	
	@ManyToMany(cascade= {CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="book_bookcategory",
			joinColumns=@JoinColumn(name="book_id"),
			inverseJoinColumns=@JoinColumn(name="bookcategory_id"))
	private List<BookCategory> categories;
	
	@Column(name="rating")
	private double rating;
	
	@Column(name="image_link")
	private String imageLink;
	
	@Column(name="description")
	private String description;
	
	@OneToOne(mappedBy="book",
			fetch=FetchType.LAZY)
	@JsonIgnore
	private Reserved reserved;
	
	@OneToOne(mappedBy="book", 
			fetch=FetchType.LAZY)
	@JsonIgnore
	private Borrowed borrowed;
	
	@OneToMany(mappedBy="book",
			fetch=FetchType.LAZY)
	@JsonIgnore
	private List<LibraryLog> libraryLog;
	
}
