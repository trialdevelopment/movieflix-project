package egen.movieflix.test.dao;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egen.movieflix.dao.MovieDao;
import egen.movieflix.dao.MovieDaoImpl;
import egen.movieflix.entity.IMDB;
import egen.movieflix.entity.Movie;
import egen.movieflix.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class MovieDaoTest {
	
	@Mock
	private EntityManager em;
	
	@InjectMocks
	private MovieDao dao = new MovieDaoImpl();
	
	@Mock
	private TypedQuery<Movie> query;
	
	private Movie movie;
	
	private IMDB imdb;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		imdb = new IMDB();
		imdb.setImdbID("test");
		imdb.setId(UUID.randomUUID().toString());
		movie = new Movie();
		movie.setTitle("test");
		movie.setImdb(imdb);
		movie.setRating(5);
		movie.setMovieID(UUID.randomUUID().toString());
	}
	
	@Test
	public void testFindAllMovies() {
		List<Movie> expected = Arrays.asList(movie);
		
		Mockito.when(em.createQuery("SELECT m FROM Movie m", Movie.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
		List<Movie> movies = dao.findAllMovies();
		Assert.assertEquals(expected, movies);
	}
	
	@Test
	public void testFindMovieById() {
		Mockito.when(em.find(Movie.class, movie.getMovieID())).thenReturn(movie);
		
		Movie actual = dao.findMovieById(movie.getMovieID());
		Assert.assertEquals(movie, actual);
	}
	
	@Test
	public void testFindMovieByName () {
		List<Movie> expected = Arrays.asList(movie);
		Mockito.when(em.createNamedQuery("Movie.findByName", Movie.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
		Movie actual = dao.findMovieByName(movie.getTitle());
		Assert.assertEquals(movie, actual);
	}
	
	@Test
	public void testFindMovieByNameNull () {
		Mockito.when(em.createNamedQuery("Movie.findByName", Movie.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(null);
		
		Movie actual = dao.findMovieByName(movie.getTitle());
		Assert.assertEquals(null, actual);
	}
	
	@Test
	public void testCreateMovie () {
		dao.createMovie(movie);
		Mockito.verify(em).persist(movie);
	}
	
	@Test
	public void testUpdateMovie () {
		dao.updateMovie(movie);
		Mockito.verify(em).merge(movie);
	}
		
}