# Weather Generative Art

The Weather Art Generator is a Java application that creates generative art based on weather data. It generates wind curves and plots them, resulting in visually appealing representations of weather conditions.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Features

- Reads weather data from a JSON file.
- Parses the data into a list of weather parameters.
- Generates wind curves based on wind speed and direction.
- Creates generative art by plotting wind curves.
- Saves the art as an image file.

## Installation

1. **Ensure you have Java and Dependencies Installed**

   Before using the Weather Art Generator, make sure you have Java installed on your machine. You can download and install Java from the official [Java website](https://www.oracle.com/java/technologies/javase-downloads.html).

2. **Clone the Repository**

   Clone the repository to your local machine using Git:

   ```bash
   git clone https://github.com/yourusername/weather-art-generator.git
   ```

2. **Navigate to the Project Directory**

   Open a terminal and navigate to the project directory:

   ```bash
   cd weather-art-generator
   ```

3. **Run the Weather Art Generator**

   To generate weather art, follow these steps:
   - Place your weather data in the input/data.txt file in JSON format.
   - Open a terminal and navigate to the project directory.
   - Run the Weather Art Generator:
     
   ```bash
   java WeatherArtGenerator
   ```

  - The generated art will be saved as output/weather_art.png.

## Project Structure

The repository is organized as follows:

- `WeatherArtGenerator.java`: The main application entry point.
- `JSONReader.java`: Reads JSON data from a file.
- `JSONParser.java`: Parses JSON data into weather parameters.
- `WeatherData.java`: Represents weather parameters.
- `WindCurveGenerator.java`: Generates wind curves.
- `WindSegmentDivider.java`: Divides wind segments into subsegments.
- `FrameGenerator.java`: Creates and displays the graphical frame.
- `Segment.java`: Represents a line segment.
- `input/data.txt`: Sample JSON weather data (replace with your own data).
- `output/weather_art.png`: The generated art image.

## Contributing

Contributions are welcome! If you have ideas for improvements or new features, please open an issue or create a pull request.

## Examples

Here are two examples of generative art images created using the Weather Art Generator for the cities of Buenos Aires and New York in 2022.

### Buenos Aires 2022

![weather_art_ba_2022](https://github.com/cgarcialm/weather_generative_art/assets/62184644/4620e151-2cd7-4d78-a748-9b78dcc835f4)

_Description: Generative art representing weather conditions in Buenos Aires in 2022._

### New York 2022

![weather_art_ny_2022](https://github.com/cgarcialm/weather_generative_art/assets/62184644/66608534-ccd3-4ea7-ac5e-e62301454104)

_Description: Generative art representing weather conditions in New York in 2022._

These examples demonstrate the creative possibilities of the Weather Art Generator and how it can visualize weather data for different locations and times.





