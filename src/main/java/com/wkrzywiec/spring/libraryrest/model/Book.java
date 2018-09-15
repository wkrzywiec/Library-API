package com.wkrzywiec.spring.libraryrest.model;

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
@ToString
@NoArgsConstructor
@Entity
@Table(name="book")
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="google_id")
	private String googleId;
	
	@Column(name="title")
	private String title;
	
	@ManyToMany(fetch=FetchType.EAGER,
				cascade= { CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="book_author",
			joinColumns=@JoinColumn(name="book_id"),
			inverseJoinColumns=@JoinColumn(name="author_id"))
	private Set<Author> authors;
	
	@Column(name="publisher")
	private String publisher;
	
	@Column(name="published_date")
	private String publishedDate;
	
	@OneToOne(fetch=FetchType.EAGER,
			cascade= {CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="isbn_id")
	private Isbn isbn;
	
	@Column(name="page_count")
	private int pageCount;
	
	@ManyToMany(fetch=FetchType.EAGER,
				cascade= {CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="book_bookcategory",
			joinColumns=@JoinColumn(name="book_id"),
			inverseJoinColumns=@JoinColumn(name="bookcategory_id"))
	private Set<BookCategory> categories;
	
	@Column(name="rating")
	private double rating;
	
	@Column(name="image_link")
	private String imageLink;
	
	@Column(name="description")
	private String description;
	
	@OneToOne(mappedBy="book",
			cascade= {CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH},
			fetch=FetchType.LAZY)
	private Reserved reserved;
	
}
