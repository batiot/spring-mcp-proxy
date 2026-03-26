## 1. Project Scaffold

- [ ] 1.1 Initialise Gradle wrapper (`gradle wrapper`) with Gradle 8.x and Kotlin DSL (`settings.gradle.kts`, `build.gradle.kts`)
- [ ] 1.2 Configure `build.gradle.kts`: Spring Boot plugin, Spring Dependency Management plugin, Java 25 toolchain with Foojay resolver
- [ ] 1.3 Add dependencies: `spring-ai-starter-mcp-server-webmvc`, `spring-boot-starter-security`, `spring-boot-starter-test`
- [ ] 1.4 Create the main application class `SpringMcpProxyApplication` with `@SpringBootApplication`
- [ ] 1.5 Verify `./gradlew build` compiles and tests pass (empty test baseline)

## 2. Application Configuration

- [ ] 2.1 Create `src/main/resources/application.properties` with `spring.ai.mcp.server.name`, `spring.ai.mcp.server.version`, and `mcp.server.api-key=changeme`
- [ ] 2.2 Disable stdio transport (`spring.ai.mcp.server.stdio=false`) and ensure HTTP transport is active
- [ ] 2.3 Create `src/main/resources/application-local.properties` (gitignored) as the override template for local secrets

## 3. API Key Authentication

- [ ] 3.1 Create `ApiKeyAuthFilter extends OncePerRequestFilter` that reads `X-Api-Key` header and returns HTTP 401 if missing or wrong
- [ ] 3.2 Create `SecurityConfig` (`@Configuration`) that adds `ApiKeyAuthFilter` to the `SecurityFilterChain` and disables CSRF and session management
- [ ] 3.3 Inject `mcp.server.api-key` into the filter via `@Value`
- [ ] 3.4 Write unit test for `ApiKeyAuthFilter`: valid key passes, missing key → 401, wrong key → 401

## 4. Example Tool

- [ ] 4.1 Create `HelloTools` `@Component` class with a method annotated `@Tool(name = "hello", description = "...")`
- [ ] 4.2 The `hello` method accepts an optional `String name` parameter and returns `"Hello, <name>!"` or `"Hello, MCP!"` as default
- [ ] 4.3 Register `HelloTools` as a `ToolCallbackProvider` bean in a `@Configuration` class
- [ ] 4.4 Write integration test confirming the tool appears in the Spring context

## 5. IDE Support

- [ ] 5.1 Create `.idea/runConfigurations/Spring_MCP_Server.xml` for IntelliJ IDEA Ultimate (Spring Boot run configuration)
- [ ] 5.2 Create `.vscode/launch.json` with a Java launch configuration for the main class
- [ ] 5.3 Create `.vscode/settings.json` with `java.configuration.updateBuildConfiguration: "automatic"` and Gradle JVM settings
- [ ] 5.4 Create `.vscode/extensions.json` recommending the Java Extension Pack and Spring Boot Extension Pack

## 6. Developer Experience

- [ ] 6.1 Update `README.md` with: project description, prerequisites (JDK 25 or Gradle toolchain), how to run, how to call the MCP server with `curl`, and the API key configuration
- [ ] 6.2 Add `.gitignore` entries for `application-local.properties`, `.idea/` workspace files (excluding run configurations), and `build/`
- [ ] 6.3 Verify full `./gradlew bootRun` starts the server and `GET /mcp/sse` with the correct API key returns HTTP 200
