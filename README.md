# 🧩 HubSpot API

API de integração desenvolvida com **Spring Boot**, seguindo práticas modernas de desenvolvimento como:

- Perfis de ambiente (`dev`, `prod`)
- Suporte à execução via terminal, scripts ou containers Docker

---

## 🚀 Como Executar

A aplicação possui dois ambientes:

- `dev` → execução local
- `prod` → hospedado na **Railway**

---

## ✅ Pré-requisitos

Antes de executar, certifique-se de ter os seguintes itens instalados:

- [Java 17+](https://adoptium.net/)
- [Maven 3.9+](https://maven.apache.org/)
- (Opcional) [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/)
- IDE de sua preferência: IntelliJ, VSCode, etc.

---

## ℹ️ Informações Importantes

- 🚪 A aplicação roda na porta: `9091`
- 📎 Todas as requisições da API utilizam o prefixo:

  ```http
  /hubspot/

- 🧭 Utilize o **Swagger** para explorar os endpoints disponíveis.
- **ATENÇÃO:** A HubSpot exige HTTPS para os webhooks, então o ambiente local (`dev`) **não funciona com webhooks**

## 💻 Execução Local

#### 🪟 Windows

- Execute o script `run-dev.bat` na raiz do projeto:
  ```bash
  run-dev.bat
  ```
- Ou rode diretamente pela sua **IDE**, apontando para o perfil `dev`.

---

#### 🐧 Linux / macOS

- Execute o script `run-dev.sh` na raiz do projeto:
  ```bash
  ./run-dev.sh
  ```
- Ou utilize sua **IDE**, configurando o perfil de execução como `dev`.

---

#### 🐳 Execução via Docker

> O projeto possui um `Dockerfile` multi-stage e um `docker-compose.yml` configurado para facilitar a execução em container.

- Na raiz do projeto, execute:

```bash
docker compose up -d --build
```

- A aplicação será exposta localmente na porta `9091`.

---

### 📦 Iniciando o projeto

- Siga o **Swagger** para entender o fluxo completo de autenticação e uso da API.
- Após executar a aplicação, pode seguir esse passo a passo

1. **Gerar URL de Autenticação**  
   Acesse no navegador: [`http://localhost:9091/hubspot/ouath/authorize-url`](http://localhost:9091/hubspot/ouath/authorize-url)
   Isso irá redirecionar você para a autenticação com a HubSpot.

2. **Autenticar-se com a HubSpot**  
Após autenticar, você será redirecionado automaticamente para o endpoint de callback da aplicação: [`http://localhost:9091/hubspot/ouath/callback`](http://localhost:9091/hubspot/ouath/callback)

3. **Criar Contato pela API**  
Após estar autenticado, você pode usar a API para criar um novo contato: [`http://localhost:9091/hubspot/contacts/create`](http://localhost:9091/hubspot/contacts/create)

---

## 📁 Estrutura do Projeto

```bash
README.md                    # Este documento
integration-api/
│
├── src/                     # Código fonte
├── run-dev.bat              # Script para execução local no Windows
├── run-dev.sh               # Script para execução local no Linux/macOS
├── Dockerfile               # Dockerfile multi-stage
├── docker-compose.yml       # Docker Compose para facilitar a execução
├── pom.xml                  # Configurações Maven

```

## ☁️ Execução em Produção

A aplicação já está disponível em produção no seguinte domínio:  
🔗 [`https://hubspot-api-production.up.railway.app`](https://hubspot-api-production.up.railway.app)

Siga o **Swagger** para entender o fluxo completo de autenticação e uso da API.

### 📦 Iniciando o projeto

1. **Gerar URL de Autenticação**  
   Acesse no navegador: [`https://hubspot-api-production.up.railway.app/hubspot/oauth/authorize-url`](https://hubspot-api-production.up.railway.app/hubspot/oauth/authorize-url)
   Isso irá redirecionar você para a autenticação com a HubSpot.

2. **Autenticar-se com a HubSpot**  
Após autenticar, você será redirecionado automaticamente para o endpoint de callback da aplicação: [`https://hubspot-api-production.up.railway.app/hubspot/oauth/callback`](https://hubspot-api-production.up.railway.app/hubspot/oauth/callback)

3. **Criar Contato pela API**  
Após estar autenticado, você pode usar a API para criar um novo contato: [`https://hubspot-api-production.up.railway.app/hubspot/contacts/create`](https://hubspot-api-production.up.railway.app/hubspot/contacts/create)

4. **Webhook de Retorno**  
Ao criar um contato, será automaticamente disparado o webhook de callback: [`https://hubspot-api-production.up.railway.app/hubspot/contacts/callback`](https://hubspot-api-production.up.railway.app/hubspot/contacts/callback)

---

## 📚 Documentação Técnica

Toda a documentação da API está disponível via Swagger nos seguintes links:

- Ambiente Local:  
[`http://localhost:9091/hubspot/swagger-ui/index.html`](http://localhost:9091/hubspot/swagger-ui/index.html)

- Ambiente de Produção:  
[`https://hubspot-api-production.up.railway.app/hubspot/swagger-ui/index.html`](https://hubspot-api-production.up.railway.app/hubspot/swagger-ui/index.html)

### ⚒️ Ferramentas

  - **Spring Boot**: Escolhido por familiaridade do framework além de ter todas as libs que eu preciso
  - **Java 17**:     Escolhido pela solidez e facilidade para integrar ao Railway.
  - **Swagger**:     Escolhido pela visibilidade dos endpoints e melhor entendimento da aplicação.
  - **Railway**:     Escolhido por ser gratuito por 15 dias e ter uma facilidade de deploy com o GitHub.
  - **Docker**:      Escolhido para subir o serviço para o Railway e facilitar a execução da aplicação.

---

### 💡 Libs

- **Springdoc OpenAPI**:     Escolhido pela configuração simples, documentação completa para melhor entendimento da aplicação
- **Lombok**:                Escolhido pela aplicação de Clean Code, reduzindo a quantidade de getter, setters e constructors, além de facilitar o uso dos mesmos.
- **Spring Security**:       Escolhido pela praticidade de configuração, familiaridade com a lib.
- **Spring Retry**:          Escolhido pela geração de tentativas (retries) no POST que pode falhar 
- **Spring Validation**:     Escolhido pela familiaridade e essencial para gerar as controllers e services.


---

### 🔭 Melhorias Futuras

  - **Testes unitários**:            Acredito que a implementação dos testes unitários seria a minha prioridade, pois testes E2E são essenciais.
  - **Upgrade versão 21 do Java**:   Inicialmente, estava mexendo nessa versão, porém tive que mudar para a versão 17 pois o Railway não aceita a versão 21 do Java.
  - **Migração de servidor**:        Migraria o serviço para algo mais robusto como GCP ou AWS para melhor escalabilidade.
  - **Logs e métricas**:             Além de colocar mais logs para visibilidade, implementaria o Prometheues + Grafana para visualização das métricas e o dashboard do grafana.
  - **Token em cache**:              Inicialmente, pensei em utilizar a lib de cache do Spring (spring-boot-starter-cache), porém nunca tinha feito cache de aplicação manual e quis tentar.
  - **Criptografia de credenciais**: As credenciais da HubSpot estão visiveis dentro das properties, gostaria de deixar isso criptografado e até mesmo persistindo em banco para ficar mais seguro.
  - **Webhook de contatos**:         O Webhook de contato funciona porém fiquei sem ideia do que fazer com ele depois que ele é acionado, por isso, ele está muito simples








