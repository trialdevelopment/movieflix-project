package egen.movieflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import egen.movieflix.entity.Person;
import egen.movieflix.exception.PersonAlreadyExistsException;
import egen.movieflix.exception.PersonNotFoundException;
import egen.movieflix.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
@Api(tags="users")
public class PersonController {

	@Autowired
	private PersonService service;

	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find All Users",
	notes="Returns a list of the users in the system.")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public List<Person> findAllPeople () {
		return service.findAllPeople();
	}

	@RequestMapping(value="{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find User By Id",
	notes="Returns a user by it's id if it exists.")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=404, message="Not Found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public Person findOne(@PathVariable("id") String id) throws PersonNotFoundException {
		return service.findPersonById(id);
	}

	@RequestMapping(value="{email}/{password}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find User By emailId",
	notes="Returns a user by it's email-id if it exists.")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=404, message="Not Found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public Person findUser(@PathVariable("email") String email,@PathVariable("password") String password) throws PersonNotFoundException {
		return service.findPerson(email,password);
	}

	@RequestMapping(method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Create User",
	notes="Create and return user")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=400, message="Bad Request"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public Person createUser (@RequestBody Person user) throws PersonAlreadyExistsException {
		return service.createPerson(user);
	}

	@RequestMapping(value="{id}", 
			method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Update User",
	notes="Update an existing user")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=400, message="Bad Request"),
			@ApiResponse(code=404, message="Not Found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public Person updateUser (@PathVariable("id") String id, @RequestBody Person user) throws PersonNotFoundException {
		return service.updatePerson(id, user);
	}

	@RequestMapping(value="{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Delete User",
	notes="Delete an existing user")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=404, message="Not Found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public void deleteUser (@PathVariable("id") String id) throws PersonNotFoundException {
		service.deletePerson(id);
	}
}