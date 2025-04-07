package br.com.hubspot.integration.api.hubspot.dtos;


import lombok.Data;

@Data
public class ContactRequestDTO {
    private Properties properties;

    @Data
    public static class Properties {
        private String email;
        private String firstname;
        private String lastname;
        private String phone;
        private String company;
        private String website;
    }
}
