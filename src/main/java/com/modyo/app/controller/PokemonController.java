package com.modyo.app.controller;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import com.modyo.app.models.PokemonDetailsDto;
import com.modyo.app.models.PokemonDetailsResponse;
import com.modyo.app.service.PokemonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Modyo Pokemon Controller")
@RestController
@RequestMapping("v1/pokemons")
public class PokemonController {
  private final PokemonService pokemonService;

  public PokemonController(PokemonService pokemonService) {
    this.pokemonService = pokemonService;
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<PokemonDetailsResponse> getAllPokemons(
          @RequestParam(defaultValue = "20") int limit,
          @RequestParam(defaultValue = "20") int offset) {

    return ResponseEntity.ok(pokemonService.fetchAllPokemon(limit, offset));
  }

  @GetMapping(path = "{pokemonId}",produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<PokemonDetailsDto> getPokemonsDetails(
          @PathVariable("pokemonId") String pokemonId) {
    return ResponseEntity.ok(pokemonService.getPokemonById(pokemonId));
  }


}

