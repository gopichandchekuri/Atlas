package com.atlas.backend.exception;

import com.atlas.backend.dto.ErrorResponse;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception){
        return buildErrorResponse(HttpStatus.NOT_FOUND,exception.getMessage(), List.of());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException exception){
        return buildErrorResponse(HttpStatus.CONFLICT,exception.getMessage(),List.of());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationFailure(MethodArgumentNotValidException exception) {
        List<String> fieldProblems=exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError ->fieldError.getField()+":" +fieldError.getDefaultMessage())
                .toList();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", fieldProblems);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableBody(Exception exception){
        return buildErrorResponse(HttpStatus.BAD_REQUEST,"Request body is missing or is not valid JSON",List.of());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleWrongParameterType(MethodArgumentTypeMismatchException exception) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                "Invalid value for parameter '" + exception.getName() + "'", List.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exception){
        if(exception instanceof org.springframework.web.ErrorResponse springError){
            HttpStatus httpStatus=HttpStatus.valueOf(springError.getStatusCode().value());
            return buildErrorResponse(httpStatus,httpStatus.getReasonPhrase(),List.of());

        }
        log.error("unexcepted error",exception);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong. Please try again later.", List.of());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(BusinessRuleException exception){
        return buildErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), List.of());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
   public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException exception){
        log.warn("Data integrity violation", exception);
        return buildErrorResponse(HttpStatus.CONFLICT, "The request conflicts with existing data",List.of() );

    }



    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus httpStatus, String message, List<String> details) {

        ErrorResponse errorResponse=new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message,
                details);
        return ResponseEntity.status(httpStatus).body(errorResponse);

    }


}
