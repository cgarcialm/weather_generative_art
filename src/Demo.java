import java.util.List;

public class Demo {

    /**
     * The main method reads JSON data from a file, parses it into a list of WeatherData objects,
     * generates wind curves, and plots them.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        final String INPUT_JSON_PATH = "input/bsas_2022.txt";
        final int WIDTH = 1200;
        final int HEIGHT = 800;
        final int X_OFFSET = 0; // Adjust to control the horizontal offset
        final int Y_OFFSET = 0; // Adjust to control the horizontal offset
        final int X_SPACING = 2; // Adjust to control the horizontal spacing
        final int Y_SPACING = 6; // Adjust to control the vertical spacing

        WeatherArtGenerator gen = new WeatherArtGenerator(
                INPUT_JSON_PATH, WIDTH, HEIGHT,
                X_OFFSET, Y_OFFSET, X_SPACING, Y_SPACING
        );

        try {
            // Read JSON data from the file
            String jsonData = JSONProcessor.readJSONFromFile(INPUT_JSON_PATH);

            // Parse the JSON data into a list of WeatherData objects
            List<WeatherData> weatherDataList = JSONProcessor.parseJSONData(jsonData);

            List<WeatherArtGenerator.Segment> windSegments =
                    WeatherArtGenerator.generateWindCurve(weatherDataList);

            List<List<WeatherArtGenerator.Segment>> windSubegments =
                    WeatherArtGenerator.divideSegments(windSegments);

            // Create and display the JFrame
            WeatherArtGenerator.generateWindArt(
                    windSubegments,
                    WIDTH, HEIGHT, X_OFFSET, Y_OFFSET, X_SPACING, Y_SPACING
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
