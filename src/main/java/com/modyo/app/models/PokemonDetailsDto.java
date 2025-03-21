package com.modyo.app.models;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
@Builder
public class PokemonDetailsDto {
  private PokemonResponseDto information;
  private Characteristic description;
  private Evolution evolutions;
}