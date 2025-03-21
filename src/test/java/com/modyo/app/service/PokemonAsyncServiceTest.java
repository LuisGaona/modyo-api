package com.modyo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.modyo.app.models.ChainLink;
import com.modyo.app.models.Characteristic;
import com.modyo.app.models.Description;
import com.modyo.app.models.Evolution;
import com.modyo.app.models.NamedAPIResource;
import com.modyo.app.models.PokemonAbility;
import com.modyo.app.models.PokemonDetails;
import com.modyo.app.models.PokemonResponseDto;
import com.modyo.app.models.PokemonType;
import com.modyo.app.service.impl.PokemonAsyncService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
class PokemonAsyncServiceTest {
  private PokemonAsyncService pokemonAsyncService;
  @Mock
  private RestTemplate restTemplate;

  @BeforeEach
  void setup() {
    pokemonAsyncService = new PokemonAsyncService(restTemplate);
  }

  @Test
  void testGetPokemonDetails() throws ExecutionException, InterruptedException {
    String url = "https://pokeapi.co/api/v2/pokemon/pikachu";
    PokemonDetails pokemonDetails = new PokemonDetails();
    pokemonDetails.setName("Pikachu");
    pokemonDetails.setWeight(60);
    pokemonDetails.setTypes((Collections.singletonList(
            new PokemonType(405, new NamedAPIResource("fuego", "https://example.com/pikachu.png")))));
    pokemonDetails.setAbilities((Collections.singletonList(
            new PokemonAbility(false, 405, new NamedAPIResource("jump", "https://example.com/pikachu.png")))));

    when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(PokemonDetails.class)))
            .thenReturn(ResponseEntity.ok(pokemonDetails));

    CompletableFuture<PokemonResponseDto> future = pokemonAsyncService.getPokemonDetails(url);

    PokemonResponseDto result = future.get();
    assertNotNull(result);
    assertEquals("Pikachu", result.getName());
    assertEquals(60, result.getWeight());
  }

  @Test
  void testGetPokemonDescription() throws ExecutionException, InterruptedException {
    String pokemonId = "pikachuId";
    String url = "/characteristic/pikachuId";
   Characteristic characteristic = Characteristic.builder().descriptions(Collections.singletonList(new Description("Pikachu is an Electric-type Pokémon",
            new NamedAPIResource("en","/characteristic/pikachuId")))).build();

    when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(Characteristic.class)))
            .thenReturn(ResponseEntity.ok(characteristic));

    CompletableFuture<Characteristic> future = pokemonAsyncService.getPokemonDescription(pokemonId);

    Characteristic result = future.get();
    assertNotNull(result);
    assertEquals(1, result.descriptions().size());
    assertEquals("en", result.descriptions().get(0).language().name());
  }

  @Test
  void testGetPokemonEvolution() throws ExecutionException, InterruptedException {
    String pokemonId = "pikachuId";
    String url = "/evolution-chain/pikachuId";

    NamedAPIResource species = new NamedAPIResource("pikachu", "url");
    ChainLink nextChainLink = new ChainLink(species, Collections.emptyList());
    ChainLink chainLink = new ChainLink(species, Collections.singletonList(nextChainLink));
    Evolution mockEvolution = new Evolution(chainLink);

    when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(Evolution.class)))
            .thenReturn(ResponseEntity.ok(mockEvolution));

    CompletableFuture<Evolution> future = pokemonAsyncService.getPokemonEvolution(pokemonId);

    Evolution result = future.get();
    assertNotNull(result);
    assertEquals(1, result.chain().evolves_to().size());
    assertEquals("pikachu", result.chain().species().name());
  }

}
