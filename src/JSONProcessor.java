import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONProcessor {
    /**
     * Reads JSON data from a file and returns it as a string.
     *
     * @param fileName The name of the JSON file.
     * @return The JSON data as a string.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static String readJSONFromFile(String fileName) throws IOException {
        StringBuilder jsonStr = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonStr.toString();
    }

    /**
     * Parses JSON data into a list of WeatherData objects.
     *
     * @param jsonData The JSON data as a string.
     * @return A list of WeatherData objects.
     */
    public static List<WeatherData> parseJSONData(String jsonData) {
        List<WeatherData> weatherDataList = new ArrayList<>();

        // Parse the JSON data
        JSONObject jsonObject = new JSONObject(jsonData);

        // Extract the "days" array
        JSONArray daysArray = jsonObject.getJSONArray("days");

        // Loop through each day's data
        for (int i = 0; i < daysArray.length(); i++) {
            JSONObject dayObject = daysArray.getJSONObject(i);

            // Extract weather parameters for each date
            float temperature = dayObject.getFloat("temp");
            float windSpeed = dayObject.getFloat("windspeed");
            float humidity = dayObject.getFloat("humidity");
            float windDirection = dayObject.getFloat("winddir");

            // Create a WeatherData object for the date and add it to the list
            WeatherData weatherData = new WeatherData(temperature, windSpeed,
                    windDirection, humidity);
            weatherDataList.add(weatherData);
        }

        return weatherDataList;
    }
}
