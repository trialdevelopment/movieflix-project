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

import egen.movieflix.dao.PersonDao;
import egen.movieflix.dao.PersonDaoImpl;
import egen.movieflix.entity.Person;
import egen.movieflix.test.TestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class PersonDaoTest {
	
	@Mock
	private EntityManager em;
	
	@InjectMocks
	private PersonDao dao = new PersonDaoImpl();
	
	@Mock
	private TypedQuery<Person> query;
	
	private Person user;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		user = new Person();
		user.setEmail("test@test.com");
		user.setFirstName("test");
		user.setLastName("user");
		user.setPassword("test");
		user.setId(UUID.randomUUID().toString());
	}
	
	@Test
	public void testFindAllUsers() {
		List<Person> expected = Arrays.asList(user);
		
		Mockito.when(em.createQuery("SELECT u FROM Person u ORDER BY u.email ASC", Person.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
		List<Person> users = dao.findAllPeople();
		Assert.assertEquals(expected, users);
	}
	
	@Test
	public void testFindPersonById() {
		Mockito.when(em.find(Person.class, user.getId())).thenReturn(user);
		
		Person actual = dao.findPersonById(user.getId());
		Assert.assertEquals(user, actual);
	}
	
	@Test
	public void testFindPerson() {
		List<Person> expected = Arrays.asList(user);
		Mockito.when(em.createNamedQuery("Person.findPerson", Person.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
		Person actual = dao.findPerson(user.getEmail(),user.getPassword());
		Assert.assertEquals(user, actual);
	}
	
	@Test
	public void testFindPersonNull () {
		Mockito.when(em.createNamedQuery("Person.findPerson", Person.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(null);
		
		Person actual = dao.findPerson(user.getEmail(),user.getPassword());
		Assert.assertEquals(null, actual);
	}
	
	@Test
	public void testFindPersonByEmail () {
		List<Person> expected = Arrays.asList(user);
		Mockito.when(em.createNamedQuery("Person.findByEmail", Person.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
		Person actual = dao.findPersonByEmail(user.getEmail());
		Assert.assertEquals(user, actual);
	}
	
	@Test
	public void testFindPersonByEmailNull () {
		Mockito.when(em.createNamedQuery("Person.findByEmail", Person.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(null);
		
		Person actual = dao.findPersonByEmail(user.getEmail());
		Assert.assertEquals(null, actual);
	}
	
	@Test
	public void testCreatePerson () {
		dao.createPerson(user);
		Mockito.verify(em).persist(user);
	}
	
	@Test
	public void testUpdatePerson () {
		dao.updatePerson(user);
		Mockito.verify(em).merge(user);
	}
}