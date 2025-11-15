package com.firstflip.supermarket.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.firstflip.supermarket.exception.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Void> handleValidationException(NotFoundException ex, Model model) {
    return ResponseEntity.notFound().build();
  }
}
