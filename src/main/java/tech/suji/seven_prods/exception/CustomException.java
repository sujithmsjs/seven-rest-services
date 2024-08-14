package tech.suji.seven_prods.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final HttpStatus httpStatus;
	private final String source;

	public CustomException() {
		this("Custom Exception", HttpStatus.BAD_REQUEST);

	}

	public CustomException(String message) {
		this(message, HttpStatus.BAD_REQUEST);
	}

	public CustomException(Exception ex) {
		this(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	public CustomException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
		this.source = "Undefined";
	}

	public CustomException(Exception ex, HttpStatus httpStatus) {
		this(ex.getMessage(), httpStatus);
	}

	public CustomException(String message, String source, HttpStatus httpStatus) {
		super(message);
		this.source = source;
		this.httpStatus = httpStatus;
	}

}
