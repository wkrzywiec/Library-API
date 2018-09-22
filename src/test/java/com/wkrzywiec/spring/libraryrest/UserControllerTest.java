package com.wkrzywiec.spring.libraryrest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

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
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void givenAllUserURL_whenProvideURL_thenReceiveJSONOkRespond() throws Exception {
		mvc.perform(
        		get("/users").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isOk())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$._embedded.userList", hasSize(24)))
        ;
	}
	
	@Test
	public void givenFirstUserId_whenProvideURL_thenReceiveJSONOkRespond() throws Exception {
		mvc.perform(
        		get("/users/1").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isOk())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.id", is(1)))
					.andExpect(jsonPath("$.username", is("admin")))
					.andExpect(jsonPath("$.email", is("edard.stark@winterfell.com")))
					.andExpect(jsonPath("$.firstName", is("Edard")))
					.andExpect(jsonPath("$.lastName", is("Stark")))
					.andExpect(jsonPath("$.phone", nullValue(String.class)))
					.andExpect(jsonPath("$.birthday", nullValue(String.class)))
					.andExpect(jsonPath("$.address", nullValue(String.class)))
					.andExpect(jsonPath("$.postalCode", nullValue(String.class)))
					.andExpect(jsonPath("$.city", nullValue(String.class)))
					.andExpect(jsonPath("$.recordCreated", is("2018-09-22T16:19:31.000+0000")))
					.andExpect(jsonPath("$.roles", hasSize(2)))
					.andExpect(jsonPath("$.roles[0].id", is(1)))
					.andExpect(jsonPath("$.roles[0].name", is("USER")))
					.andExpect(jsonPath("$.roles[1].id", is(2)))
					.andExpect(jsonPath("$.roles[1].name", is("ADMIN")))
					.andExpect(jsonPath("$.penalties", hasSize(0)))
					.andExpect(jsonPath("$.userLogs", hasSize(4)))
					.andExpect(jsonPath("$.libraryLogs", hasSize(3)))
					.andExpect(jsonPath("$._links.self.href", is("http://localhost/users/1")))
					.andExpect(jsonPath("$._links.users.href", is("http://localhost/users")))
        ;
	}
	
	@Test
	public void givenInvalidUserId_whenProvideURL_thenReceiveErrorJSONNotFoundRespond() throws Exception {
		mvc.perform(
        		get("/users/1500").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isNotFound())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$.status", is("404, Not Found")))
                	.andExpect(jsonPath("$.message", is("Could not find user with id: 1500")))
                	.andExpect(jsonPath("$.details", is("You can't make any action on a non-existing resource")))
        ;
	}
}
