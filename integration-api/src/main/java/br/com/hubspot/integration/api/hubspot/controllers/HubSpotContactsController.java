package br.com.hubspot.integration.api.hubspot.controllers;

import br.com.hubspot.integration.api.hubspot.dtos.ContactRequestDTO;
import br.com.hubspot.integration.api.hubspot.dtos.DefaultResponseDTO;
import br.com.hubspot.integration.api.hubspot.dtos.HubSpotErrorResponseDTO;
import br.com.hubspot.integration.api.hubspot.services.contacts.IHubSpotContactsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Hubspot Contacts", description = "Endpoints relacionados ao Hubspot contacts")
@RestController
@RequestMapping("/contacts")
public class HubSpotContactsController {

    private final IHubSpotContactsService service;

    public HubSpotContactsController(IHubSpotContactsService service) {
        this.service = service;
    }


    @Operation(
            summary = "Criar novo contato",
            description = "Endpoint que faz a criação de um Contato no CRM através da API. O endpoint\n" +
                    "deve respeitar as políticas de rate limit definidas pela API."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully to created contact",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponseDTO.class)
                    )
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
    @PostMapping("/create")
    public ResponseEntity<DefaultResponseDTO> createContact(@RequestBody ContactRequestDTO contact) {
        DefaultResponseDTO response = service.createContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(
            summary = "Chamar webhook",
            description = "Endpoint que escuta e processa eventos do tipo \"contact.creation\", enviados\n" +
                    "pelo webhook do HubSpot."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully to returned by HubSpot Webhook",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponseDTO.class)
                    )
            ),
    })
    @PostMapping("/callback")
    public ResponseEntity<?> callback() {
        service.callbackContact("Successfully to returned by HubSpot Webhook");
        return ResponseEntity.ok().body(DefaultResponseDTO.builder().success(true).message("Successfully to returned by HubSpot Webhook").build());
    }

}
