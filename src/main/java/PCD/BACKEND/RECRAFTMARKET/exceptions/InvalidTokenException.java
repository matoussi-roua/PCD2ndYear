//represents an exception that indicates an authentication token is invalid
package PCD.BACKEND.RECRAFTMARKET.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends  RuntimeException{
    public InvalidTokenException(String message) { super(message);}
}
