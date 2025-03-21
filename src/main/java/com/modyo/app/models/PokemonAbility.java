package com.modyo.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record PokemonAbility(@JsonProperty("is_hidden") Boolean isHidden, Integer slot, NamedAPIResource ability) {
}
