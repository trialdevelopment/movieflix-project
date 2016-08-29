package egen.movieflix.dao;

import java.util.List;

import egen.movieflix.entity.User;
import egen.movieflix.exception.UserNotFoundException;

public interface UserDao {

	public List<User> findAllUsers ();
	public User findUserById(String id);
	public User findUser(String email,String password);
	public User findUserByEmail(String email);
	public User createUser(User user);
	public User updateUser(User user);
	public void deleteUser(String id) throws UserNotFoundException;
}