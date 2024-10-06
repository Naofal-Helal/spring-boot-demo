package world.array.springboot.restapi;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * CustomizedResponseEntityExceptionHandler
 */
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  Logger logger = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleAllExceptions(
      Exception ex,
      WebRequest request) {
    var annotation = ex.getClass().getAnnotation(ResponseStatus.class);
    HttpStatus status;
    if (annotation != null) {
      status = annotation.code();
    } else {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      if (ex.getClass().getName().toLowerCase().contains("notfound")) {
        status = HttpStatus.NOT_FOUND;
      }
    }
    ErrorDetails errorDetails = new ErrorDetails(
        status.getReasonPhrase(),
        status.value(),
        ex.getMessage(),
        LocalDateTime.now());
    ResponseEntity<Object> response = ResponseEntity
        .status(status)
        .header("Content-Type", "application/problem+json")
        .body(errorDetails);
    return response;
  }

  class ValidationErrorDetails extends ErrorDetails {
    public Map<Object, Object> errors;

    public ValidationErrorDetails(String title, int status, String detail, LocalDateTime timestamp,
        Map<Object, Object> errors) {
      super(title, status, detail, timestamp);
      this.errors = errors;
    }

    public ValidationErrorDetails(Map<Object, Object> errors) {
      this.errors = errors;
    }

  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    var httpStatus = HttpStatus.valueOf(status.value());
    ValidationErrorDetails errorDetails = new ValidationErrorDetails(
        httpStatus.getReasonPhrase(),
        status.value(),
        "Invalid request content.",
        LocalDateTime.now(),
        ex.getAllErrors()
            .stream()
            .collect(Collectors.toMap(
                oe -> switch (oe) {
                  case FieldError fe -> fe.getField();
                  case ObjectError _oe -> _oe.getObjectName();
                },
                oe -> oe.getDefaultMessage())));
    ResponseEntity<Object> response = ResponseEntity
        .status(status)
        .header("Content-Type", "application/problem+json")
        .body(errorDetails);
    return response;
  }

}
