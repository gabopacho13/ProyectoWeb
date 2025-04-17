package co.edu.javeriana.jpa_example2.exception_handler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import co.edu.javeriana.jpa_example2.dto.ErrorDto;

@ControllerAdvice
public class AppControlerAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NoSuchElementException e){
        return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorDto("Resource does not exist"));
    }
}
