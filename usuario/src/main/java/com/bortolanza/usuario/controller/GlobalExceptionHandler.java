package com.bortolanza.usuario.controller;


import com.bortolanza.usuario.infrastructure.exceptions.ConflictException;
import com.bortolanza.usuario.infrastructure.exceptions.IllegalArgumentException;
import com.bortolanza.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.bortolanza.usuario.infrastructure.exceptions.UnauthorizedException;
import com.bortolanza.usuario.infrastructure.exceptions.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                            HttpServletRequest request){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorMessage(HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Not Found"
                ));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflictException ex, HttpServletRequest request){
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(buildErrorMessage(HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Not Found"
        ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildErrorMessage(HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "User is not authorized"
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorMessage(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Bad Request"
        ));
    }

    private ErrorResponseDTO buildErrorMessage(int status, String message, String path, String error) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .error(error)
                .status(status)
                .path(path)
                .build();
    }
}
