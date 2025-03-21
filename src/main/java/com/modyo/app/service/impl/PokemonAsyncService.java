package com.modyo.app.service.impl;

import com.modyo.app.models.Characteristic;
import com.modyo.app.models.Evolution;
import com.modyo.app.models.PokemonDetails;
import com.modyo.app.models.PokemonResponseDto;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.concurrent.CompletableFuture;

@Component
public class PokemonAsyncService {
  private final RestTemplate restTemplate;

  public PokemonAsyncService(RestTemplate restTemplate){
    this.restTemplate = restTemplate;
  }
  @Async
  public CompletableFuture<PokemonResponseDto> getPokemonDetails(String url) {
    PokemonDetails response = restTemplate.exchange(url, HttpMethod.GET, null,
            PokemonDetails.class).getBody();
    return CompletableFuture.completedFuture(PokemonMapper.mapToPokemonResponseDto(response));
  }

  @Async
  public CompletableFuture<Characteristic> getPokemonDescription(String pokemonId) {
    String url = UriComponentsBuilder.fromHttpUrl("/characteristic")
            .pathSegment(pokemonId)
            .toUriString();

    Characteristic response = restTemplate.exchange(url, HttpMethod.GET, null,
            Characteristic.class).getBody();
    return CompletableFuture.completedFuture(response);
  }

  @Async
  public CompletableFuture<Evolution> getPokemonEvolution(String pokemonId) {
    String url = UriComponentsBuilder.fromHttpUrl("/evolution-chain")
            .pathSegment(pokemonId)
            .toUriString();
    Evolution response = restTemplate.exchange(url, HttpMethod.GET, null, Evolution.class).getBody();
    return CompletableFuture.completedFuture(response);
  }
}
