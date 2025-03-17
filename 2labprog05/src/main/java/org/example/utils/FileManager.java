package org.example.utils;

import java.io.*;
import java.util.Scanner;
/**
 * Manages file operations such as reading from and writing to a CSV file.
 * This class provides methods to obtain a {@link Scanner} for reading
 * and a {@link PrintWriter} for writing.
 */
public class FileManager {
    //The file path of the CSV file used for storage.
    private final String csvFilePath;
    /**
     * Constructs a {@code FileManager} with the specified file path.
     *
     * @param filePath The path to the CSV file.
     */

    public FileManager(String filePath) {
        this.csvFilePath = filePath;
    }

    public Scanner getScanner() throws FileNotFoundException {
        return new Scanner(new File(csvFilePath));
    }
    /**
     * Returns a {@link PrintWriter} to write data to the CSV file.
     * This method overwrites the existing content in the file.
     *
     * @return A {@link PrintWriter} instance for writing to the file.
     * @throws IOException If an error occurs while opening the file.
     */

    public PrintWriter getWriter() throws IOException {
        System.out.println("DEBUG: Writing to file -> " + csvFilePath);

        return new PrintWriter(new FileWriter(csvFilePath, false));
    }
}
