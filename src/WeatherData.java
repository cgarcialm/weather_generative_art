/**
 * Represents weather parameters for a date.
 */
public class WeatherData {
    private float temperature;
    private float windSpeed;
    private float windDirection;
    private float humidity;

    // Constructor
    public WeatherData(float temperature, float windSpeed,
                       float windDirection, float humidity) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection - 90;
        this.humidity = humidity;
    }

    // Getter methods
    public float getTemperature() {
        return temperature;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public float getHumidity() {
        return humidity;
    }
}