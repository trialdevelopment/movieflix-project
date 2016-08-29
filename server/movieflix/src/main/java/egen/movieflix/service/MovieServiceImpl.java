package egen.movieflix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egen.movieflix.dao.MovieDao;
import egen.movieflix.entity.Movie;
import egen.movieflix.exception.MovieAlreadyExistsException;
import egen.movieflix.exception.MovieNotFoundException;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	MovieDao movieDao;

	@Override
	public List<Movie> findAllMovies() {
		return movieDao.findAllMovies();
	}

	@Override
	public Movie createMovie(Movie movie) throws MovieAlreadyExistsException {

		Movie existing = movieDao.findMovieByName(movie.getTitle());

		if(existing == null)
		{
			return movieDao.createMovie(movie);
		}

		else {

			throw new MovieAlreadyExistsException();
		}

	}

	@Override
	public Movie updateMovie(String id, Movie movie) throws MovieNotFoundException, MovieAlreadyExistsException {
		Movie existing = movieDao.findMovieById(id);

		if(existing != null)
		{
			if(!existing.getTitle().equalsIgnoreCase(movie.getTitle())){

				if(movieDao.findMovieByName(movie.getTitle())==null){
					return movieDao.updateMovie(movie);
				}
				else{
					throw new MovieAlreadyExistsException();
				}
			}
			else {
				return movieDao.updateMovie(movie);
			}
		}
		else {
			throw new MovieNotFoundException();
		}
	}

	@Override
	public void deleteMovie(String id) throws MovieNotFoundException {

		movieDao.deleteMovie(id);

	}

	@Override
	public void updateRating(String id, int rating) throws MovieNotFoundException {
		Movie existing = movieDao.findMovieById(id);

		if(existing != null)
		{
			movieDao.updateRating(existing,rating);
		}

		else {
			throw new MovieNotFoundException();
		}
	}

	@Override
	public Movie findMovieByName(String name) throws MovieNotFoundException {
		Movie existing = movieDao.findMovieByName(name);
		if(existing !=null){
			return movieDao.findMovieByName(name);
		}
		else {
			throw new MovieNotFoundException();
		}

	}


}