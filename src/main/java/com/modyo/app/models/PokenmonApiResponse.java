package com.modyo.app.models;

import lombok.Generated;

import java.util.List;

@Generated
public record PokenmonApiResponse(int count, String next, String previous, List<NamedAPIResource> results) {
}

