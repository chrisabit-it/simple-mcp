package spielwiese.mcp.server.ha;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import spielwiese.mcp.server.ha.services.HaService;

@SpringBootApplication
public class McpServerHaApplication {
  static void main(String[] args) {
    SpringApplication.run(McpServerHaApplication.class, args);
  }

  @Bean
  public ToolCallbackProvider haTools(HaService haService) {
    return MethodToolCallbackProvider.builder().toolObjects(haService).build();
  }

  @Bean
  public ToolCallback toUpperCase() {
    return FunctionToolCallback.builder("toUpperCase", (TextInput input) -> input.input().toUpperCase())
        .inputType(TextInput.class)
        .description("Put the text to upper case")
        .build();
  }

  public record TextInput(String input) {
  }
}
