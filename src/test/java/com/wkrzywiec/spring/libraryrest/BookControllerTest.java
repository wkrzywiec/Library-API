package com.wkrzywiec.spring.libraryrest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wkrzywiec.spring.libraryrest.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void givenAllBooksURL_whenCallGETMethod_thenReceiveJSONOkRespond() throws Exception {
		mvc.perform(
        		get("/books/").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isOk())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$._embedded.bookList", hasSize(28)))
        ;
	}
	
	@Test
	public void givenTenthUserId_whenCallGETMethod_thenReceiveJSONOkRespond() throws Exception {
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
					.andExpect(jsonPath("$._links.self.href", is("http://localhost/books/10")))
					.andExpect(jsonPath("$._links.books.href", is("http://localhost/books")))
        ;
	}
	
	@Test
	public void givenInvalidBookId_whenCallGETMethod_thenReceiveJSONNotFoundRespond() throws Exception {
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
	
	@Test
	@Sql(scripts="/delete-inserted-user.sql", 
	executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void givenNewBook_whenCallPOSTMethod_thenSaveNewBookAndReceiveJSONRespond() throws Exception {
		
		User newUser = new User();
		newUser.setUsername("wojtek");
		newUser.setEmail("wojtek@mail.com");
		newUser.setEnable(true);
		newUser.setFirstName("Wojtek");
		newUser.setLastName("Krzywiec");
		newUser.setPhone("123456789");
		newUser.setAddress("Main street 12");
		newUser.setPostalCode("74-234");
		newUser.setCity("Warsaw");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(newUser);
		
		mvc.perform(
				post("/users").contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username", is(newUser.getUsername())))
				.andExpect(jsonPath("$.email", is(newUser.getEmail())))
				.andExpect(jsonPath("$.firstName", is(newUser.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(newUser.getLastName())))
				.andExpect(jsonPath("$.phone", is(newUser.getPhone())))
				.andExpect(jsonPath("$.birthday", nullValue(String.class)))
				.andExpect(jsonPath("$.address", is(newUser.getAddress())))
				.andExpect(jsonPath("$.postalCode", is(newUser.getPostalCode())))
				.andExpect(jsonPath("$.city", is(newUser.getCity())))
				.andExpect(jsonPath("$.roles", hasSize(1)))
				.andExpect(jsonPath("$.roles[0].id", is(1)))
				.andExpect(jsonPath("$.roles[0].name", is("USER")))
		;
	}
	
	@Test
	public void givenEmptyNewUser_whenCallPOSTMethod_thenReceiveJSONErrorRespond() throws Exception {
		
		User newUser = new User();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(newUser);
	    
	    mvc.perform(
				post("/users").contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("400, Bad Request")))
            	.andExpect(jsonPath("$.message", is("could not execute statement, SQL State: 23000")))
            	.andExpect(jsonPath("$.details", is("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Column 'email' cannot be null")))
		;
	}
	
	@Test
	@Sql(scripts="/insert-deleted-book.sql", 
		executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void givenValidUserIdWithNoReferenceToOtherTable_whenCallDELETEMethod_thenDeleteUserAndReceiveNoContentRespond() throws Exception {
		mvc.perform(
        		delete("/books/2").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isNoContent())
        ;
		
		mvc.perform(
        		get("/books/2").contentType(MediaType.APPLICATION_JSON))
                	.andExpect(status().isNotFound())
        ;
	}
	
	@Test
	public void givenValidUserIdThatIsReferenceInOtherTable_whenCallDELETEMethod_thenNotDeleteAndReceiveErrorJSONRespond() throws Exception {
		mvc.perform(
        		delete("/books/16").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isBadRequest())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$.status", is("400, Bad Request")))
                	.andExpect(jsonPath("$.message", is("could not execute statement, SQL State: 23000")))
                	.andExpect(jsonPath("$.details", is("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint fails (`library_db`.`borrowed`, CONSTRAINT `FK_BOOK_BORROWED` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)")))
        ;
		
		mvc.perform(
        		get("/books/16").contentType(MediaType.APPLICATION_JSON))
                	.andExpect(status().isOk())
        ;
	}	
}
