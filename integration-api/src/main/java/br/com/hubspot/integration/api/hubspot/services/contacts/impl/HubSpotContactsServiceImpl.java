package br.com.hubspot.integration.api.hubspot.services.contacts.impl;

import br.com.hubspot.integration.api.auth.TokenManager;
import br.com.hubspot.integration.api.hubspot.client.HubSpotRestClient;
import br.com.hubspot.integration.api.hubspot.dtos.ContactRequestDTO;
import br.com.hubspot.integration.api.hubspot.dtos.DefaultResponseDTO;
import br.com.hubspot.integration.api.hubspot.services.contacts.IHubSpotContactsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HubSpotContactsServiceImpl implements IHubSpotContactsService {

    private final HubSpotRestClient hubspotRestClient;
    private final TokenManager tokenManager;
    private final String urlContact;

    public HubSpotContactsServiceImpl(HubSpotRestClient hubspotRestClient,
                                      TokenManager tokenManager,
                                      @Value("${hubspot.url.contacts}") String urlContact) {
        this.hubspotRestClient = hubspotRestClient;
        this.tokenManager = tokenManager;
        this.urlContact = urlContact;
    }

    @Override
    public DefaultResponseDTO createContact(ContactRequestDTO contactRequestDTO) {
        log.info("1.0 init createContact dto: {}", contactRequestDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",tokenManager.generateBearerToken());
        Object response = hubspotRestClient.post(urlContact,contactRequestDTO,Object.class,headers,MediaType.APPLICATION_JSON);
        log.info("2.0 init createContact response: {}", response);
        return DefaultResponseDTO.builder().message("Successfully to created contact").success(true).build();
    }

    @Override
    public void callbackContact(Object payload) {
        log.info("callbackContact receive webhook: {}", payload);
    }
}
