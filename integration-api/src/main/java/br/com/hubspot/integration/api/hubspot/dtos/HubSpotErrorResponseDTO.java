package br.com.hubspot.integration.api.hubspot.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Resposta de erro da integração com a HubSpot")
public class HubSpotErrorResponseDTO {

    @Schema(description = "Response do erro", example = "RESPONSE_ERROR")
    private String response;

    @Schema(description = "Mensagem de status", example = "Error to call HubSpot")
    private String message;

    @Schema(description = "Código de status", example = "400")
    private int statusCode;




}
