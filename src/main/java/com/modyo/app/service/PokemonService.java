package com.modyo.app.service;

import com.modyo.app.models.PokemonDetailsDto;
import com.modyo.app.models.PokemonDetailsResponse;
import com.modyo.app.models.PokemonResponseDto;

public interface PokemonService {
PokemonDetailsResponse fetchAllPokemon (int limit, int offset);
  PokemonDetailsDto getPokemonById(String pokemonId);
}
