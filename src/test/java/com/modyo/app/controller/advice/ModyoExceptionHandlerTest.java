package com.modyo.app.controller.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.modyo.app.models.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ExtendWith(MockitoExtension.class)
 class ModyoExceptionHandlerTest {

  @Mock
  private NoHandlerFoundException noHandlerFoundException;
  @Mock
  private RestClientException restClientException;

  private ModyoExceptionHandler modyoExceptionHandler;

  @BeforeEach
  void setup() {
    modyoExceptionHandler = new ModyoExceptionHandler();
  }

  @Test
  void testNoHandlerExceptionNotFound() {
    String errorMessage = "No data found";
    when(noHandlerFoundException.getMessage()).thenReturn(errorMessage);

    ResponseEntity<ErrorResponse> response = modyoExceptionHandler.handleNoHandlerFoundException(noHandlerFoundException);

    assertEquals(NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().message().contains(errorMessage));
    assertEquals(NOT_FOUND.value(), response.getStatusCode().value());
  }

  @Test
  void testInternalServerException() {
    String errorMessage = "Request Unauthorized, invalid token";
    when(restClientException.getMessage()).thenReturn(errorMessage);
    // Act
    ResponseEntity<ErrorResponse> response = modyoExceptionHandler.handleErrorInternalException(restClientException);
    // Assert
    assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().message().contains(errorMessage));
    assertEquals(INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value());
  }

}
