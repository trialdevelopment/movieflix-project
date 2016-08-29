package egen.movieflix.dao;

import java.util.List;

import egen.movieflix.entity.Person;
import egen.movieflix.exception.PersonNotFoundException;

public interface PersonDao {

	public List<Person> findAllPeople ();
	public Person findPersonById(String id);
	public Person findPerson(String email,String password);
	public Person findPersonByEmail(String email);
	public Person createPerson(Person user);
	public Person updatePerson(Person user);
	public void deletePerson(String id) throws PersonNotFoundException;
}