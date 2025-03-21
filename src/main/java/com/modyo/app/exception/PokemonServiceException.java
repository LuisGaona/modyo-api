package com.modyo.app.exception;

public class PokemonServiceException extends RuntimeException{

  public PokemonServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public PokemonServiceException(String message) {
    super(message);
  }
}
