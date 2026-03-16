package com.example.taskmanager.task.GlobalException;

import com.example.taskmanager.task.*;
import com.example.taskmanager.task.DTO.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDueDateException.class)
    public ResponseEntity<ErrorDTO> handleDates(InvalidDueDateException ex , HttpServletRequest request){

        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                "INVALID_DUE_DATE",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUser(UserNotFoundException ex , HttpServletRequest request){

        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                "NO_USER",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(NoChangeDetectedException.class)
    public ResponseEntity<ErrorDTO> handleUpdate(NoChangeDetectedException ex , HttpServletRequest request){

        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                "NO_CHANGE_FOUND",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleTask(TaskNotFoundException ex , HttpServletRequest request){

        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                "TASK_NOT_FOUND",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }


    @ExceptionHandler(UnauthorizeAccessException.class)
    public ResponseEntity<ErrorDTO> handleAuth(UnauthorizeAccessException ex , HttpServletRequest request){

        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                "UNAUTHORIZED_USER",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Validation Failed",
                "VALIDATION_ERROR",
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDTO> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                "ACCESS_DENIED",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getName() + " should be of type " + ex.getRequiredType().getSimpleName(),
                "TYPE_MISMATCH",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    // triggers when request body is invalid JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Malformed JSON request",
                "INVALID_JSON",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneric(Exception ex, HttpServletRequest request) {
        ErrorDTO errorDTO = ErrorDTO.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                "Something went wrong",
                "INTERNAL_ERROR",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}
