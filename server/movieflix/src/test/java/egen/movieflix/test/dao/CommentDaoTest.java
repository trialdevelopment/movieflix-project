package egen.movieflix.test.dao;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egen.movieflix.dao.CommentDao;
import egen.movieflix.dao.CommentDaoImpl;
import egen.movieflix.entity.Comment;
import egen.movieflix.entity.Movie;
import egen.movieflix.entity.User;
import egen.movieflix.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class CommentDaoTest {
	
	@Mock
	private EntityManager em;
	
	@InjectMocks
	private CommentDao dao = new CommentDaoImpl();
	
	@Mock
	private TypedQuery<Comment> query;
	
	private Comment comment;
	
	private Movie movie;
	
	private User user;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		user = new User();
		user.setEmail("test@test.com");
		user.setFirstName("test");
		user.setLastName("user");
		user.setPassword("test");
		user.setId(UUID.randomUUID().toString());
		
		movie = new Movie();
		movie.setTitle("test");
		movie.setMovieID(UUID.randomUUID().toString());
		
		comment = new Comment();
		comment.setCommentID(UUID.randomUUID().toString());
		comment.setComment("test");
		comment.setMovie(movie);
		comment.setUser(user);
	}
	
	@Test
	public void testFindAllComments() {
		List<Comment> expected = Arrays.asList(comment);
		
		Mockito.when(em.createQuery("SELECT c FROM Comment c", Comment.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
	}
	
	@Test
	public void testAddComment () {
		dao.addComment(comment);
		Mockito.verify(em).persist(comment);
	}
}