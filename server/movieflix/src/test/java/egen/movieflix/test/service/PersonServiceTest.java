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

import egen.movieflix.dao.PersonDao;
import egen.movieflix.entity.Person;
import egen.movieflix.exception.PersonAlreadyExistsException;
import egen.movieflix.exception.PersonNotFoundException;
import egen.movieflix.service.PersonService;
import egen.movieflix.service.PersonServiceImpl;
import egen.movieflix.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class PersonServiceTest {
	
	@Mock
	private PersonDao dao;
	
	@InjectMocks
	private PersonService service = new PersonServiceImpl();
	
	private Person user;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		user = new Person();
		user.setEmail("test@test.com");
		user.setFirstName("test");
		user.setLastName("test");
		user.setPassword("test");
		user.setId(UUID.randomUUID().toString());
	}
	
	@Test
	public void testFindAllPeople () {
		service.findAllPeople();
		Mockito.verify(dao).findAllPeople();
	}
	
	@Test
	public void testFindPersonById () throws PersonNotFoundException {
		Mockito.when(dao.findPersonById(user.getId())).thenReturn(user);
		
		Person actual = service.findPersonById(user.getId());
		Assert.assertEquals(user, actual);
	}
	
	@Test(expected=PersonNotFoundException.class)
	public void testFindPersonByIdException () throws PersonNotFoundException {
		Mockito.when(dao.findPersonById(user.getId())).thenReturn(null);
		
		service.findPersonById(user.getId());
	}
	
	@Test
	public void testFindPersonByEmail () throws PersonNotFoundException {
		Mockito.when(dao.findPersonByEmail(user.getEmail())).thenReturn(user);
		
		Person actual = service.findPersonByEmail(user.getEmail());
		Assert.assertEquals(user, actual);
	}

	@Test(expected=PersonNotFoundException.class)
	public void testFindPersonByEmailException () throws PersonNotFoundException {
		Mockito.when(dao.findPersonByEmail(user.getEmail())).thenReturn(null);
		
		service.findPersonByEmail(user.getEmail());
	}
	
	
	@Test(expected=PersonAlreadyExistsException.class)
	public void testCreatePersonException () throws PersonAlreadyExistsException {
		Mockito.when(dao.findPersonByEmail(user.getEmail())).thenReturn(user);
		service.createPerson(user);
	}
	
	
	@Test
	public void testCreatePerson () throws PersonAlreadyExistsException {
		Mockito.when(dao.findPersonByEmail(user.getEmail())).thenReturn(null);
		service.createPerson(user);
		Mockito.verify(dao).createPerson(user);
	}
	
	@Test(expected=PersonNotFoundException.class)
	public void testUpdatePerson () throws PersonNotFoundException {
		Mockito.when(dao.findPersonById(user.getId())).thenReturn(null);
		service.updatePerson(user.getId(),user);
	}
	
	@Test
	public void testDeletePerson () throws PersonNotFoundException{
		service.deletePerson(user.getId());
		Mockito.verify(dao).deletePerson(user.getId());
	}
}