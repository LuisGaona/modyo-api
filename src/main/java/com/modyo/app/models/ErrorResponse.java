package com.modyo.app.models;

import lombok.Generated;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Generated
public record ErrorResponse(LocalDateTime time, String message, HttpStatus httpStatus) {
}