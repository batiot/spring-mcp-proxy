package com.batiot.springmcpproxy.tools;

import com.batiot.springmcpproxy.SpringMcpProxyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SpringMcpProxyApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
class HelloToolsTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private HelloTools helloTools;

    @Test
    void helloToolBeanIsRegisteredInContext() {
        assertThat(context.containsBean("helloTools")).isTrue();
    }

    @Test
    void helloWithNameReturnsPersonalisedGreeting() {
        String result = helloTools.hello("World");
        assertThat(result).isEqualTo("Hello, World!");
    }

    @Test
    void helloWithNullNameReturnsDefaultGreeting() {
        String result = helloTools.hello(null);
        assertThat(result).isEqualTo("Hello, MCP!");
    }

    @Test
    void helloWithBlankNameReturnsDefaultGreeting() {
        String result = helloTools.hello("  ");
        assertThat(result).isEqualTo("Hello, MCP!");
    }
}
