package egen.movieflix.test.service;

import java.util.UUID;

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
import egen.movieflix.entity.Comment;
import egen.movieflix.entity.Movie;
import egen.movieflix.entity.User;
import egen.movieflix.service.CommentService;
import egen.movieflix.service.CommentServiceImpl;
import egen.movieflix.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class CommentServiceTest {
	
	@Mock
	private CommentDao dao;
	
	@InjectMocks
	private CommentService service = new CommentServiceImpl();
	
	private Movie movie;
	
	private User user;
	
	private Comment comment;
	
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
	public void testFindAllComments () {
		service.findAllComments();
		Mockito.verify(dao).findAllComments();
	}
	
	@Test
	public void testAddComment () {
		service.addComment(comment);
		Mockito.verify(dao).addComment(comment);
	}

	public void addComment(Comment any) {
		// TODO Auto-generated method stub
		
	}
	
}