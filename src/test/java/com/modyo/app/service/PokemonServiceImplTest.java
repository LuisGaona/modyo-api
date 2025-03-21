package com.modyo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.modyo.app.constants.CommonConstants;
import com.modyo.app.exception.PokemonServiceException;
import com.modyo.app.models.Characteristic;
import com.modyo.app.models.Evolution;
import com.modyo.app.models.NamedAPIResource;
import com.modyo.app.models.PokemonDetailsDto;
import com.modyo.app.models.PokemonDetailsResponse;
import com.modyo.app.models.PokemonResponseDto;
import com.modyo.app.models.PokenmonApiResponse;
import com.modyo.app.service.impl.PokemonAsyncService;
import com.modyo.app.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
class PokemonServiceImplTest {
  private PokemonServiceImpl pokemonService;
  @Mock
  private RestTemplate restTemplate;
  @Mock
  private PokemonAsyncService pokemonAsyncService;


  @BeforeEach
  void setup() {
    pokemonService = new PokemonServiceImpl(restTemplate,pokemonAsyncService,"https://pokeapi.co/");
  }

  @Test
  void testFetchAllPokemon() {
    int limit = 10;
    int offset = 0;

    PokenmonApiResponse mockApiResponse = mock(PokenmonApiResponse.class);
    when(restTemplate.getForEntity(anyString(), eq(PokenmonApiResponse.class)))
            .thenReturn(ResponseEntity.ok(mockApiResponse));
    when(mockApiResponse.results()).thenReturn(Collections.singletonList(new NamedAPIResource("picachu","/pokemon")));
    when(mockApiResponse.count()).thenReturn(2);

    CompletableFuture<PokemonResponseDto> future = CompletableFuture.completedFuture(PokemonResponseDto.builder().pokemonImageUrl("pokemom").build());
    when(pokemonAsyncService.getPokemonDetails(anyString())).thenReturn(future);

    PokemonDetailsResponse response1 = pokemonService.fetchAllPokemon(limit, offset);
    assertNotNull(response1);
    assertEquals("pokemom", response1.pokemons().get(0).getPokemonImageUrl());
  }

  @Test
  void testGetPokemonById(){
    String pokemonId = "pikachu";
    String pokemonUrl = "https://pokeapi.co/pokemon/pikachu";

    PokemonResponseDto mockPokemonResponse = mock(PokemonResponseDto.class);
    Characteristic mockDescription = mock(Characteristic.class);
    Evolution mockEvolution = mock(Evolution.class);

    when(pokemonAsyncService.getPokemonDetails(pokemonUrl)).thenReturn(CompletableFuture.completedFuture(mockPokemonResponse));
    when(pokemonAsyncService.getPokemonDescription(pokemonId)).thenReturn(CompletableFuture.completedFuture(mockDescription));
    when(pokemonAsyncService.getPokemonEvolution(pokemonId)).thenReturn(CompletableFuture.completedFuture(mockEvolution));
    PokemonDetailsDto result = pokemonService.getPokemonById(pokemonId);
    assertNotNull(result);

  }

  @Test
  void testGetPokemonByIdWhenRestClientExceptionIsThrown() {

    String pokemonId = "charizard";
    String url = "https://pokeapi.co/pokemon/charizard";

    when(pokemonAsyncService.getPokemonDetails(url)).thenThrow(RestClientException.class);

    PokemonServiceException exception = assertThrows(PokemonServiceException.class, () -> {
      pokemonService.getPokemonById(pokemonId);
    });

    assertEquals(CommonConstants.POKEMON_API_FAILED, exception.getMessage());
    verify(pokemonAsyncService, times(1)).getPokemonDetails(url);
  }

  @Test
  void testGetPokemonById_WhenInterruptedExceptionIsThrown() {
    String pokemonId = "bulbasaur";
    String url = "https://pokeapi.co/pokemon/bulbasaur";

    doAnswer(invocation -> {
      throw new InterruptedException("Mocked InterruptedException");
    }).when(pokemonAsyncService).getPokemonDetails(url);

    PokemonServiceException exception = assertThrows(PokemonServiceException.class, () -> {
      pokemonService.getPokemonById(pokemonId);
    });

    assertEquals(CommonConstants.POKEMON_API_FAILED, exception.getMessage());
    verify(pokemonAsyncService, times(1)).getPokemonDetails(url);
  }

}
