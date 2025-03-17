package org.example.commands;

import org.example.utils.CollectionManager;

public abstract class AbstrantCommands {
    protected final CollectionManager collectionManager;

    public AbstrantCommands(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Each command must implement this method.
     */
    public abstract void execute(String arg); // FIXED: No `{}` and removed @Override
}
