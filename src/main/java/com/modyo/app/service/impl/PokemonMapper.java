package com.modyo.app.service.impl;
import com.modyo.app.models.PokemonDetails;
import com.modyo.app.models.PokemonResponseDto;

public class PokemonMapper {

  public static PokemonResponseDto mapToPokemonResponseDto(PokemonDetails pokemonDetails) {
    if (pokemonDetails == null) {
      return null;
    }

    String photoUrl = null;
    if (pokemonDetails.getSprites() != null) {
      photoUrl = pokemonDetails.getSprites().getFrontDefault();
    }

    return PokemonResponseDto.builder()
            .name(pokemonDetails.getName())
            .pokemonImageUrl(photoUrl)
            .types(pokemonDetails.getTypes())
            .weight(pokemonDetails.getWeight())
            .abilities(pokemonDetails.getAbilities()).build();
  }
}
