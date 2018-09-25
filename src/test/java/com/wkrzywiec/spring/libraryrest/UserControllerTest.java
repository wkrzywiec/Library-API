package com.wkrzywiec.spring.libraryrest;

import static org.hamcrest.Matchers.hasSize;
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
import com.wkrzywiec.spring.libraryrest.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void givenAllUserURL_whenCallGETMethod_thenReceiveJSONOkRespond() throws Exception {
		mvc.perform(
        		get("/users").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isOk())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$._embedded.userList", hasSize(24)))
        ;
	}
	
	@Test
	public void givenFirstUserId_whenCallGETMethod_thenReceiveJSONOkRespond() throws Exception {
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
					.andExpect(jsonPath("$.roles", hasSize(2)))
					.andExpect(jsonPath("$.roles[0].id", is(1)))
					.andExpect(jsonPath("$.roles[0].name", is("USER")))
					.andExpect(jsonPath("$.roles[1].id", is(2)))
					.andExpect(jsonPath("$.roles[1].name", is("ADMIN")))
					.andExpect(jsonPath("$._links.self.href", is("http://localhost/users/1")))
					.andExpect(jsonPath("$._links.users.href", is("http://localhost/users")))
        ;
	}
	
	@Test
	public void givenInvalidUserId_whenCallGETMethod_thenReceiveErrorJSONNotFoundRespond() throws Exception {
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
	
	@Test
	@Sql(scripts="/delete-inserted-user.sql", 
	executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void givenNewUser_whenCallPOSTMethod_thenSaveNewUserAndReceiveJSONRespond() throws Exception {
		
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
				//.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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
	@Sql(scripts="/insert-deleted-user.sql", 
		executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void givenValidUserIdWithNoReferenceToOtherTable_whenCallDELETEMethod_thenDeleteUserAndReceiveNoContentRespond() throws Exception {
		mvc.perform(
        		delete("/users/8").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isNoContent())
        ;
		
		mvc.perform(
        		get("/users/8").contentType(MediaType.APPLICATION_JSON))
                	.andExpect(status().isNotFound())
        ;
	}
	
	@Test
	public void givenValidUserIdThatIsReferenceInOtherTable_whenCallDELETEMethod_thenNotDeleteAndReceiveErrorJSONRespond() throws Exception {
		mvc.perform(
        		delete("/users/4").contentType(MediaType.APPLICATION_JSON))
                	.andDo(print())
                	.andExpect(status().isBadRequest())
                	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                	.andExpect(jsonPath("$.status", is("400, Bad Request")))
                	.andExpect(jsonPath("$.message", is("could not execute statement")))
                	.andExpect(jsonPath("$.details", is("Reason: ConstraintViolationException. This entity has some constrains that doesn't allow to proceed.")))
        ;
		
		mvc.perform(
        		get("/users/4").contentType(MediaType.APPLICATION_JSON))
                	.andExpect(status().isOk())
        ;
	}	
}
