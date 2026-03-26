### Requirement: Project uses Java 25 with Gradle Kotlin DSL
The project SHALL be a standard Gradle project using the Kotlin DSL (`build.gradle.kts`) targeting Java 25 via Gradle toolchain configuration. It SHALL include the Foojay toolchain resolver plugin to auto-provision the JDK if not locally available.

#### Scenario: Gradle build succeeds on a machine without JDK 25 installed
- **WHEN** a developer runs `./gradlew build` without JDK 25 installed
- **THEN** Gradle downloads and provisions JDK 25 via Foojay resolver and the build succeeds

#### Scenario: Build produces an executable JAR
- **WHEN** `./gradlew bootJar` is executed
- **THEN** a fat JAR is produced in `build/libs/` that can be executed with `java -jar`

---

### Requirement: Application starts as an HTTP MCP server
The application SHALL use `spring-ai-starter-mcp-server-webmvc` to expose the MCP protocol over HTTP. Stdio transport SHALL NOT be enabled. The server SHALL expose:
- `GET /sse` — SSE stream for server-to-client events (Spring AI default)
- `POST /mcp/message` — client-to-server MCP message endpoint (Spring AI default)

#### Scenario: Server starts and SSE endpoint is reachable
- **WHEN** the application is started with `./gradlew bootRun`
- **THEN** a `GET /sse` request returns a `text/event-stream` response with HTTP 200

#### Scenario: Stdio transport is not activated
- **WHEN** the application starts
- **THEN** no stdin/stdout MCP transport is initialised, and the process does not block waiting on stdin

---

### Requirement: IDE run configurations are provided
The project SHALL include ready-to-use IDE configurations for IntelliJ IDEA Ultimate and VSCode so developers can run and debug the application without manual setup.

#### Scenario: IntelliJ run configuration works out of the box
- **WHEN** a developer opens the project in IntelliJ IDEA Ultimate
- **THEN** a pre-configured run configuration named "Spring MCP Server" is available under `.idea/runConfigurations/` and can be executed directly

#### Scenario: VSCode launch configuration works out of the box
- **WHEN** a developer opens the project in VSCode with the Java Extension Pack installed
- **THEN** a launch configuration named "Spring MCP Server" is available in `.vscode/launch.json` and allows running and debugging the application
