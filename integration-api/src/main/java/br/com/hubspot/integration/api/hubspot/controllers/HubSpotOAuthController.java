package br.com.hubspot.integration.api.hubspot.controllers;

import br.com.hubspot.integration.api.hubspot.dtos.HubSpotErrorResponseDTO;
import br.com.hubspot.integration.api.hubspot.services.oauth.IHubSpotOAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequestMapping("/oauth")
public class HubSpotOAuthController {

    private final IHubSpotOAuthService service;

    public HubSpotOAuthController(IHubSpotOAuthService service) {
        this.service = service;
    }

    @Operation(
            summary = "Gerar e retornar a URL de autorização.",
            description = " Endpoint responsável por gerar e retornar a URL de autorização para iniciar o " +
                    "fluxo OAuth com o HubSpot."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "Redireciona para a autenticação da HubSpot. A URL de redirecionamento será informada no header 'Location'.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error to call HubSpot",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubSpotErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error to call HubSpot",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubSpotErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/authorize-url")
    public ResponseEntity<String> getAuthorizationUrl() {
        String url = service.generateAuthorizationUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    @Operation(
            summary = "Trocar o código de autorização pelo token de acesso",
            description = "Endpoint recebe o código de autorização fornecido pelo HubSpot e realiza a" +
                    "troca pelo token de acesso."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "Redirecionamento para a home da HubSpot. A URL de redirecionamento será informada no header 'Location'.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error to call HubSpot",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubSpotErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error to call HubSpot",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubSpotErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code) {
        String url = service.exchageCodeToToken(code);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

}
