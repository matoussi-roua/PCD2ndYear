//the ApiError class provides a standardized representation of errors in your API responses
package PCD.BACKEND.RECRAFTMARKET.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {
    private LocalDateTime timestamp; //represents the timestamp when the error occurred
    private HttpStatus status; //represents the HTTP status code associated with the error.
    //This field contains a human-readable message describing the error. It's often used to
    // provide additional context or details about the error condition.
    private String message;
    //This field holds a list of detailed error messages or error objects. It's optional
    // and can be used to include additional information about the error
    private List errors;
}
