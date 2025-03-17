package org.example; // Add this line at the top

import org.example.utils.CollectionManager;
import org.example.utils.CommandManager;
import org.example.utils.ConsoleManager;
import java.util.Scanner; // Import Scanner

public class Main {
    public static void main(String[] args) {
        // First, create CollectionManager without CommandManager (temporary)

        CollectionManager collectionManager = new CollectionManager(null);
        // Now, create CommandManager and pass the CollectionManager
        CommandManager commandManager = new CommandManager(collectionManager );
        Scanner scanner = new Scanner(System.in); // Create Scanner object

        // Create ConsoleManager and start interactive mode

        ConsoleManager consoleManager = new ConsoleManager(commandManager); // Pass scanner

        consoleManager.start(); // Start interactive mode
    }
}
