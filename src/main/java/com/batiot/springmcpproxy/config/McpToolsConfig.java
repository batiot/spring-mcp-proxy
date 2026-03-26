package com.batiot.springmcpproxy.config;

import com.batiot.springmcpproxy.tools.HelloTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolsConfig {

    @Bean
    public ToolCallbackProvider helloToolCallbackProvider(HelloTools helloTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(helloTools)
                .build();
    }
}
