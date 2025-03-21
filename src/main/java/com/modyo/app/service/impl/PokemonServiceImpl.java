package com.modyo.app.service.impl;

import com.modyo.app.constants.CommonConstants;
import com.modyo.app.exception.PokemonServiceException;
import com.modyo.app.models.Characteristic;
import com.modyo.app.models.Evolution;
import com.modyo.app.models.Meta;
import com.modyo.app.models.PokemonDetails;
import com.modyo.app.models.PokemonDetailsDto;
import com.modyo.app.models.PokemonDetailsResponse;
import com.modyo.app.models.PokemonResponseDto;
import com.modyo.app.models.PokenmonApiResponse;
import com.modyo.app.service.PokemonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
  private final RestTemplate restTemplate;
  private final PokemonAsyncService pokemonAsyncService;

  private final String apiUrl;

  public PokemonServiceImpl(RestTemplate restTemplate, PokemonAsyncService pokemonAsyncService,
                            @Value("${pokemon.api.base-url}") String apiUrl) {
    this.restTemplate = restTemplate;
    this.pokemonAsyncService = pokemonAsyncService;
    this.apiUrl = apiUrl;
  }

  @Override
  @Cacheable(value = "pokemonListCache", key = "#limit + '-' + #offset")
  public PokemonDetailsResponse fetchAllPokemon(int limit, int offset) {
    try {
    PokenmonApiResponse response = callApiPokemon(limit, offset);

    List<CompletableFuture<PokemonResponseDto>> resultFeatures = response.results().stream()
            .map(data -> pokemonAsyncService.getPokemonDetails(data.url()))
            .collect(Collectors.toList());

    CompletableFuture<Void> allOf = CompletableFuture.allOf(resultFeatures.toArray(new CompletableFuture[0]));
    allOf.join();

    List<PokemonResponseDto> result = resultFeatures.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    return new PokemonDetailsResponse(new Meta(response.count(), response.next(), response.previous()), result);
    } catch (RestClientException e){
      throw new PokemonServiceException(CommonConstants.POKEMON_API_FAILED, e);
    }
  }

  private PokenmonApiResponse callApiPokemon(int limit, int offset) {
    return restTemplate.getForEntity(
            UriComponentsBuilder.fromPath("/pokemon")
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .toUriString(), PokenmonApiResponse.class
    ).getBody();
  }

  @Override
  @Cacheable(value = "pokemonDetailsCache", key = "#pokemonId")
  public PokemonDetailsDto getPokemonById(String pokemonId) {
    String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .pathSegment("pokemon")
            .pathSegment(pokemonId)
            .toUriString();
    try {
      PokemonResponseDto pokemonDetails = pokemonAsyncService.getPokemonDetails(url).get();
      Characteristic description = pokemonAsyncService.getPokemonDescription(pokemonId).get();
      Evolution evolution = pokemonAsyncService.getPokemonEvolution(pokemonId).get();
      return PokemonDetailsDto.builder().information(pokemonDetails).description(description).evolutions(evolution)
              .build();
    } catch (InterruptedException | ExecutionException | RestClientException e) {
      throw new PokemonServiceException(CommonConstants.POKEMON_API_FAILED, e);
    }
  }

}
