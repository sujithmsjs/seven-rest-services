package tech.suji.seven_prods.util;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class Utility {
	public Duration getDuration(String timeString) {
		// Define the formatter for HH:mm:ss
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		// Parse the time string to LocalTime
		LocalTime localTime = LocalTime.parse(timeString, formatter);

		// Convert LocalTime to Duration
		return Duration.between(LocalTime.MIDNIGHT, localTime);
	}
}
