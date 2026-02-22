
package spielwiese.mcp.server.ha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public record SensorData(
		@JsonProperty("sensorType") String sensorType,
		@JsonProperty("value") Double value,
		@JsonProperty("unit") String unit,
		@JsonProperty("timestamp") String timestamp
) {
}