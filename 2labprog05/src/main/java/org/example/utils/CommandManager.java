package org.example.utils;

import org.example.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final CollectionManager collectionManager;
    private final Map<String, AbstrantCommands> commands = new HashMap<>();

    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        registerCommands();
    }

    private void registerCommands() {
        commands.put("help", new Help(collectionManager));
        commands.put("show", new Show(collectionManager));
        commands.put("add", new Add(collectionManager));
        commands.put("update_by_id", new UpdateById(collectionManager));
        commands.put("remove_by_id", new RemoveById(collectionManager));
        commands.put("save", new Save(collectionManager));
        commands.put("exit", new Exit(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("info", new Info(collectionManager));
        commands.put("execute_script", new ExecuteScript(collectionManager));

    }

    public void executeCommand(String commandLine) {
        if (commandLine == null || commandLine.trim().isEmpty()) {
            return; // Ignore empty inputs
        }

        String[] parts = commandLine.trim().split("\\s+", 2);
        String commandName = parts[0];
        String argument = (parts.length > 1) ? parts[1] : "";
        System.out.println("DEBUG: Executing command -> " + commandName + " with argument -> " + argument);

        AbstrantCommands command = commands.get(commandName);

        if (command != null) {
            command.execute(argument);
        } else {
            System.out.println("Error: Unknown command '" + commandName + "'. Type 'help' for a list of commands.");

            System.out.println("Error: Unknown command '" + commandName + "'. Type 'help' for a list of commands.");
        }
    }
}