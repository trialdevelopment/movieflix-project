package egen.movieflix.service;

import java.util.List;

import egen.movieflix.entity.User;
import egen.movieflix.exception.UserAlreadyExistsException;
import egen.movieflix.exception.UserNotFoundException;

public interface UserService {

	List<User> findAllUsers();

	User findUserById(String id) throws UserNotFoundException;

	User findUser(String email,String password) throws UserNotFoundException;

	User findUserByEmail(String email) throws UserNotFoundException;

	User createUser(User user) throws UserAlreadyExistsException;

	User updateUser(String id, User user) throws UserNotFoundException;

	void deleteUser(String id) throws UserNotFoundException;

}
