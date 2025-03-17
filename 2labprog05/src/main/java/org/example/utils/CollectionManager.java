package org.example.utils;

import org.example.collection.Coordinates;
import org.example.collection.LocationFrom;
import org.example.collection.LocationTo;
import org.example.collection.Route;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Manages a collection of Route objects.
 * Stores and retrieves data from a CSV file.
 *
 */
public class CollectionManager {
   // The set that stores all routes.
    private final HashSet<Route> routeSet = new HashSet<>();
    private final String CSV_FILE = "routes.csv";
    private final LocalDateTime initializationDate;
    private final CommandManager commandManager;

    /**
        * Initializes the collection manager and loads data from the CSV file.
     */
    public CollectionManager( CommandManager commandManager ) {

        this.initializationDate = LocalDateTime.now();  // ✅ Store the initialization time
        this.commandManager = commandManager;
       loadFromCSV();


        if (CSV_FILE == null || CSV_FILE.isEmpty())

            System.out.println("Error: Environment variable 'ROUTE_FILE' is not set or empty.");
//        System.exit(1); // ❌ Stop execution if the filename is not provided


        loadFromCSV();


    }

    public HashSet<Route> getRouteSet() {
        return routeSet;

    }

    /**
     * Get a route by ID.
     */
    public Route getRouteById(long id) {
        for (Route route : routeSet) {
            if (route.getId() == id) {
                return route;
            }
        }
        return null;
    }

    /**
     * Updates an existing route by ID.
     * Returns `true` if the update is successful, `false` if the ID does not exist.
     */
    public boolean updateRouteById(long id, Route updatedRoute) {
        Route existingRoute = getRouteById(id);
        if (existingRoute == null) {
            return false; // ID not found
        }

        // Remove old entry and add the updated one
        routeSet.remove(existingRoute);
        routeSet.add(updatedRoute);
        return true;
    }


    public void addRoute(Route route) {
        boolean added = routeSet.add(route);
        if (added) {
            System.out.println("Route added successfully.");
        } else {
            System.out.println("Route with the same ID already exists.");
        }
        System.out.println( "Current Routes in CollectionManager -> " + routeSet.size());

        // Immediately save after adding
        saveToCSV();
    }




    public void removeRouteById(long id) {
        Route toRemove = null;
        for (Route route : routeSet) {
            if (route.getId() == id) {
                toRemove = route;
                break;
            }
        }
        if (toRemove != null) {
            routeSet.remove(toRemove);
            System.out.println("Route removed successfully.");
        } else {
            System.out.println("Route with ID " + id + " not found.");
        }
    }

    public void clearRoutes() {
        routeSet.clear();
        System.out.println("ALL routes have been cleared.");
    }



    public void showRoutes() {
        if (routeSet.isEmpty()) {
            System.out.println("No routes available.");
        } else {
            routeSet.forEach(System.out::println);
        }
    }
    /**
     * Displays information about the collection, including type, initialization date, and number of elements.
     */
    public void displayCollectionInfo() {
        System.out.println("Collection Type: " + routeSet.getClass().getSimpleName());
        System.out.println("Initialization Date: " + initializationDate);
        System.out.println("Number of Elements: " + routeSet.size());
    }
    /**
     * Reads and executes commands from a script file.
     * The script contains commands in the same form as user input.
     *
     * @param fileName The script file containing commands.
     */
    public void executeScript(String fileName) {
        File scriptFile = new File(fileName);

        if (!scriptFile.exists() || !scriptFile.isFile()) {
            System.out.println("Error: Script file not found -> " + fileName);
            return;
        }

        try (Scanner fileScanner = new Scanner(scriptFile)) {
            System.out.println("Executing script: " + fileName);

            while (fileScanner.hasNextLine()) {
                String command = fileScanner.nextLine().trim();
                if (!command.isEmpty()) {
                    System.out.println("> " + command);  // Show command being executed
                    commandManager.executeCommand(command);
                }
            }

            System.out.println("Script execution completed.");
        } catch (FileNotFoundException e) {
            System.out.println("Error reading script file: " + e.getMessage());
        }
    }




    public void saveToCSV() {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(CSV_FILE), StandardCharsets.UTF_8)) {
            writer.write("id,name,x,y,creationDate,from_x,from_y,from_z,from_name,to_x,to_y,to_z,distance\n");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Route route : routeSet) {
                writer.write(route.getId() + "," +
                        route.getName() + "," +
                        route.getCoordinates().getX() + "," +
                        route.getCoordinates().getY() + "," +
                        route.getCreationDate().format(formatter) + "," +
                        route.getFrom().getX() + "," +
                        route.getFrom().getY() + "," +
                        route.getFrom().getZ() + "," +
                        route.getFrom().getName() + "," +
                        route.getTo().getX() + "," +
                        route.getTo().getY() + "," +
                        route.getTo().getZ() + "," +
                        route.getDistance() + "\n");
            }

            System.out.println("Routes saved to CSV file.");
        } catch (IOException e) {
            System.out.println("Error saving to CSV: " + e.getMessage());
        }
    }


    public void loadFromCSV() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.out.println("CSV file not found. A new collection will be created.");
            return;
        }

        try (Scanner scanner = new Scanner(new FileInputStream(CSV_FILE), StandardCharsets.UTF_8)) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header line

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(","); // Use "," as the delimiter


                if (parts.length != 13) { // Adjusted to match new fields
                    System.out.println("Invalid line format: " + line);
                    continue;
                }

                try {
                    long id = Long.parseLong(parts[0]);
                    String name = parts[1];
                    int x = Integer.parseInt(parts[2]);
                    int y = Integer.parseInt(parts[3]);
                    LocalDateTime creationDate = LocalDateTime.parse(parts[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    Float fromX = Float.parseFloat(parts[5]);  // Updated for Float
                    Long fromY = Long.parseLong(parts[6]);     // Updated for Long
                    float fromZ = Float.parseFloat(parts[7]);  // Added for float
                    String fromName = parts[8];                // String
                    float toX = Float.parseFloat(parts[9]);    // Updated for float
                    Double toY = Double.parseDouble(parts[10]); // Updated for Double
                    Long toZ = Long.parseLong(parts[11]);       // Updated for Long
                    int distance = Integer.parseInt(parts[12]); // Updated index

                    Route route = new Route(
                            id,
                            name,
                            new Coordinates(x, y),
                            creationDate,
                            new LocationFrom(fromX, fromY, fromZ, fromName),  // Updated constructor call
                            new LocationTo(toX, toY, toZ),                    // Updated constructor call
                            distance
                    );
                    if (!routeSet.contains(route)) { //  Prevent duplicate routes
                        routeSet.add(route); // Only add if it does NOT already exist
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Error parsing number: " + e.getMessage());
                }
            }
            System.out.println("Routes successfully loaded from CSV -> " + routeSet.size());
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }
}

