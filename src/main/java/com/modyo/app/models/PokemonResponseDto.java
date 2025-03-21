package com.modyo.app.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.experimental.SuperBuilder;

@Generated
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonPropertyOrder({"name", "pokemonImageUrl", "types", "weight", "abilities"})
public class PokemonResponseDto extends PokemonBase {
  private String pokemonImageUrl;

}
