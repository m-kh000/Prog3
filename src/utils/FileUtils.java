package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import core.Factory;
import core.Item;
import core.Product;
import core.ProductLine;
import core.User;
import jsonParser.*;

public class FileUtils {
    private static final Object FILE_LOCK = new Object();

    private static final File USERS_FILE = new File("./files/Users.json");
    private static final File ITEMS_FILE = new File("./files/Items.json");
    private static final File PRODUCTS_FILE = new File("./files/Products.json");
    private static final File PRODUCTLINES_FILE = new File("./files/ProductLines.json");

    private static class ProductLinesPaths {
        private List<String> productLinesPaths;

        public ProductLinesPaths() {}

        public ProductLinesPaths(List<String> l) {
            this.productLinesPaths = l;
        }

        public String[] getProductLinesPaths() {
            return this.productLinesPaths.toArray(new String[this.productLinesPaths.size()]);
        }
    }

    /*
     * Reads the data from a provided file.
     * 
     * @param file the path to the file
     * 
     * @return {@code List<String>} where each element is a line from the file
     *          or {@code null} if an {@code IOException} occurred.
     * 
     * @throws FileNotFoundException if there is no file with the provided path
     */
    /*
    public static List<String> readFile(String filePath, Class<?> type) throws FileNotFoundException {
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
    */

    public static List<Item> readItems() throws IOException {
        synchronized (FILE_LOCK) {
            if (!ITEMS_FILE.exists()) {
                return new ArrayList<>();
            }
            
            StringBuilder sb = new StringBuilder();
            String line = "";

            try (BufferedReader reader = new BufferedReader(new FileReader(ITEMS_FILE))) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                Item[] i = JsonParser.fromJson(sb.toString(), Item[].class);

                return new ArrayList<>(Arrays.asList(i));
            }
        }
    }
    public static List<Product> readProducts() throws IOException {
        synchronized (FILE_LOCK) {
            if (!PRODUCTS_FILE.exists()) {
                return new ArrayList<>();
            }
            
            StringBuilder sb = new StringBuilder();
            String line = "";

            try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                Product[] p = JsonParser.fromJson(sb.toString(), Product[].class, Item.class, Integer.class, null);

                return new ArrayList<>(Arrays.asList(p));
            }
        }
    }

    public static HashSet<ProductLine> readProductLines() throws IOException {
        synchronized (FILE_LOCK) {
            if (!PRODUCTLINES_FILE.exists()) {
                return new HashSet<>();
            }

            StringBuilder sb = new StringBuilder();
            String line = "";

            try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTLINES_FILE))) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                ProductLine[] pl = JsonParser.fromJson(sb.toString(), ProductLine[].class, Item.class, Integer.class, null);
                
                return new HashSet<>(Arrays.asList(pl));
            }
        }
    }

    /**
     * This method saves all users in the users list in the provided factory.
     * 
     * <p>
     * Writes the data to ./files/Users.txt using a {@code BufferedWriter} that wraps a {@code FileWriter}
     * in a class level synchronization to make the writing process thread safe.
     * </p>
     * <p>
     * This method automatically creates the Users.txt file with its parent directories 
     * if it does not exist.
     * </p>
     * 
     * @param factory the factory that holds the users list
     */
    public static void saveUsers(Factory factory) throws IOException {
        synchronized (FILE_LOCK) {
            List<User> users = factory.getUsers();
    
            USERS_FILE.getParentFile().mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
    
                if (!USERS_FILE.exists()) {
                    USERS_FILE.createNewFile();
                }
    
                for (User u : users) {
                    writer.write(u.getFileFormat());
                    writer.newLine();                
                }
            }
        }
    }

    /**
     * This method saves all items in the items hashmap in the provided factory.
     * 
     * <p>
     * Writes the data to ./files/Items.txt using a {@code BufferedWriter} that wraps a {@code FileWriter}
     * in a class level synchronization to make the writing process thread safe.
     * </p>
     * <p>
     * This method automatically creates the Items.txt file with its parent directories
     * if it does not exist.
     * </p>
     * 
     * @param factory the factory that holds the items list
     */
    public static void saveItems(Factory factory) throws IOException {
        synchronized (FILE_LOCK) {
            List<Item> items = new ArrayList<>(factory.getWarehouse().getItems());
    
            ITEMS_FILE.getParentFile().mkdirs();
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE))) {
                
                String itemsJson = JsonParser.toJson(items);

                if (!ITEMS_FILE.exists()) {
                    ITEMS_FILE.createNewFile();
                }
                
                writer.write(itemsJson);
            } catch (IllegalAccessException e) {
                /* log the exception to Exceptions.txt */
            }
        }
    }

    /**
     * This method saves all products in the products hashmap in the provided factory.
     * 
     * <p>
     * Writes the data to ./files/Products.txt using a {@code BufferedWriter} that wraps a {@code FileWriter}
     * in a class level synchronization to make the writing process thread safe.
     * </p>
     * <p>
     * This method automatically creates the Products.txt file with its parent directories
     * if it does not exist.
     * </p>
     * 
     * @param factory the factory that holds the products list
     */
    public static void saveProducts(Factory factory) throws IOException {
        synchronized (FILE_LOCK) {
            List<Product> products = new ArrayList<>(factory.getWarehouse().getProducts());

            PRODUCTS_FILE.getParentFile().mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE))) {

                String productsJson = JsonParser.toJson(products);

                if (!PRODUCTS_FILE.exists()) {
                    PRODUCTS_FILE.createNewFile();
                }

                writer.write(productsJson);
            } catch (IllegalAccessException e) {
                /* log the exception to Exceptions.txt */
            }
        }
    }

    public static void saveProductLines(Factory factory) throws IOException {
        synchronized (FILE_LOCK) {
            HashSet<ProductLine> productLines = new HashSet<>(factory.getAllLines());

            PRODUCTLINES_FILE.getParentFile().mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTLINES_FILE))) {
                
                String productLinesJson = JsonParser.toJson(productLines);

                if (!PRODUCTLINES_FILE.exists()) {
                    PRODUCTLINES_FILE.createNewFile();
                }

                writer.write(productLinesJson);
            } catch (IllegalAccessException e) {
                /* log the exception to Exceptions.txt */
            }
        }
    }

    public static void saveTasks(ProductLine productLine) {
        synchronized (FILE_LOCK) {
            
        }
    }
}
