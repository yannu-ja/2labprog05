package org.example.commands;

import org.example.collection.Coordinates;
import org.example.collection.LocationFrom;
import org.example.collection.LocationTo;
import org.example.collection.Route;
import org.example.utils.CollectionManager;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UpdateById extends AbstrantCommands {
    private final Scanner scanner = new Scanner(System.in);

    public UpdateById(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String arg) {
        if (arg.isEmpty()) {
            System.out.println("Error: No ID provided. Usage: update_by_id <id>");
            return;
        }

        long id;
        try {
            id = Long.parseLong(arg);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a valid numeric ID.");
            return;
        }

        Route existingRoute = collectionManager.getRouteById(id);
        if (existingRoute == null) {
            System.out.println("Error: No route found with ID: " + id);
            return;
        }

        System.out.println("Updating Route with ID: " + id);

        // Get valid route name
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine().trim();
        while (newName.isEmpty()) {
            System.out.print("Invalid input. Please enter a valid name: ");
            newName = scanner.nextLine().trim();
        }

        // Get valid coordinates
        int x = getIntInput("Enter new coordinates (x): ");
        int y = getIntInput("Enter new coordinates (y): ");

        // Get valid "from" location
        float fromX = getFloatInput("Enter new 'from' X coordinate: ");
        long fromY = getLongInput("Enter new 'from' Y coordinate: ");
        float fromZ = getFloatInput("Enter new 'from' Z coordinate: ");
        System.out.print("Enter new 'from' name: ");
        String fromName = scanner.nextLine().trim();

        // Get valid "to" location
        float toX = getFloatInput("Enter new 'to' X coordinate: ");
        double toY = getDoubleInput("Enter new 'to' Y coordinate: ");
        long toZ = getLongInput("Enter new 'to' Z coordinate: ");

        // Get valid distance
        int distance = getIntInput("Enter new distance: ");

        // Create the updated route
        Route updatedRoute = new Route(
                id,
                newName,
                new Coordinates(x, y),
                LocalDateTime.now(),
                new LocationFrom(fromX, fromY, fromZ, fromName),
                new LocationTo(toX, toY, toZ),
                distance
        );

        boolean success = collectionManager.updateRouteById(id, updatedRoute);
        if (success) {
            System.out.println("Route updated successfully.");
        } else {
            System.out.println("Failed to update route. Route with ID " + id + " not found.");
        }
    }

    /**
     * Helper method to get valid integer input
     */
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    /**
     * Helper method to get valid float input
     */
    private float getFloatInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextFloat();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid float.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    /**
     * Helper method to get valid long input
     */
    private long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid long.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    /**
     * Helper method to get valid double input
     */
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid double.");
                scanner.next(); // Clear invalid input
            }
        }
    }
}
