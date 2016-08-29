package egen.movieflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Person already exists")
public class PersonAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 546687436534995035L;

}
