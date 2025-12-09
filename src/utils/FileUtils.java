package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    /**
     * Reads the data from a provided file.
     * 
     * @param file the path to the file
     * 
     * @return {@code List<String>} where each element is a line from the file
     *          or {@code null} if an {@code IOException} occurred.
     * 
     * @throws FileNotFoundException if there is no file with the provided path
     */
    public static List<String> readFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        
        if (!file.exists())
            throw new FileNotFoundException("No file with the provided path.");

        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            return lines;
        } catch (IOException e) {
            System.err.println("I/O Error: " + e);
            return null;
        }
    }

    /**
     * Creates a file and write the specified data on it 
     * or append the data to an existing file.
     * <p>
     *      The writing process is atomic, if any exception happend then
     *      no data will be added to the file at all.
     * </p>
     * 
     * @param filePath the path to the file
     * @param data the data to write
     */
    public static void saveFile(String filePath, String data) {
        /* 
         *                     ------------ IMPORTANT ------------
         * Atomic File Replacement: For ensuring that a file is either fully updated 
         * with new content or remains with its old content (without an intermediate state),
         * a common pattern is to write the new content to a temporary file and then 
         * atomically rename the temporary file to replace the original. 
         * This approach is often used for configuration files or data files 
         * where consistency is crucial.
         */
        File file = new File(filePath);

        try {
            if (file.createNewFile()) {
                //TODO write data to the file directly
            } else {
                //TODO append data to the existing file
            }
        } catch (IOException e) {

        }
    }
}
