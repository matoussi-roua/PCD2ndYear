//When an ExpiredTokenException is thrown, Spring will automatically return an HTTP 401 response to the client
package PCD.BACKEND.RECRAFTMARKET.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//HttpStatus.UNAUTHORIZED corresponds to the HTTP status code 401, which
// indicates that the client's request lacks valid authentication credentials
// or the provided token has expired.
@ResponseStatus(HttpStatus.UNAUTHORIZED) // when this exception is thrown httpStatus.Unauthorized should be returned
public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String message){super(message);}
}
