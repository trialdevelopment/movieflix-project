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

import egen.movieflix.controller.UserController;
import egen.movieflix.entity.User;
import egen.movieflix.exception.UserAlreadyExistsException;
import egen.movieflix.exception.UserNotFoundException;
import egen.movieflix.service.UserService;
import egen.movieflix.test.TestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class UserControllerTest {
	
	
		//mock the dependencies of the controller
		@Mock
		private UserService service;

		//controller that needs to be tested
		@InjectMocks
		private UserController controller;
		
		private MockMvc mockMvc;

		private User user;

		@Before
		public void setup() {
			//init mockito based mocks
			MockitoAnnotations.initMocks(this);
			
			user = new User();
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
		public void testFindAllUsers() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/users"))
			.andExpect(MockMvcResultMatchers.status().isOk());

			Mockito.verify(service).findAllUsers();
		}

		@Test
		public void testFindOne() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/users/" + user.getId()))
					.andExpect(MockMvcResultMatchers.status().isOk());
			Mockito.verify(service).findUserById(user.getId());
		}

		@Test
		public void testFindOneNotFound() throws Exception {
			Mockito.when(service.findUserById("asdfasdf")).thenThrow(new UserNotFoundException());
			
			mockMvc.perform(MockMvcRequestBuilders.get("/users/asdfasdf"))
					.andExpect(MockMvcResultMatchers.status().isNotFound());
		}
		
		@Test
		public void testFindUser() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/users/" + user.getEmail() +'/' +user.getPassword()))
					.andExpect(MockMvcResultMatchers.status().isOk());
			Mockito.verify(service).findUser(user.getEmail(),user.getPassword());
		}

		@Test
		public void testFindUserNotFound() throws Exception {
			Mockito.when(service.findUser("asdfasdf","dkjsdjksd")).thenThrow(new UserNotFoundException());
			
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
			
			Mockito.verify(service).createUser(Mockito.any(User.class));
		}
		
		@Test
		public void testCreateUserException() throws Exception {
			Mockito.when(service.createUser(Mockito.any(User.class))).thenThrow(new UserAlreadyExistsException());
			
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
			
			Mockito.verify(service).updateUser(Mockito.eq(user.getId()), Mockito.any(User.class));
		}
		
		@Test
		public void testDeleteUser() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + user.getId()))
					.andExpect(MockMvcResultMatchers.status().isOk());
			Mockito.verify(service).deleteUser(user.getId());
		}

}