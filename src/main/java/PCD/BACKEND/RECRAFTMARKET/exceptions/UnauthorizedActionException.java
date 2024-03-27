package PCD.BACKEND.RECRAFTMARKET.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String message){
        super(message);
    }
}


//HttpStatus.OK: Represents the HTTP status code 200, indicating that the request has succeeded.
//
//HttpStatus.CREATED: Represents the HTTP status code 201, indicating that a new resource has been
// successfully created as a result of the request.
//
//HttpStatus.BAD_REQUEST: Represents the HTTP status code 400, indicating that the server could not
// understand the request due to invalid syntax or other client-side errors.
//
//HttpStatus.UNAUTHORIZED: Represents the HTTP status code 401, indicating that the request requires
// user authentication.
//
//HttpStatus.FORBIDDEN: Represents the HTTP status code 403, indicating that the server understood the
// request but refuses to authorize it.
//
//HttpStatus.NOT_FOUND: Represents the HTTP status code 404, indicating that the requested resource
// could not be found on the server.
//
//HttpStatus.METHOD_NOT_ALLOWED: Represents the HTTP status code 405, indicating that the method
// specified in the request is not allowed for the resource identified by the request URI.
//
//HttpStatus.INTERNAL_SERVER_ERROR: Represents the HTTP status code 500, indicating that the server
// encountered an unexpected condition that prevented it from fulfilling the request.
//
//HttpStatus.SERVICE_UNAVAILABLE: Represents the HTTP status code 503, indicating that the server is
// currently unable to handle the request due to temporary overloading or maintenance of the server.