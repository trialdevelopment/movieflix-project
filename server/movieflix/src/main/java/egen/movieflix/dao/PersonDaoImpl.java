package egen.movieflix.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import egen.movieflix.entity.Person;
import egen.movieflix.exception.PersonNotFoundException;

/**
 ********************************************************
 * UserDaoImpl 
 ******************************************************** 
 * 
 * This UserDaoImpl is the implementation of UserDao Interface.
 * The UserDaoImpl class contains the following methods:
 * 1.findAllUsers() - This method is used to get a list of all the users. 
 * 2.findUserById() - This method is used to search for a particular user by it's ID.
 * 3.findUser() - This method is used to authenticate a user by verifying login details matching with the database values.
 * 4.findUserByEmail() - This method is used to search for a particular user by it's Email.
 * 4.createUser() - This method is used to create a specific user.
 * 5.updateUser() - This method is used to update a particular user information.
 * 6.deleteUser() - This method deletes the requested user from the database.
 * 7.updateRating() - This method is used to update user rating for a movie by averaging it with the current value of the rating and the update. 
 * @author Jay Nagda.
 * @category REST API Backend- Egen Project
 */

@Repository
@Transactional
public class PersonDaoImpl implements PersonDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Person> findAllPeople() {
		TypedQuery<Person> query = em.createQuery("SELECT u FROM Person u ORDER BY u.email ASC", Person.class);
		List<Person> users = query.getResultList();
		return users;
	}

	@Override
	public Person findPersonById(String id) {
		return em.find(Person.class, id);
	}

	@Override
	public Person findPerson(String email,String password) {
		TypedQuery<Person> query = em.createNamedQuery("Person.findPerson", Person.class);
		query.setParameter("pEmail", email);
		query.setParameter("pPassword", password);
		List<Person> users = query.getResultList();
		if(users != null && users.size() == 1) {
			return users.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public Person findPersonByEmail(String email) {
		TypedQuery<Person> query = em.createNamedQuery("Person.findByEmail", Person.class);
		query.setParameter("pEmail", email);
		List<Person> users = query.getResultList();
		if(users != null && users.size() == 1) {
			return users.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	@Transactional
	public Person createPerson(Person user) {
		em.persist(user);
		return user;
	}

	@Override
	@Transactional
	public Person updatePerson(Person user) {
		return em.merge(user);
	}

	@Override
	@Transactional
	public void deletePerson (String id) throws PersonNotFoundException {
		Person existing =  findPersonById(id);
		if(existing == null) {
			throw new PersonNotFoundException();
		}
		else {
			em.remove(existing);
		}


		Query query = em.createQuery(
				"DELETE FROM Person m WHERE m.id = :pID");
		query.setParameter("pID", id).executeUpdate();

	}


}
