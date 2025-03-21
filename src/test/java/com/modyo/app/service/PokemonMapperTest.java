package com.modyo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.app.models.NamedAPIResource;
import com.modyo.app.models.PokemonAbility;
import com.modyo.app.models.PokemonDetails;
import com.modyo.app.models.PokemonResponseDto;
import com.modyo.app.models.PokemonSprites;
import com.modyo.app.models.PokemonType;
import com.modyo.app.service.impl.PokemonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
 class PokemonMapperTest {
  private PokemonMapper pokemonMapper;

  @BeforeEach
  void setup() {
    pokemonMapper = new PokemonMapper();
  }

  @Test
  void testMapToPokemonResponseDtoWithValidData() {
    PokemonDetails pokemonDetails = mock(PokemonDetails.class);
    PokemonSprites sprites = mock(PokemonSprites.class);
    when(pokemonDetails.getName()).thenReturn("Pikachu");
    when(pokemonDetails.getTypes()).thenReturn((Collections.singletonList(
            new PokemonType(405, new NamedAPIResource("fuego", "https://example.com/pikachu.png")))));
    when(pokemonDetails.getWeight()).thenReturn(60);
    when(pokemonDetails.getAbilities()).thenReturn((Collections.singletonList(
            new PokemonAbility(false, 405, new NamedAPIResource("jump", "https://example.com/pikachu.png")))));
    when(pokemonDetails.getSprites()).thenReturn(sprites);
    when(sprites.getFrontDefault()).thenReturn("https://example.com/pikachu.png");

    PokemonResponseDto result = PokemonMapper.mapToPokemonResponseDto(pokemonDetails);

    assertNotNull(result);
    assertEquals("Pikachu", result.getName());
    assertEquals(60, result.getWeight());
    assertEquals(1, result.getAbilities().size());
    assertEquals("https://example.com/pikachu.png", result.getPokemonImageUrl());
  }

  @Test
  void testMapToPokemonResponseDtoWithNullDetails() {
    PokemonResponseDto result = PokemonMapper.mapToPokemonResponseDto(null);
    assertNull(result);
  }
}
