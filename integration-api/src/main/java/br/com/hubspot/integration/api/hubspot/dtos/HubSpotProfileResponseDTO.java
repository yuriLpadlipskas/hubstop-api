package br.com.hubspot.integration.api.hubspot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HubSpotProfileResponseDTO(
        String token,
        String user,
        @JsonProperty("hub_domain") String hubDomain,
        @JsonProperty("hub_id") Integer hubId,
        @JsonProperty("app_id") Integer appId,
        @JsonProperty("expires_in") Integer expiresIn,
        @JsonProperty("user_id") Integer userId,
        @JsonProperty("token_type") String tokenType
) {}
