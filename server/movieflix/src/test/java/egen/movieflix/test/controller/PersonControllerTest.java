package egen.movieflix.test.controller;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import egen.movieflix.controller.PersonController;
import egen.movieflix.entity.Person;
import egen.movieflix.exception.PersonAlreadyExistsException;
import egen.movieflix.exception.PersonNotFoundException;
import egen.movieflix.service.PersonService;
import egen.movieflix.test.TestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class PersonControllerTest {
	
	
		//mock the dependencies of the controller
		@Mock
		private PersonService service;

		//controller that needs to be tested
		@InjectMocks
		private PersonController controller;
		
		private MockMvc mockMvc;

		private Person user;

		@Before
		public void setup() {
			//init mockito based mocks
			MockitoAnnotations.initMocks(this);
			
			user = new Person();
			user.setEmail("test@test.com");
			user.setFirstName("test");
			user.setLastName("test");
			user.setPassword("test");
			user.setId(UUID.randomUUID().toString());

			//init mockMvc with standalone setup for this controller test (used for unit test)
			//look at WebApplicationContext setup as well (mostly used for integration tests)
			mockMvc = MockMvcBuilders
						.standaloneSetup(controller)
						.build();
		}
		
		@Test
		public void testFindAllPeople() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/users"))
			.andExpect(MockMvcResultMatchers.status().isOk());

			Mockito.verify(service).findAllPeople();
		}

		@Test
		public void testFindOne() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/users/" + user.getId()))
					.andExpect(MockMvcResultMatchers.status().isOk());
			Mockito.verify(service).findPersonById(user.getId());
		}

		@Test
		public void testFindOneNotFound() throws Exception {
			Mockito.when(service.findPersonById("asdfasdf")).thenThrow(new PersonNotFoundException());
			
			mockMvc.perform(MockMvcRequestBuilders.get("/users/asdfasdf"))
					.andExpect(MockMvcResultMatchers.status().isNotFound());
		}
		
		@Test
		public void testFindUser() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/users/" + user.getEmail() +'/' +user.getPassword()))
					.andExpect(MockMvcResultMatchers.status().isOk());
			Mockito.verify(service).findPerson(user.getEmail(),user.getPassword());
		}

		@Test
		public void testFindUserNotFound() throws Exception {
			Mockito.when(service.findPerson("asdfasdf","dkjsdjksd")).thenThrow(new PersonNotFoundException());
			
			mockMvc.perform(MockMvcRequestBuilders.get("/users/asdfasdf/dkjsdjksd"))
					.andExpect(MockMvcResultMatchers.status().isNotFound());
		}

		@Test
		public void testCreateUser() throws Exception {
			//serialize to JSON string.
			//using Jackson's ObjectMapper to do that
			String requestBody = new ObjectMapper().writeValueAsString(user);
			
			mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isOk());
			
			Mockito.verify(service).createPerson(Mockito.any(Person.class));
		}
		
		@Test
		public void testCreateUserException() throws Exception {
			Mockito.when(service.createPerson(Mockito.any(Person.class))).thenThrow(new PersonAlreadyExistsException());
			
			String requestBody = new ObjectMapper().writeValueAsString(user);
			mockMvc.perform(MockMvcRequestBuilders.post("/users")
												  .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
												  .content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
		
		@Test
		public void testUpdateUser() throws Exception {
			String requestBody = new ObjectMapper().writeValueAsString(user);
			mockMvc.perform(MockMvcRequestBuilders.put("/users/" + user.getId())
												  .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
												  .content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isOk());
			
			Mockito.verify(service).updatePerson(Mockito.eq(user.getId()), Mockito.any(Person.class));
		}
		
		@Test
		public void testDeleteUser() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + user.getId()))
					.andExpect(MockMvcResultMatchers.status().isOk());
			Mockito.verify(service).deletePerson(user.getId());
		}

}