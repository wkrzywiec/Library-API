package com.wkrzywiec.spring.libraryrest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void givenAllBooksURL_whenProvideURL_thenReceiveJSONOkRespond() throws Exception {
		mvc.perform(
        		get("/books").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isOk())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$._embedded.bookList", hasSize(28)))
        ;
	}
	
	@Test
	public void givenTenthUserId_whenProvideURL_thenReceiveJSONOkRespond() throws Exception {
		mvc.perform(
        		get("/books/10").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isOk())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.id", is(10)))
					.andExpect(jsonPath("$.googleId", is("7Q4R3RHe8AQC")))
					.andExpect(jsonPath("$.title", is("A Dance with Dragons")))
					.andExpect(jsonPath("$.authors", hasSize(1)))
					.andExpect(jsonPath("$.authors[0].id", is(6)))
					.andExpect(jsonPath("$.authors[0].name", is("George R. R. Martin")))
					.andExpect(jsonPath("$.publisher", is("Random House Publishing Group")))
					.andExpect(jsonPath("$.publishedDate", is("2011-07-12")))
					.andExpect(jsonPath("$.isbn.id", is(10)))
					.andExpect(jsonPath("$.isbn.isbn10", is("0553905651")))
					.andExpect(jsonPath("$.isbn.isbn13", is("9780553905656")))
					.andExpect(jsonPath("$.pageCount", is(1040)))
					.andExpect(jsonPath("$.categories", hasSize(3)))
					.andExpect(jsonPath("$.categories[0].id", is(1)))
					.andExpect(jsonPath("$.categories[0].name", is("Fiction / Action & Adventure")))
					.andExpect(jsonPath("$.categories[1].id", is(11)))
					.andExpect(jsonPath("$.categories[1].name", is("Fiction / Science Fiction / Action & Adventure")))
					.andExpect(jsonPath("$.categories[2].id", is(12)))
					.andExpect(jsonPath("$.categories[2].name", is("Fiction / Fantasy / Epic")))
					.andExpect(jsonPath("$.rating", is(3.5)))
					.andExpect(jsonPath("$.imageLink", is("http://books.google.com/books/content?id=7Q4R3RHe8AQC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70E7ghngLwyzjH06O8JLiHQwmJ1nWmgdGYPoqUZeuhu6nZxZvuiMnYspZZU2pcabdEuevVsWIKHlZEDrI0fazN_76fxMJLrAF_BDfG5iLBcRLgOWeug9HATPTOChfZvKchtZY_Y&source=gbs_api")))
					.andExpect(jsonPath("$.libraryLog", hasSize(1)))
					.andExpect(jsonPath("$.libraryLog[0].id", is(4)))
					.andExpect(jsonPath("$.libraryLog[0].level", is("INFO")))
					.andExpect(jsonPath("$.libraryLog[0].message", is("Reserved")))
					.andExpect(jsonPath("$.libraryLog[0].dated", is("2018-09-15T09:14:13.000+0000")))
					.andExpect(jsonPath("$.libraryLog[0].changedByUsername", is("rob")))
					.andExpect(jsonPath("$._links.self.href", is("http://localhost/books/10")))
					.andExpect(jsonPath("$._links.books.href", is("http://localhost/books")))
        ;
	}
	
	@Test
	public void givenInvalidBookId_whenProvideURL_thenReceiveJSONNotFoundRespond() throws Exception {
		mvc.perform(
        		get("/books/1500").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isNotFound())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$.status", is("404, Not Found")))
                	.andExpect(jsonPath("$.message", is("Could not find book with id: 1500")))
                	.andExpect(jsonPath("$.details", is("You can't make any action on a non-existing resource")))
        ;
	}
}
