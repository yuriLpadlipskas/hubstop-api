package br.com.hubspot.integration.api.hubspot.services.contacts;

import br.com.hubspot.integration.api.hubspot.dtos.ContactRequestDTO;
import br.com.hubspot.integration.api.hubspot.dtos.DefaultResponseDTO;

public interface IHubSpotContactsService {
    DefaultResponseDTO createContact(ContactRequestDTO contactRequestDTO);
    void callbackContact(Object payload);
}
