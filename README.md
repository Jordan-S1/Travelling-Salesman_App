# Travelling-Salesman_App

This project is a Java-based application that solves the Travelling Salesman Problem (TSP) using the Nearest Neighbor algorithm. The application provides a graphical user interface (GUI) for users to input location data, compute the shortest route, and visualize the results.

## Features

- **Graphical User Interface**: A user-friendly interface built with Java Swing.
- **Input Handling**: Accepts location data in a specific format via a text area.
- **Nearest Neighbor Algorithm**: Implements the nearest neighbor heuristic to compute the shortest route.
- **Output Visualization**: Displays the computed shortest route in a text area.
- **Distance Calculation**: Calculates distances between locations using latitude and longitude.

## How to Run

1. Ensure you have Java installed on your system.
2. Open the project in Visual Studio Code or any Java IDE.
3. Compile the source code:
   ```sh
   javac -d bin src/*.java
   ```
4. Run the application:
   ```sh
   java -cp bin TSP
   ```

## Input Format

The input data should be provided in the following format:

```sh
<id>,<address>,<number>,<latitude>,<longitude>
```

Example:

```sh
1,38 Parsons Hall Maynooth,4,53.37521,-6.6103
2,34 Silken Vale Maynooth,6,53.37626,-6.59308
```

## Sample Data

A sample input file is provided as TSP Data Sample.txt for testing purposes.
