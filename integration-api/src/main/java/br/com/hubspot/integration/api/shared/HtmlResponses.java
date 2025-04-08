package br.com.hubspot.integration.api.shared;

public class HtmlResponses {

    public static final String AUTH_ERROR = """
        <!DOCTYPE html>
        <html lang="pt-BR">
        <head>
            <meta charset="UTF-8">
            <title>Erro na Autorização</title>
        </head>
        <body>
            <h2>Erro durante a autorização!</h2>
            <p>Por favor, tente novamente.</p>
        </body>
        </html>
        """;
}
