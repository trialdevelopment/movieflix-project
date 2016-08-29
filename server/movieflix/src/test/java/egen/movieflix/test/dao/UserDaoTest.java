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

import egen.movieflix.dao.UserDao;
import egen.movieflix.dao.UserDaoImpl;
import egen.movieflix.entity.User;
import egen.movieflix.test.TestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class UserDaoTest {
	
	@Mock
	private EntityManager em;
	
	@InjectMocks
	private UserDao dao = new UserDaoImpl();
	
	@Mock
	private TypedQuery<User> query;
	
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
	}
	
	@Test
	public void testFindAllUsers() {
		List<User> expected = Arrays.asList(user);
		
		Mockito.when(em.createQuery("SELECT u FROM User u ORDER BY u.email ASC", User.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
		List<User> users = dao.findAllUsers();
		Assert.assertEquals(expected, users);
	}
	
	@Test
	public void testFindUserById() {
		Mockito.when(em.find(User.class, user.getId())).thenReturn(user);
		
		User actual = dao.findUserById(user.getId());
		Assert.assertEquals(user, actual);
	}
	
	@Test
	public void testFindUser() {
		List<User> expected = Arrays.asList(user);
		Mockito.when(em.createNamedQuery("User.findUser", User.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
		User actual = dao.findUser(user.getEmail(),user.getPassword());
		Assert.assertEquals(user, actual);
	}
	
	@Test
	public void testFindUserNull () {
		Mockito.when(em.createNamedQuery("User.findUser", User.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(null);
		
		User actual = dao.findUser(user.getEmail(),user.getPassword());
		Assert.assertEquals(null, actual);
	}
	
	@Test
	public void testFindUserByEmail () {
		List<User> expected = Arrays.asList(user);
		Mockito.when(em.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
		User actual = dao.findUserByEmail(user.getEmail());
		Assert.assertEquals(user, actual);
	}
	
	@Test
	public void testFindUserByEmailNull () {
		Mockito.when(em.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(null);
		
		User actual = dao.findUserByEmail(user.getEmail());
		Assert.assertEquals(null, actual);
	}
	
	@Test
	public void testCreateUser () {
		dao.createUser(user);
		Mockito.verify(em).persist(user);
	}
	
	@Test
	public void testUpdateUser () {
		dao.updateUser(user);
		Mockito.verify(em).merge(user);
	}
}