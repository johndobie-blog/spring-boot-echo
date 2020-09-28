package com.johndobie.springboot.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
@Configuration
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationErrors handleException(MethodArgumentNotValidException e) {
        List<ValidationError> validationErrors = processFieldErrors(e);
        return ValidationErrors.builder().validationErrors(validationErrors).build();
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationErrors  handleException(ConstraintViolationException e) {
        List<ValidationError> validationErrors = processConstraintViolations(e);
        return ValidationErrors.builder().validationErrors(validationErrors).build();
    }

    private List<ValidationError>  processFieldErrors(MethodArgumentNotValidException e) {
        List<ValidationError> validationErrors = new ArrayList<ValidationError>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {

            String code = fieldError.getCode();
            String source = fieldError.getObjectName() + "/" + fieldError.getField();
            String detail = fieldError.getField() + " " + fieldError.getDefaultMessage();

            ValidationError validationError = ValidationError
                                                      .builder().code(code).source(source).detail(detail).build();
            validationErrors.add(validationError);
        }

        return validationErrors;
    }

    private List<ValidationError>  processConstraintViolations(ConstraintViolationException e) {
        List<ValidationError> validationErrors = new ArrayList<ValidationError>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {

            String code = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            String source = violation.getPropertyPath().toString();
            String detail = violation.getMessage();

            ValidationError validationError = ValidationError
                                                      .builder().code(code).source(source).detail(detail).build();
            validationErrors.add(validationError);
        }
        return validationErrors;
    }

    private String getErrorBody(String body) {
        String formattedBody = "NO_CONTENT";
        if (body != null && body.length() > 0) {
            formattedBody = removeNewline(body);
        }
        return formattedBody;
    }

    private List<ValidationError>  buildError(String code, String detail, String source) {

        ValidationError validationError = ValidationError
                                                  .builder().code(code).source(source).detail(detail).build();

        List<ValidationError> validationErrors = new ArrayList<ValidationError>();
        validationErrors.add(validationError);

        return validationErrors;
    }

    private String removeNewline(String s) {
        return s.replaceAll("\\r\\n|\\r|\\n", " ");
    }


}
