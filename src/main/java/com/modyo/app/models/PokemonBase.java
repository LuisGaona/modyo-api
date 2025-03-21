package com.modyo.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PokemonBase {

  private String name;
  private List<PokemonType> types;
  private Integer weight;
  private List<PokemonAbility> abilities;
}
