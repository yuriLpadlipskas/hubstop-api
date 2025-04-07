package br.com.hubspot.integration.api.hubspot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponseDTO(
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") Integer expiresIn
) {}
