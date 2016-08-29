package egen.movieflix.service;

import java.util.List;

import egen.movieflix.entity.Person;
import egen.movieflix.exception.PersonAlreadyExistsException;
import egen.movieflix.exception.PersonNotFoundException;

public interface PersonService {

	List<Person> findAllPeople();

	Person findPersonById(String id) throws PersonNotFoundException;

	Person findPerson(String email,String password) throws PersonNotFoundException;

	Person findPersonByEmail(String email) throws PersonNotFoundException;

	Person createPerson(Person user) throws PersonAlreadyExistsException;

	Person updatePerson(String id, Person user) throws PersonNotFoundException;

	void deletePerson(String id) throws PersonNotFoundException;

}
