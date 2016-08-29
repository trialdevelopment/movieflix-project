package egen.movieflix.service;

import java.util.List;

import egen.movieflix.entity.Movie;
import egen.movieflix.exception.MovieAlreadyExistsException;
import egen.movieflix.exception.MovieNotFoundException;

public interface MovieService {

	List<Movie> findAllMovies();

	Movie createMovie(Movie movie) throws MovieAlreadyExistsException;

	Movie updateMovie(String id,Movie movie) throws MovieNotFoundException,MovieAlreadyExistsException;

	void updateRating(String id,int rating) throws MovieNotFoundException;

	Movie findMovieByName(String name) throws MovieNotFoundException;

	void deleteMovie(String id) throws MovieNotFoundException;

}