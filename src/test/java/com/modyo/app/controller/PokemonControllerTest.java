package com.modyo.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.app.models.PokemonDetailsDto;
import com.modyo.app.models.PokemonDetailsResponse;
import com.modyo.app.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
 class PokemonControllerTest {

  private PokemonController pokemonController;

  @Mock
  private PokemonServiceImpl pokemonService;


  @BeforeEach
  void setup() {
    pokemonController = new PokemonController(pokemonService);
  }

  @Test
  void testGetAllPokemonSucces() {
    PokemonDetailsResponse pokemonDetailsResponse= mock(PokemonDetailsResponse.class);
    when(pokemonService.fetchAllPokemon(anyInt(), anyInt()))
            .thenReturn(pokemonDetailsResponse);
    var responseEntity = pokemonController.getAllPokemons(20,20);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Mockito.verify(pokemonService, Mockito.times(1))
            .fetchAllPokemon (anyInt(), anyInt());
  }

  @Test
  void testGetPokemonsDetailsSucces() {
    PokemonDetailsDto pokemonDetailsResponse= mock(PokemonDetailsDto.class);
    when(pokemonService.getPokemonById(anyString()))
            .thenReturn(pokemonDetailsResponse);
    var responseEntity = pokemonController.getPokemonsDetails("pokemonId");
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Mockito.verify(pokemonService, Mockito.times(1))
            .getPokemonById (anyString());
  }
}
