import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The WeatherArtGenerator class generates generative art based on weather data.
 */
public class WeatherArtGenerator {

    /**
     * The main method reads JSON data from a file, parses it into a list of WeatherData objects,
     * generates wind curves, and plots them.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        try {
            // Read JSON data from the file
            String jsonData = readJSONFromFile("input/data.txt");

            // Parse the JSON data into a list of WeatherData objects
            List<WeatherData> weatherDataList = parseJSONData(jsonData);

            List<Segment> windSegments = generateWindCurve(weatherDataList);

            List<List<Segment>> windSubegments = divideSegments(windSegments);

            // Create and display the JFrame
            createAndDisplayFrame(windSubegments);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    /**
     * Represents weather parameters for a date.
     */
    static class WeatherData {
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

    /**
     * Maps a value from one range to another.
     *
     * @param value    The value to be mapped.
     * @param fromMin  The minimum value of the original range.
     * @param fromMax  The maximum value of the original range.
     * @param toMin    The minimum value of the target range.
     * @param toMax    The maximum value of the target range.
     * @return The mapped value.
     */
    public static float map(float value, float fromMin, float fromMax, float toMin, float toMax) {
        return (value - fromMin) * (toMax - toMin) / (fromMax - fromMin) + toMin;
    }

    /**
     * Gets a color based on temperature.
     *
     * @param temperature The temperature value.
     * @return A Color representing the temperature-based color.
     */
    public static Color getColorBasedOnTemperature(float temperature) {
        // Implement your color mapping logic here
        // Example: Warmer colors for higher temperatures, cooler colors for lower temperatures
        return Color.getHSBColor(map(temperature, 0, 30, 0, 0.7f), 1, 1);
    }

    /**
     * Represents a line segment.
     */
    static class Segment {
        private double startX;
        private double startY;
        private double endX;
        private double endY;
        Color segmentColor;


        public Segment(double startX, double startY, double endX, double endY
                , Color segmentColor) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.segmentColor = segmentColor;
        }

        public double getLength() {
            return Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        }

        public double getOrientation() {
            return Math.toDegrees(Math.atan2(endY - startY, endX - startX));
        }

        public Color getColor() {
            return segmentColor;
        }
    }

    /**
     * Generates wind curves based on weather data.
     *
     * @param weatherDataList A list of WeatherData objects.
     * @return A list of wind curve segments.
     */
    public static List<Segment> generateWindCurve(List<WeatherData> weatherDataList) {
        List<Segment> windSegments = new ArrayList<>();

        double x = 0; // Initial x-coordinate
        double y = 0; // Initial y-coordinate

        for (WeatherData weatherData : weatherDataList) {
            double windSpeed = weatherData.getWindSpeed();
            double windDirection = weatherData.getWindDirection();

            // Calculate the change in x and y based on windSpeed and windDirection
            double deltaX = windSpeed * Math.cos(Math.toRadians(windDirection));
            double deltaY = windSpeed * Math.sin(Math.toRadians(windDirection));

            // Get the temperature and use it to determine the segment color
            float temperature = weatherData.getTemperature();
            Color segmentColor = getColorBasedOnTemperature(temperature);

            // Create a segment using the change in x and y
            Segment segment = new Segment(x, y, x + deltaX, y + deltaY, segmentColor);
            windSegments.add(segment);

            // Update the current x and y coordinates for the next segment
            x += deltaX;
            y += deltaY;
        }

        return windSegments;
    }

    /**
     * Divides a list of segments into sublists.
     *
     * @param originalSegments The original list of segments.
     * @return A list of sublists of segments.
     */
    public static List<List<Segment>> divideSegments(List<Segment> originalSegments) {
        List<List<Segment>> dividedSegments = new ArrayList<>();

        int n = originalSegments.size();
        int startIndex = 0;
        int endIndex = (int) (0.75 * n);

        while (endIndex <= n) {
            // Create a sublist from startIndex to endIndex
            List<Segment> sublist = originalSegments.subList(startIndex, endIndex);
            dividedSegments.add(new ArrayList<>(sublist));

            // Move the window forward by one segment
            startIndex++;
            endIndex++;

            // Ensure endIndex does not exceed n
            if (endIndex > n) {
                break;
            }
        }

        return dividedSegments;
    }

    /**
     * Plots wind curves on a JPanel.
     *
     * @param windSegments A list of wind curve segments.
     */
    public static void createAndDisplayFrame(List<List<Segment>> windSubegments) throws IOException {
        // Create a BufferedImage to draw the wind curves on
        int width = 1200;
        int height = 800;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Get the graphics context of the image
        Graphics2D graphics = image.createGraphics();

        // Fill the image with a black background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);

        // Plot the wind curves on the image
        int yOffset = 0;
        int xOffset = 0;

        for (List<Segment> segmentList : windSubegments) {
            plotWindCurve(graphics, segmentList, xOffset, yOffset);

            yOffset += 6; // Adjust as needed to control the vertical spacing
            xOffset += 2; // Adjust as needed to control the vertical spacing
        }

        // Save the generated image
        saveImage(image, "output/weather_art.png");
    }

    /**
     * Plots a wind curve on a graphics context.
     *
     * @param g        The graphics context.
     * @param segments A list of segments representing the wind curve.
     * @param xOffset  The x-offset for plotting.
     * @param yOffset  The y-offset for plotting.
     */
    public static void plotWindCurve(Graphics g, List<Segment> segments,
                                     int xOffset, int yOffset) {
        int x = xOffset; // Starting x-coordinate
        int y = yOffset; // Starting y-coordinate

        for (int i = 0; i < segments.size(); i++) {
            Segment segment = segments.get(i);
            double length = segment.getLength();
            double orientation = segment.getOrientation();
            Color segmentColor = segment.getColor();

            // Calculate the ending coordinates based on length and orientation
            int endX = x + (int) (length * Math.cos(Math.toRadians(orientation)));
            int endY = y - (int) (length * Math.sin(Math.toRadians(orientation)));

            // Set the color for this segment
            g.setColor(segmentColor);

            // Draw a line segment
            g.drawLine(x, y, endX, endY);

            // Update the starting coordinates for the next segment
            x = endX;
            y = endY;
        }
    }
    public static void saveImage(BufferedImage image, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        ImageIO.write(image, "png", outputFile);
    }
}
