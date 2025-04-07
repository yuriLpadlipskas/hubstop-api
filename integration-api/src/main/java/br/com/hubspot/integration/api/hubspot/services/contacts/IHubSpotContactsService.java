package br.com.hubspot.integration.api.hubspot.services.contacts;

import br.com.hubspot.integration.api.hubspot.dtos.ContactRequestDTO;

public interface IHubSpotContactsService {
    String createContact(ContactRequestDTO contactRequestDTO);
    void callbackContact(Object payload);
}
