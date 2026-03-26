package com.batiot.springmcpproxy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the MCP HTTP server security layer.
 * Validates that the API key filter and MCP endpoints are wired correctly.
 */
@SpringBootTest
@AutoConfigureMockMvc
class McpServerSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Spring AI MCP WebMVC transport registers the SSE endpoint at /sse by default
    // and the message endpoint at /mcp/message.

    @Test
    void sseEndpointWithCorrectApiKeyReturnsOk() throws Exception {
        mockMvc.perform(get("/sse")
                        .header("X-Api-Key", "changeme"))
                .andExpect(status().isOk());
    }

    @Test
    void sseEndpointWithoutApiKeyReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/sse"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void sseEndpointWithWrongApiKeyReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/sse")
                        .header("X-Api-Key", "wrong"))
                .andExpect(status().isUnauthorized());
    }
}
