package augusto108.ces.appointmenttracker.handlers;

import augusto108.ces.appointmenttracker.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler
{

	private static ResponseEntity<ErrorResponse> notFoundErrorResponseEntity(
		String error,
		String message,
		HttpStatus status)
	{
		ErrorResponse response = new ErrorResponse(error, message, status);

		return ResponseEntity
			.status(status)
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
			.body(response);
	}

	@ExceptionHandler({EntityNotFoundException.class, NoHandlerFoundException.class})
	public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e)
	{
		return notFoundErrorResponseEntity(e.toString(), e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException e)
	{
		final String errorMessage = "Wrong property format: " + e.getMessage();
		return notFoundErrorResponseEntity(e.toString(), errorMessage, HttpStatus.BAD_REQUEST);
	}

	@Getter
	public static class ErrorResponse
	{

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
		private final LocalDateTime timestamp;

		private final String error;
		private final String message;
		private final HttpStatus status;
		private final long statusCode;

		public ErrorResponse(String error, String message, HttpStatus status)
		{
			this.timestamp = LocalDateTime.now();
			this.error = error;
			this.message = message;
			this.status = status;
			this.statusCode = status.value();
		}
	}
}
