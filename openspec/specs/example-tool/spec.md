### Requirement: A static `hello` MCP tool is registered
The server SHALL expose a single MCP tool named `hello` that returns a static greeting string. This tool SHALL be discoverable by MCP clients via the tools/list endpoint and callable via tools/call. The tool SHALL accept one optional string parameter `name` and return a personalised greeting.

#### Scenario: Tool appears in tools list
- **WHEN** an MCP client sends a `tools/list` request
- **THEN** the response includes a tool entry with name `hello` and a description

#### Scenario: Tool called with a name parameter returns personalised greeting
- **WHEN** an MCP client calls the `hello` tool with parameter `name = "World"`
- **THEN** the tool returns a text result containing `"Hello, World!"`

#### Scenario: Tool called without a name parameter returns default greeting
- **WHEN** an MCP client calls the `hello` tool without supplying a `name` parameter
- **THEN** the tool returns a text result containing a default greeting (e.g., `"Hello, MCP!"`)

---

### Requirement: Tool implementation is a Spring-managed bean
The `hello` tool SHALL be implemented as a method annotated with `@Tool` on a class annotated with `@Component`. The bean SHALL be auto-detected by the Spring context and registered with the MCP server via `ToolCallbackProvider`.

#### Scenario: Tool bean is registered in the Spring context
- **WHEN** the application context starts
- **THEN** the `hello` tool bean is loaded and available in the MCP server's tool registry without any manual registration code in the main class
