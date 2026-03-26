## ADDED Requirements

### Requirement: All MCP endpoints require a valid API key
The server SHALL enforce API key authentication on all MCP endpoints (`/mcp/**`). Every request SHALL include an `X-Api-Key` HTTP header whose value matches the configured server-side key. Requests without the header or with an incorrect value SHALL be rejected with HTTP 401 before reaching any MCP handler.

#### Scenario: Request with correct API key is allowed through
- **WHEN** a client sends a request to `/mcp/sse` with header `X-Api-Key: <correct-key>`
- **THEN** the request is processed normally and the server responds with HTTP 200

#### Scenario: Request with missing API key header is rejected
- **WHEN** a client sends a request to `/mcp/sse` without the `X-Api-Key` header
- **THEN** the server responds with HTTP 401 and does not process the MCP request

#### Scenario: Request with wrong API key value is rejected
- **WHEN** a client sends a request to `/mcp/message` with header `X-Api-Key: wrong-value`
- **THEN** the server responds with HTTP 401 and does not process the MCP request

---

### Requirement: API key is configurable via application properties
The API key value SHALL be read from the property `mcp.server.api-key` in `application.properties`. It SHALL be possible to override this value via the environment variable `MCP_SERVER_API_KEY` without changing any code or configuration files. The default value provided in `application.properties` SHALL be `changeme`, which is intentionally insecure to prompt developers to change it.

#### Scenario: Custom API key set via application property
- **WHEN** `mcp.server.api-key=my-secret` is set in `application.properties` and a request includes `X-Api-Key: my-secret`
- **THEN** the request is accepted

#### Scenario: API key overridden via environment variable
- **WHEN** the application is started with environment variable `MCP_SERVER_API_KEY=env-secret` and a request includes `X-Api-Key: env-secret`
- **THEN** the request is accepted

#### Scenario: Default key prompts change
- **WHEN** no `mcp.server.api-key` is explicitly set
- **THEN** the server still starts with the default `changeme` key (for local development only)
