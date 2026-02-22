package spielwiese.mcp.client.ha.controller;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HaController {
	private final ChatClient chatClient;

	public HaController(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpSyncClients) {
		this.chatClient = chatClientBuilder
				.defaultSystem(
						"""
								You are an assistant for analyzing home automation sensor data. You receive structured forecast data for your apartment.
								
								Data units:
								- Temperature in °C
								- Measurement time as timestamp in Unix time (ms)
								- Humidity in %
								- Air pressure in hectopascals
								
								Instructions:
								- Convert all values to readable units.
								- Identify and describe the state of the apartment.
								- Comment on missing values (null).
								- At the end, provide a recommendation for the heating system and whether windows should be closed or open.
								
								Formulate your answers clearly, step by step, and in complete sentences. Keep them friendly, precise, and insightful.
								""")
				.defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
				.defaultAdvisors(MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().build()).build(),
						new SimpleLoggerAdvisor())
				.build();
	}

	@PostMapping("/ask")
	public Answer ask(@RequestBody Question question) {
	return chatClient.prompt().user(question.question()).call().entity(Answer.class);
	}

	@SuppressWarnings("unused")
  public record Question(String question) {
	}

	public record Answer(String answer) {
	}
}