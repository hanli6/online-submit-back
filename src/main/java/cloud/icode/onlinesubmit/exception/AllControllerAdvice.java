package cloud.icode.onlinesubmit.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class AllControllerAdvice {
    public final static Logger logger = LoggerFactory.getLogger(AllControllerAdvice.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentHandler(IllegalArgumentException e) {
        logger.error("IllegalArgumentException-error->", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("MethodArgumentNotValidException->", e);

        return this.getErrorMessages(e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException e) {
        logger.error("BindException error->", e);
        return String.format("xxxxxx" + ":%s", this.getBindingErrorField(e.getBindingResult()));
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidFormatException.class)
    public String handleInvalidFormatException(InvalidFormatException e) {
        logger.error("InvalidFormatException error->", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(ConstraintViolationException e) {
        logger.error("ConstraintViolationException-error->", e);
        return e.getConstraintViolations().stream().map(this::getMessage).collect(Collectors.joining(";"));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        logger.error("Exception-error->", e);
        return "UNKNOWN_ERROR_MESSAGE";
    }

    private String getBindingErrorField(BindingResult bindingResult) {
        return bindingResult
                .getAllErrors()
                .stream()
                .map(this::getFieldName)
                .collect(Collectors.joining(";"));
    }

    private String getErrorMessages(BindingResult bindingResult) {
        return bindingResult
                .getAllErrors()
                .stream()
                .map(this::getMessage)
                .collect(Collectors.joining(";"));
    }

    private String getMessage(ObjectError error) {
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            return fieldError.getField() + ":" + fieldError.getDefaultMessage();
        }
        return error.getObjectName() + ":" + error.getDefaultMessage();
    }

    private String getFieldName(ObjectError error) {
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            return fieldError.getField();
        }
        return error.getObjectName();
    }


    private String getMessage(ConstraintViolation<?> violation) {
        return violation.getPropertyPath() + ":" + violation.getMessage();
    }
}