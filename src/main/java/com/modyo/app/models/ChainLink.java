package com.modyo.app.models;

import lombok.Generated;

import java.util.List;
@Generated
public record ChainLink(NamedAPIResource species, List<ChainLink> evolves_to) {
}
