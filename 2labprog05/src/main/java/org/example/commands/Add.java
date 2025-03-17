package org.example.commands;

import org.example.collection.Coordinates;
import org.example.collection.LocationFrom;
import org.example.collection.LocationTo;
import org.example.collection.Route;
import org.example.utils.CollectionManager;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Add extends AbstrantCommands {
    private final Scanner scanner = new Scanner(System.in);

    public Add(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String arg) {
        System.out.println("Executing command -> add with argument ->");

        // Generate a unique ID
        long generatedId = new Random().nextLong() & Long.MAX_VALUE; // Ensures a positive ID
        System.out.println("ID automatically generated: " + generatedId);

        // Get Route Name
        System.out.print("Enter route name: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Route name cannot be empty. Enter route name: ");
            name = scanner.nextLine().trim();
        }

        // Get Coordinates
        System.out.print("Enter X coordinate: ");
        int x = getIntInput();
        System.out.print("Enter Y coordinate: ");
        int y = getIntInput();

        // Auto-generate creation date
        LocalDateTime creationDate = LocalDateTime.now();

        // Get LocationFrom details
        System.out.print("Enter 'From' location X: ");
        float fromX = getFloatInput();
        System.out.print("Enter 'From' location Y: ");
        long fromY = getLongInput();
        System.out.print("Enter 'From' location Z: ");
        float fromZ = getFloatInput();
        System.out.print("Enter 'From' location name: ");
        String fromName = scanner.nextLine().trim();

        // Get LocationTo details
        System.out.print("Enter 'To' location X: ");
        float toX = getFloatInput();
        System.out.print("Enter 'To' location Y: ");
        double toY = getDoubleInput();
        System.out.print("Enter 'To' location Z: ");
        long toZ = getLongInput();

        // Get Distance
        System.out.print("Enter distance (must be >1): ");
        int distance = getIntInput();
        while (distance <= 1) {
            System.out.print("Distance must be greater than 1. Enter distance again: ");
            distance = getIntInput();
        }

        // Create new Route and add it to the collection
        Route route = new Route(
                generatedId,
                name,
                new Coordinates(x, y),
                creationDate,
                new LocationFrom(fromX, fromY, fromZ, fromName),
                new LocationTo(toX, toY, toZ),
                distance
        );

        collectionManager.addRoute(route);
        System.out.println("Route successfully added!");
    }

    // Helper method to get an integer input
    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a valid integer: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // Helper method to get a float input
    private float getFloatInput() {
        while (!scanner.hasNextFloat()) {
            System.out.print("Invalid input. Please enter a valid float: ");
            scanner.next();
        }
        return scanner.nextFloat();
    }

    // Helper method to get a long input
    private long getLongInput() {
        while (!scanner.hasNextLong()) {
            System.out.print("Invalid input. Please enter a valid long value: ");
            scanner.next();
        }
        return scanner.nextLong();
    }

    // Helper method to get a double input
    private double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid double value: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
