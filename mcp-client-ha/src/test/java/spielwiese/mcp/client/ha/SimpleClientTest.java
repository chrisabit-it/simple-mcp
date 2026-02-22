
package spielwiese.mcp.client.ha;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"spring.ai.mcp.client.sse.connections.test-server.url=http://localhost:8080"
})
@DisplayName("MCP Client Integration Tests")
class SimpleClientTest {

	@Autowired
	private List<McpSyncClient> mcpSyncClients;

	private McpSyncClient getTestClient() {
		return mcpSyncClients.isEmpty() ? null : mcpSyncClients.get(0);
	}

	@Test
	@DisplayName("Should connect to MCP server")
	void testConnection() {
		assertNotNull(mcpSyncClients, "MCP clients should be autowired");
		assertFalse(mcpSyncClients.isEmpty(), "Should have at least one MCP client");
		assertNotNull(getTestClient(), "Test server client should exist");
	}

	@Test
	@DisplayName("Should list all available tools")
	void testListTools() {
		McpSyncClient client = getTestClient();
		assertNotNull(client, "Client should not be null");

		McpSchema.ListToolsResult toolsList = client.listTools();

		assertNotNull(toolsList, "Tools list should not be null");
		assertNotNull(toolsList.tools(), "Tools collection should not be null");
		assertFalse(toolsList.tools().isEmpty(), "Should have at least one tool");

		System.out.println("Available Tools = " + toolsList);
		toolsList.tools().forEach(tool -> {
			System.out.println("Tool: " + tool.name() +
					", description: " + tool.description());

			assertNotNull(tool.name(), "Tool name should not be null");
			assertNotNull(tool.description(), "Tool description should not be null");
		});
	}

	@Test
	@DisplayName("Should call toUpperCase tool successfully")
	void testToUpperCaseTool() {
		McpSyncClient client = getTestClient();
		assertNotNull(client, "Client should not be null");

		McpSchema.CallToolResult result = client.callTool(
				new McpSchema.CallToolRequest("toUpperCase", Map.of("input", "abc-xyz"), null)
		);

		assertNotNull(result, "Result should not be null");
		assertNotNull(result.content(), "Result content should not be null");

		System.out.println("To Upper Case: " + result);
	}
}