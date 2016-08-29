package egen.movieflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Movie already exists")
public class MovieAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 2916236655384340186L;

}