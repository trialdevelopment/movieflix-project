package egen.movieflix.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import egen.movieflix.entity.Movie;
import egen.movieflix.exception.MovieNotFoundException;

/**
 ********************************************************
 * MovieDaoImpl 
 ******************************************************** 
 * 
 * This MovieDaoImpl is the implementation of MovieDao Interface.
 * The MovieDaoImpl class contains the following methods:
 * 1.findAllMovies() - This method is used to get a list of all the movies. 
 * 2.findMovieByName() - This method is used to search for a particular movie by name.
 * 3.findMovieById() - This method is used to search for a particular movie by ID.
 * 4.createMovie() - This method is used to create a specific movie.
 * 5.updateMovie() - This method is used to update a particular movie.
 * 6.deleteMovie() - This method deletes the requested movie from the database.
 * 7.updateRating() - This method is used to update user rating for a movie by averaging it with the current value of the rating and the update. 
 * @author Jay Nagda.
 * @category REST API Backend- Egen Project
 */

@Repository
public class MovieDaoImpl implements MovieDao {

	@PersistenceContext
	EntityManager em;

	@Autowired
	CommentDao commentDao;

	@Override
	public List<Movie> findAllMovies() {
		TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
		List<Movie> movies = query.getResultList();
		return movies;
	}

	@Override
	@Transactional
	public Movie findMovieByName(String name) {
		TypedQuery<Movie> query = em.createNamedQuery("Movie.findByName", Movie.class);
		query.setParameter("pTitle", name);
		List<Movie> movies = query.getResultList();
		if(movies != null && movies.size() == 1) {
			return movies.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public Movie findMovieById(String id) {
		return em.find(Movie.class, id);
	}

	@Override
	@Transactional
	public Movie createMovie(Movie movie) {

		em.persist(movie);
		return movie;
	}

	@Override
	@Transactional
	public Movie updateMovie(Movie movie) {

		return em.merge(movie);
	}

	@Override
	@Transactional
	public void deleteMovie(String movieID) throws MovieNotFoundException{

		Movie existing = findMovieById(movieID);

		if(existing != null)
		{
			commentDao.deleteComment(existing);
			em.remove(existing);
		}
		else {
			throw new MovieNotFoundException();
		}
	}

	@Override
	@Transactional
	public void updateRating(Movie movie, int rating) {

		rating = (movie.getRating()+rating)/2;

		Query query = em.createQuery("UPDATE Movie m set m.rating =:pRating WHERE m.movieID = :pMovieID");
		query.setParameter("pRating", rating);
		query.setParameter("pMovieID", movie.getMovieID());
		query.executeUpdate();
	}


}