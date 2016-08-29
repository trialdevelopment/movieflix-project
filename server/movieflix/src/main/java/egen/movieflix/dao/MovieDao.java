package egen.movieflix.dao;

import java.util.List;

import egen.movieflix.entity.Movie;
import egen.movieflix.exception.MovieNotFoundException;

public interface MovieDao {

	Movie createMovie(Movie movie);
	List<Movie> findAllMovies();
	Movie findMovieByName(String name);
	Movie findMovieById(String id);
	Movie updateMovie(Movie movie);
	void updateRating(Movie movie,int rating);
	void deleteMovie(String movieID) throws MovieNotFoundException;

}