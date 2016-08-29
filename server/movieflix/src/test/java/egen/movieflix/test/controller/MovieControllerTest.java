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

import egen.movieflix.controller.MovieController;
import egen.movieflix.entity.IMDB;
import egen.movieflix.entity.Movie;
import egen.movieflix.exception.MovieAlreadyExistsException;
import egen.movieflix.exception.MovieNotFoundException;
import egen.movieflix.service.MovieService;
import egen.movieflix.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class MovieControllerTest {
	
	    //mock the dependencies of the controller
		@Mock
		private MovieService service;

		//controller that needs to be tested
		@InjectMocks
		private MovieController controller;
			
		private MockMvc mockMvc;

		private Movie movie;
		
		private IMDB imdb;
		
		private int rating;

		@Before
		public void setup() {
			//init mockito based mocks
			MockitoAnnotations.initMocks(this);
				
			imdb = new IMDB();
			imdb.setImdbID("test");
			imdb.setId(UUID.randomUUID().toString());
			rating =2;
			movie = new Movie();
			movie.setTitle("test");
			movie.setImdb(imdb);
			movie.setMovieID(UUID.randomUUID().toString());
			
			//init mockMvc with standalone setup for this controller test (used for unit test)
			//look at WebApplicationContext setup as well (mostly used for integration tests)
			mockMvc = MockMvcBuilders
							.standaloneSetup(controller)
							.build();
		}
		
		@Test
		public void testFindAllMovies() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
			.andExpect(MockMvcResultMatchers.status().isOk());

			Mockito.verify(service).findAllMovies();
		}

		@Test
		public void testGetMovie() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/movies/" + movie.getTitle()))
					.andExpect(MockMvcResultMatchers.status().isOk());
			Mockito.verify(service).findMovieByName(movie.getTitle());
		}

		@Test
		public void testGetMovieNotFound() throws Exception {
			Mockito.when(service.findMovieByName("asdfasdf")).thenThrow(new MovieNotFoundException());
			
			mockMvc.perform(MockMvcRequestBuilders.get("/users/asdfasdf"))
					.andExpect(MockMvcResultMatchers.status().isNotFound());
		}
		
		@Test
		public void testCreateMovie() throws Exception {
			//serialize to JSON string.
			//using Jackson's ObjectMapper to do that
			String requestBody = new ObjectMapper().writeValueAsString(movie);
			
			mockMvc.perform(MockMvcRequestBuilders.post("/movies").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isOk());
			
			Mockito.verify(service).createMovie(Mockito.any(Movie.class));
		}
		
		@Test
		public void testCreateMovieException() throws Exception {
			Mockito.when(service.createMovie(Mockito.any(Movie.class))).thenThrow(new MovieAlreadyExistsException());
			
			String requestBody = new ObjectMapper().writeValueAsString(movie);
			mockMvc.perform(MockMvcRequestBuilders.post("/movies")
												  .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
												  .content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
		
		@Test
		public void testUpdateMovie() throws Exception {
			String requestBody = new ObjectMapper().writeValueAsString(movie);
			mockMvc.perform(MockMvcRequestBuilders.put("/movies/" + movie.getMovieID())
												  .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
												  .content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isOk());
			
			Mockito.verify(service).updateMovie(Mockito.eq(movie.getMovieID()), Mockito.any(Movie.class));
		}
		
		@Test
		public void testUpdateRating() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.put("/movies/" + movie.getMovieID() +'/' + rating))
					.andExpect(MockMvcResultMatchers.status().isOk());
			
			Mockito.verify(service).updateRating(Mockito.eq(movie.getMovieID()), Mockito.eq(rating));
		}
		
		@Test
		public void testDeleteMovie() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.delete("/movies/" + movie.getMovieID()))
					.andExpect(MockMvcResultMatchers.status().isOk());
			Mockito.verify(service).deleteMovie(movie.getMovieID());
		}
}