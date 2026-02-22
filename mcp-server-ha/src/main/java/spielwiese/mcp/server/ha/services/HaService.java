package spielwiese.mcp.server.ha.services;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import spielwiese.mcp.server.ha.model.SensorData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@SuppressWarnings("unused")
@Service
public class HaService {

	private final Random random = new Random();
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Tool(description = "Reads the current temperature from a sensor")
	public SensorData getTemperature(
			@ToolParam(description = "The sensor ID (optional)") String sensorId) {

		double temperature = 15.0 + (random.nextDouble() * 20.0); // 15-35°C
		return new SensorData(
				"Temperature",
				Math.round(temperature * 100.0) / 100.0,
				"°C",
				LocalDateTime.now().format(formatter)
		);
	}

	@Tool(description = "Reads the current humidity from a sensor")
	public SensorData getHumidity(
			@ToolParam(description = "The sensor ID (optional)") String sensorId) {

		double humidity = 30.0 + (random.nextDouble() * 60.0); // 30-90%
		return new SensorData(
				"Humidity",
				Math.round(humidity * 100.0) / 100.0,
				"%",
				LocalDateTime.now().format(formatter)
		);
	}

	@Tool(description = "Reads the current air pressure from a sensor")
	public SensorData getPressure(
			@ToolParam(description = "The sensor ID (optional)") String sensorId) {

		double pressure = 980.0 + (random.nextDouble() * 60.0); // 980-1040 hPa
		return new SensorData(
				"Pressure",
				Math.round(pressure * 100.0) / 100.0,
				"hPa",
				LocalDateTime.now().format(formatter)
		);
	}

	@Tool(description = "Reads all sensor data at once")
	public String getAllSensorData(
			@ToolParam(description = "The sensor ID (optional)") String sensorId) {

		SensorData temp = getTemperature(sensorId);
		SensorData humidity = getHumidity(sensorId);
		SensorData pressure = getPressure(sensorId);

		return String.format(
				"Sensor data from %s:\n- %s: %.2f %s\n- %s: %.2f %s\n- %s: %.2f %s",
				temp.timestamp(),
				temp.sensorType(), temp.value(), temp.unit(),
				humidity.sensorType(), humidity.value(), humidity.unit(),
				pressure.sensorType(), pressure.value(), pressure.unit()
		);
	}
}