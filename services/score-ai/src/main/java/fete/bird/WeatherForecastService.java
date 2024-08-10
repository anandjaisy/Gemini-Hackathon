package fete.bird;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class WeatherForecastService {
    @Tool("Get the weather forecast for a location")
    static WeatherForecast getForecast(@P("Location to get the forecast for") String location) {
        if (location.equals("Paris")) {
            return new WeatherForecast("Paris", "Sunny", 20);
        } else if (location.equals("London")) {
            return new WeatherForecast("London", "Rainy", 15);
        } else {
            return new WeatherForecast("Unknown", "Unknown", 0);
        }
    }
}
