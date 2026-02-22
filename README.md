# MCP Client - Home Automation

A Spring Boot application demonstrating the Model Context Protocol (MCP) for home automation sensor data analysis using AI.

## Overview

This project showcases the integration between MCP client and server components to enable AI-powered analysis of home automation sensor data. The application uses a local Ollama instance running in Docker Compose to provide natural language processing capabilities.

## Architecture

### MCP Server
The MCP server exposes home automation tools that provide access to sensor data:
- Temperature readings
- Humidity measurements
- Air pressure data
- Timestamp information

### MCP Client
The Spring Boot client application:
- Connects to the MCP server via HTTP
- Discovers available tools dynamically
- Provides a conversational interface using Spring AI
- Integrates with Ollama LLM for intelligent responses
- Maintains conversation context using chat memory

## Key Features

- **Tool Discovery**: Automatically discovers and registers MCP tools
- **Natural Language Interface**: Ask questions about your home environment in plain English
- **AI-Powered Analysis**: Uses Ollama (running locally in Docker) to analyze sensor data and provide recommendations
- **Context Awareness**: Maintains conversation history for coherent multi-turn interactions
- **REST API**: Simple `/api/ask` endpoint for submitting questions

## Technology Stack

- **Java 25** with Jakarta EE
- **Spring Boot 4.0.3**
- **Spring AI 2.0.0-M2** with MCP support
- **Ollama** (local LLM via Docker Compose)
- **Model Context Protocol SDK 0.17.1**

## Example Queries
```
bash curl -X POST [http://localhost:8081/api/ask](http://localhost:8081/api/ask)
-H "Content-Type: application/json"
-d '{"question": "How should I adjust my heating?"}'
```

```
bash curl -X POST [http://localhost:8081/api/ask](http://localhost:8081/api/ask)
-H "Content-Type: application/json"
-d '{"question": "Should I open the windows? I'm feeling a bit dizzy!"}'
```

## References

- **Model Context Protocol (MCP)**: [Anthropic MCP Documentation](https://www.anthropic.com/news/model-context-protocol)
- **MCP Specification**: [modelcontextprotocol.io](https://modelcontextprotocol.io/)
- **Spring AI**: [Spring AI Documentation](https://spring.io/projects/spring-ai)
- **Ollama**: [ollama.com](https://ollama.com/)

## License

This is a demonstration project for educational purposes.
