package com.batiot.springmcpproxy.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Example MCP tool that demonstrates the tool registration pattern.
 * Registered with the MCP server via {@link com.batiot.springmcpproxy.config.McpToolsConfig}.
 */
@Component
public class HelloTools {

    @Tool(name = "hello", description = "Returns a personalised greeting. Pass a name for a custom message, or omit for the default.")
    public String hello(
            @ToolParam(description = "Name to greet (optional)", required = false) String name) {
        if (name == null || name.isBlank()) {
            return "Hello, MCP!";
        }
        return "Hello, " + name + "!";
    }
}
