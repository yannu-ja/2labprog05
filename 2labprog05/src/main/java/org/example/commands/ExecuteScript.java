package org.example.commands;

import org.example.utils.CollectionManager;

/**
 * Command to execute a script file containing a list of commands.
 */
public class ExecuteScript extends AbstrantCommands {

    public ExecuteScript(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String fileName) {
        if (fileName.isEmpty()) {
            System.out.println("Error: Missing file name for execute_script command.");
            return;
        }
        collectionManager.executeScript(fileName);
    }
}
