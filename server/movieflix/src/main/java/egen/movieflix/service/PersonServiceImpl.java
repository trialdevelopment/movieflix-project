package egen.movieflix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egen.movieflix.dao.PersonDao;
import egen.movieflix.entity.Person;
import egen.movieflix.exception.PersonAlreadyExistsException;
import egen.movieflix.exception.PersonNotFoundException;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao dao;

	@Override
	public List<Person> findAllPeople() {
		return dao.findAllPeople();
	}

	@Override
	public Person findPersonById(String id) throws PersonNotFoundException {
		Person person =  dao.findPersonById(id);
		if(person == null) {
			throw new PersonNotFoundException();
		}
		else {
			return person;
		}
	}

	@Override
	public Person findPerson(String email,String password) throws PersonNotFoundException {
		Person person = dao.findPerson(email,password);
		if(person == null) {
			throw new PersonNotFoundException();
		}
		else {
			return person;
		}
	}

	@Override
	public Person findPersonByEmail(String email) throws PersonNotFoundException {
		Person person = dao.findPersonByEmail(email);
		if(person == null) {
			throw new PersonNotFoundException();
		}
		else {
			return person;
		}
	}

	@Override
	public Person createPerson(Person person) throws PersonAlreadyExistsException {
		Person existing =  dao.findPersonByEmail(person.getEmail());
		if(existing == null) {
			return dao.createPerson(person);
		}
		else {
			throw new PersonAlreadyExistsException();
		}
	}

	@Override
	public Person updatePerson(String id, Person person) throws PersonNotFoundException {
		Person existing =  dao.findPersonById(id);
		if(existing == null) {
			throw new PersonNotFoundException();
		}
		else {
			return dao.updatePerson(person);
		}
	}

	@Override
	public void deletePerson(String id) throws PersonNotFoundException {
		dao.deletePerson(id);
	}

}