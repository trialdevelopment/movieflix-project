package egen.movieflix.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import egen.movieflix.entity.User;
import egen.movieflix.exception.UserNotFoundException;

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
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<User> findAllUsers() {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.email ASC", User.class);
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	public User findUserById(String id) {
		return em.find(User.class, id);
	}

	@Override
	public User findUser(String email,String password) {
		TypedQuery<User> query = em.createNamedQuery("User.findUser", User.class);
		query.setParameter("pEmail", email);
		query.setParameter("pPassword", password);
		List<User> users = query.getResultList();
		if(users != null && users.size() == 1) {
			return users.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public User findUserByEmail(String email) {
		TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
		query.setParameter("pEmail", email);
		List<User> users = query.getResultList();
		if(users != null && users.size() == 1) {
			return users.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	@Transactional
	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	@Override
	@Transactional
	public User updateUser(User user) {
		return em.merge(user);
	}

	@Override
	@Transactional
	public void deleteUser (String id) throws UserNotFoundException {
		User existing =  findUserById(id);
		if(existing == null) {
			throw new UserNotFoundException();
		}
		else {
			em.remove(existing);
		}


		Query query = em.createQuery(
				"DELETE FROM User m WHERE m.id = :pID");
		query.setParameter("pID", id).executeUpdate();

	}


}
