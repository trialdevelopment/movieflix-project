package egen.movieflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import egen.movieflix.entity.Movie;
import egen.movieflix.exception.MovieAlreadyExistsException;
import egen.movieflix.exception.MovieNotFoundException;
import egen.movieflix.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/movies")
@Api(tags="movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find All movies",
	notes="Returns a list of the movies in the system.")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public List<Movie> findAllMovies() {
		return movieService.findAllMovies();
	}

	@RequestMapping(value="{name}",method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Get Movie By Name",
	notes="Returns Movie.")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public Movie getMovie(@PathVariable("name") String name) throws MovieNotFoundException {
		return movieService.findMovieByName(name);
	}


	@RequestMapping(method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Add Movie",
	notes="Add and return movie")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=400, message="Bad Request"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public Movie createMovie(@RequestBody Movie movie) throws MovieAlreadyExistsException {

		return movieService.createMovie(movie);
	}

	@RequestMapping(value="{id}/{rating}", 
			method=RequestMethod.PUT,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Update user Rating",
	notes="Update user rating for an existing Movie")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=400, message="Bad Request"),
			@ApiResponse(code=404, message="Not Found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public void updateRating(@PathVariable("id") String id,@PathVariable("rating") int rating) throws MovieNotFoundException {
		movieService.updateRating(id, rating);
	}


	@RequestMapping(value="{id}", 
			method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Update Movie",
	notes="Update an existing Movie")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=400, message="Bad Request"),
			@ApiResponse(code=404, message="Not Found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public Movie updateMovie (@PathVariable("id") String id, @RequestBody Movie movie) throws MovieNotFoundException, MovieAlreadyExistsException {
		return movieService.updateMovie(id, movie);
	}


	@RequestMapping(value="{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Delete Movie",
	notes="Delete an existing Movie")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=404, message="Not Found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public void deleteMovie (@PathVariable("id") String id) throws MovieNotFoundException {
		movieService.deleteMovie(id);
	}

}