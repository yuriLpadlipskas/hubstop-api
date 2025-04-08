package br.com.hubspot.integration.api.hubspot.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DefaultResponseDTO {

    @Schema(description = "Mensagem de status do retorno", example = "Successfully in your request")
    private String message;

    @Schema(description = "Status da operação", example = "true")
    private Boolean success;
}
