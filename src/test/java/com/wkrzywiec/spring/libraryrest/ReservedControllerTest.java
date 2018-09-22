package com.wkrzywiec.spring.libraryrest;

import static org.hamcrest.Matchers.hasSize;
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
public class ReservedControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void givenAllReservedURL_whenProvideURL_thenReceiveJSONOkRespond() throws Exception {
		mvc.perform(
        		get("/reserved").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isOk())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$._embedded.reservedList", hasSize(4)))
        ;
	}
	
	@Test
	public void givenFourthReservedId_whenProvideURL_thenReceiveJSONOkRespond() throws Exception {
		mvc.perform(
        		get("/reserved/4").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isOk())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.id", is(4)))
					.andExpect(jsonPath("$.book.googleId", is("7Q4R3RHe8AQC")))
					.andExpect(jsonPath("$.user.username", is("rob")))
					.andExpect(jsonPath("$.dated", is("2018-09-15T09:14:13.000+0000")))
					.andExpect(jsonPath("$.dueDate", is("2018-09-18")))
					.andExpect(jsonPath("$._links.self.href", is("http://localhost/reserved/4")))
					.andExpect(jsonPath("$._links.reserved.href", is("http://localhost/reserved")))
        ;
	}
	
	@Test
	public void givenInvalidBookId_whenProvideURL_thenReceiveJSONNotFoundRespond() throws Exception {
		mvc.perform(
        		get("/reserved/1500").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isNotFound())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$.status", is("404, Not Found")))
                	.andExpect(jsonPath("$.message", is("Could not find reservation with id: 1500")))
                	.andExpect(jsonPath("$.details", is("You can't make any action on a non-existing resource")))
        ;
	}
}
