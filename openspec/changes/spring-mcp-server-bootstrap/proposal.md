## Why

This project needs a working foundation: a Spring Boot MCP server that exposes tools over HTTP using `spring-ai-mcp-server-spring-boot-starter`, secured by API key authentication, and fully compatible with both IntelliJ IDEA Ultimate and VSCode development environments. Without this bootstrap, there is nothing to build further proxy or tool integrations on.

## What Changes

- Create a new Java 25 + Gradle project from scratch with Spring Boot 3.x
- Add `spring-ai-mcp-server-spring-boot-starter` as the core MCP server dependency (HTTP transport only — no stdio)
- Implement API key authentication via a filter that validates a configurable header (`X-Api-Key`) against a value from `application.properties`
- Implement one static example MCP tool (e.g., `hello` — returns a fixed greeting) to validate the server works end-to-end
- Provide IDE support files: IntelliJ run configurations (`.idea/runConfigurations/`) and VSCode launch/settings files (`.vscode/`)

## Capabilities

### New Capabilities

- `mcp-http-server`: Spring Boot application exposing an MCP endpoint over HTTP (SSE + HTTP transport), no stdio
- `api-key-auth`: Request filter that enforces API key authentication on MCP endpoints via a configurable header
- `example-tool`: A static MCP tool (`hello`) that returns a hardcoded greeting, demonstrating the tool registration pattern

### Modified Capabilities

<!-- No existing capabilities — this is a greenfield project -->

## Impact

- **New project structure**: `src/main/java`, `src/test/java`, `build.gradle`, `settings.gradle`, `gradle/wrapper/`
- **Dependencies**: `spring-ai-mcp-server-spring-boot-starter`, Spring Boot 3.x, Spring Security (or servlet filter for API key)
- **Configuration**: `application.properties` with `mcp.server.api-key` and transport settings
- **IDE files**: `.idea/` run configurations, `.vscode/launch.json` and `.vscode/settings.json`
- **No breaking changes** — greenfield project
