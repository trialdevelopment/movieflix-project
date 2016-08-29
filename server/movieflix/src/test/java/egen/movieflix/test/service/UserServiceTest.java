package egen.movieflix.test.service;

import java.util.UUID;

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
import egen.movieflix.entity.User;
import egen.movieflix.exception.UserAlreadyExistsException;
import egen.movieflix.exception.UserNotFoundException;
import egen.movieflix.service.UserService;
import egen.movieflix.service.UserServiceImpl;
import egen.movieflix.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class UserServiceTest {
	
	@Mock
	private UserDao dao;
	
	@InjectMocks
	private UserService service = new UserServiceImpl();
	
	private User user;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setEmail("test@test.com");
		user.setFirstName("test");
		user.setLastName("test");
		user.setPassword("test");
		user.setId(UUID.randomUUID().toString());
	}
	
	@Test
	public void testFindAllUsers () {
		service.findAllUsers();
		Mockito.verify(dao).findAllUsers();
	}
	
	@Test
	public void testFindUserById () throws UserNotFoundException {
		Mockito.when(dao.findUserById(user.getId())).thenReturn(user);
		
		User actual = service.findUserById(user.getId());
		Assert.assertEquals(user, actual);
	}
	
	@Test(expected=UserNotFoundException.class)
	public void testFindUserByIdException () throws UserNotFoundException {
		Mockito.when(dao.findUserById(user.getId())).thenReturn(null);
		
		service.findUserById(user.getId());
	}
	
	@Test
	public void testFindUserByEmail () throws UserNotFoundException {
		Mockito.when(dao.findUserByEmail(user.getEmail())).thenReturn(user);
		
		User actual = service.findUserByEmail(user.getEmail());
		Assert.assertEquals(user, actual);
	}

	@Test(expected=UserNotFoundException.class)
	public void testFindUserByEmailException () throws UserNotFoundException {
		Mockito.when(dao.findUserByEmail(user.getEmail())).thenReturn(null);
		
		service.findUserByEmail(user.getEmail());
	}
	
	
	@Test(expected=UserAlreadyExistsException.class)
	public void testCreateUserException () throws UserAlreadyExistsException {
		Mockito.when(dao.findUserByEmail(user.getEmail())).thenReturn(user);
		service.createUser(user);
	}
	
	
	@Test
	public void testCreateUser () throws UserAlreadyExistsException {
		Mockito.when(dao.findUserByEmail(user.getEmail())).thenReturn(null);
		service.createUser(user);
		Mockito.verify(dao).createUser(user);
	}
	
	@Test(expected=UserNotFoundException.class)
	public void testUpdateUser () throws UserNotFoundException {
		Mockito.when(dao.findUserById(user.getId())).thenReturn(null);
		service.updateUser(user.getId(),user);
	}
	
	@Test
	public void testDeleteUser () throws UserNotFoundException{
		service.deleteUser(user.getId());
		Mockito.verify(dao).deleteUser(user.getId());
	}
}