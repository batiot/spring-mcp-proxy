## Context

This is a greenfield project. There is no existing code — we are bootstrapping a new Spring Boot application that exposes MCP tools over HTTP. The project must work seamlessly in IntelliJ IDEA Ultimate (native Spring and Gradle support) and VSCode (via the Java extension pack and Spring Boot extension), and it should use Java 25 (the latest LTS as of September 2025).

The Model Context Protocol (MCP) is an open standard for AI model-tool communication. Spring AI's `spring-ai-starter-mcp-server-webmvc` provides a ready-made Spring Boot auto-configuration for MCP over HTTP using SSE (Server-Sent Events) and a synchronous message endpoint.

## Goals / Non-Goals

**Goals:**
- Bootstrap a working Gradle (Kotlin DSL) + Java 25 Spring Boot project
- Expose MCP tools over HTTP (SSE + POST `/mcp/message`) — no stdio transport
- Protect all MCP endpoints with API key authentication (header `X-Api-Key`)
- Register one static example tool (`hello`) to verify end-to-end functionality
- Provide IDE run/launch configs for both IntelliJ and VSCode

**Non-Goals:**
- OAuth2 / JWT / OIDC authentication (out of scope for this spike)
- Dynamic tool registration or proxy to external REST APIs (next phase)
- Multi-tenant API key management or key rotation
- Docker / Kubernetes deployment configuration
- stdio MCP transport

## Decisions

### D1 — Gradle Kotlin DSL (`build.gradle.kts`) over Groovy DSL
**Rationale:** Kotlin DSL provides type-safe build scripts with full IDE autocomplete in IntelliJ. It is the current Gradle best practice for new JVM projects.  
**Alternative considered:** Groovy DSL — dropped because it lacks compile-time type safety and IDE autocompletion.

### D2 — `spring-ai-starter-mcp-server-webmvc` (not webflux)
**Rationale:** The WebMVC (servlet) variant is simpler to debug, uses standard Spring MVC, and avoids reactive complexity for a spike. It exposes SSE via `/mcp/sse` and the message endpoint at `/mcp/message`.  
**Alternative considered:** `spring-ai-starter-mcp-server-webflux` (reactive) — deferred; overkill for this stage.

### D3 — API key via Spring Security `OncePerRequestFilter`
**Rationale:** A thin servlet filter is the simplest and most idiomatic Spring way to validate a shared secret before requests reach controllers. Spring Security's `SecurityFilterChain` is used to attach it cleanly, keeping auth logic separate from business logic.  
**Alternative considered:** Spring Security `BasicAuthenticationFilter` or custom `HandlerInterceptor` — filter approach is more straightforward and does not leak HTTP 401 via WWW-Authenticate headers (which would confuse MCP clients).

### D4 — Tool registration via `@Tool` annotation on a `@Component` bean
**Rationale:** Spring AI's annotation-based tool registration (`@Tool` from `spring-ai-core`) is auto-discovered if the bean is in the Spring context and declared via `McpServerFeatures` or `ToolCallbackProvider`. This keeps tool definition declarative and co-located with its implementation.  
**Alternative considered:** Programmatic `FunctionCallback` registration — more verbose, appropriate for dynamic tools (future phase).

### D5 — API key stored in `application.properties` (not hardcoded)
**Rationale:** Externalising the key allows overriding via environment variable (`MCP_SERVER_API_KEY`) or Kubernetes secret without changing code. The default value (`changeme`) is intentionally insecure to force change before deployment.  
**Alternative considered:** `application.yml` — both work; `.properties` is simpler for a small config surface.

### D6 — Java 25 toolchain via Gradle `java { toolchain { languageVersion = JavaLanguageVersion.of(25) } }`
**Rationale:** Gradle toolchain support resolves the correct JDK automatically, making the project portable across machines regardless of the developer's installed JDK. IntelliJ and VSCode Java extensions both honour Gradle toolchain declarations.

## Risks / Trade-offs

- **[Risk] Spring AI milestone versions** → Spring AI 1.0.x may still have API surface changes. Mitigation: pin exact version in `build.gradle.kts`; upgrade consciously.
- **[Risk] Java 25 toolchain availability on CI/developer machines** → Gradle toolchain auto-provisioning (Foojay resolver) will download JDK 25 if not found. Mitigation: add `org.gradle.toolchains.foojay-resolver-convention` plugin.
- **[Risk] API key in plain `application.properties` checked into VCS** → The default value is intentionally `changeme`; a `.gitignore` entry and `application-local.properties` override pattern mitigates accidental secret commit.
- **[Trade-off] OncePerRequestFilter approach bypasses Spring Security's principal** → MCP clients do not use standard authentication flows, so we intentionally keep the security model simple. The filter short-circuits with HTTP 401 on mismatch; no `SecurityContext` population needed.
