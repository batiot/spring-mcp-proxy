# spring-mcp-proxy

A **Spring Boot MCP server** that exposes tools to AI clients over HTTP using the [Model Context Protocol](https://modelcontextprotocol.io/) (MCP).  
Built with [Spring AI](https://docs.spring.io/spring-ai/reference/) and secured with API key authentication.

---

## Features

- **HTTP-only MCP transport** — SSE stream at `GET /sse`, message endpoint at `POST /mcp/message`
- **API key authentication** — all endpoints protected by `X-Api-Key` header
- **Example tool** — `hello` tool returns a personalised greeting

---

## Prerequisites

| Tool | Version |
|------|---------|
| JDK  | 25 (auto-provisioned via Gradle toolchain if not installed) |
| Gradle | Wrapper included (9.4.1) |

No manual JDK installation is required if you have an internet connection — Gradle will download JDK 25 automatically via the [Foojay Toolchain Resolver](https://foojay.io/).

---

## Running the server

### Using Gradle wrapper

```bash
./gradlew bootRun
```

The server starts on `http://localhost:8080`.

### Overriding the API key

**Option A — environment variable (recommended):**
```bash
MCP_SERVER_API_KEY=my-secret ./gradlew bootRun
```

**Option B — local properties file:**

Create `src/main/resources/application-local.properties` (already `.gitignore`d):
```properties
mcp.server.api-key=my-secret
```
Then run with the `local` profile:
```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

> **Warning:** The default key `changeme` is intentionally insecure. Always set a strong key before exposing the server.

---

## Testing the server

### Check the SSE endpoint

```bash
curl -N -H "X-Api-Key: changeme" http://localhost:8080/sse
```

You should receive a `text/event-stream` response with the endpoint event.

### Missing API key → 401

```bash
curl -i http://localhost:8080/sse
# HTTP/1.1 401
```

### Call the `hello` tool (via MCP message)

First establish an SSE connection to receive the session endpoint, then send a tool call:

```bash
# 1. Get the session message endpoint from the SSE stream
curl -N -H "X-Api-Key: changeme" http://localhost:8080/sse &

# 2. Send a tool call (replace SESSION_ENDPOINT with the endpoint from the SSE event)
curl -X POST -H "Content-Type: application/json" -H "X-Api-Key: changeme" \
  http://localhost:8080/mcp/message \
  -d '{"jsonrpc":"2.0","id":1,"method":"tools/call","params":{"name":"hello","arguments":{"name":"World"}}}'
```

---

## Running tests

```bash
./gradlew test
```

Test coverage:
- `ApiKeyAuthFilterTest` — unit tests for the API key filter (valid key, missing key, wrong key)
- `HelloToolsTest` — unit tests for the hello tool logic and Spring context registration
- `McpServerSecurityIntegrationTest` — full-stack integration tests for SSE endpoint security

---

## Project structure

```
src/
  main/
    java/com/batiot/springmcpproxy/
      SpringMcpProxyApplication.java   # Main entry point
      config/
        McpToolsConfig.java            # ToolCallbackProvider registration
      security/
        ApiKeyAuthFilter.java          # X-Api-Key header validation
        SecurityConfig.java            # Spring Security filter chain
      tools/
        HelloTools.java                # @Tool-annotated example tool
    resources/
      application.properties           # Server config (api key default: changeme)
  test/
    java/com/batiot/springmcpproxy/
      McpServerSecurityIntegrationTest.java
      security/ApiKeyAuthFilterTest.java
      tools/HelloToolsTest.java
.idea/runConfigurations/              # IntelliJ run configuration
.vscode/                              # VSCode launch + settings + extensions
```

---

## IDE setup

### IntelliJ IDEA Ultimate
Open the project root — a **"Spring MCP Server"** run configuration is pre-configured under `.idea/runConfigurations/`.

### VSCode
Open the project root with the [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) and [Spring Boot Extension Pack](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-spring-boot) installed.  
A **"Spring MCP Server"** launch configuration is available in `.vscode/launch.json`.

Recommended extensions are listed in `.vscode/extensions.json` — VSCode will prompt you to install them.
