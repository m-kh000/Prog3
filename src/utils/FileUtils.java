package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import core.Factory;
import core.Item;
import core.Product;
import core.ProductLine;
import core.Task;
import core.User;
import jsonParser.*;

public class FileUtils {
    private static final Object FILE_LOCK = new Object();

    private static final File FILES = new File("./files/");
    private static final File USERS_FILE = new File("./files/Users.json");
    private static final File ITEMS_FILE = new File("./files/Items.json");
    private static final File PRODUCTS_FILE = new File("./files/Products.json");
    private static final File PRODUCTLINESPATHS_FILE = new File("./files/ProductlinesPaths.json");
    private static final File EXCEPTIONS_FILE = new File("./files/Exceptions.txt");

    public static List<User> readUsers() throws IOException {
        synchronized (FILE_LOCK) {
            if (!USERS_FILE.exists()) {
                return new ArrayList<>();
            }

            User[] users = JsonParser.fromJson(readData(USERS_FILE), User[].class);
            return new ArrayList<>(Arrays.asList(users));
        }
    }

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

    public static HashSet<ProductLine> readProductLines() {
        synchronized (FILE_LOCK) {
            try {
                if (!PRODUCTLINESPATHS_FILE.exists()) {
                    throw new IOException("Couldn\'t find the path: " + PRODUCTLINESPATHS_FILE.getAbsolutePath());
                }

                String[] productLinesPaths = JsonParser.fromJson(readData(PRODUCTLINESPATHS_FILE),
                                                         String[].class);

                HashSet<ProductLine> productLines = new HashSet<>();
                
                for (int i = 0; i < productLinesPaths.length; i++) {
                    File file = new File(productLinesPaths[i]);
                    if (!file.exists() || !file.isDirectory()) {
                        throw new IOException("Path does not exist or is not a directory: " + file.getAbsolutePath());
                    }
                    
                    File[] plFiles = file.listFiles();
                    if (plFiles == null || plFiles.length <= 0) {
                        throw new IOException("Couldn\'t find directory or directory is empty: " + file.getAbsolutePath());
                    }

                    ProductLine pl = new ProductLine();
                    List<Task> inline = new ArrayList<>();
                    List<Task> inprogress = new ArrayList<>();
                    List<Task> completed = new ArrayList<>();
                    List<Task> canceled = new ArrayList<>();
                    for (File f : plFiles) {
                        String fileName = f.getName();
                        switch (fileName) {
                            case "Productline.json":
                                pl = JsonParser.fromJson(readData(f), ProductLine.class);
                                break;
                            case "inline.json":
                                Task[] inlineArr = JsonParser.fromJson(readData(f), Task[].class, LocalDate.class);
                                inline = Arrays.asList(inlineArr);
                                break;
                            case "inprogress.json":
                                Task[] inprogressArr = JsonParser.fromJson(readData(f), Task[].class, LocalDate.class);
                                inprogress = Arrays.asList(inprogressArr);
                                break;
                            case "completed.json":
                                Task[] completedArr = JsonParser.fromJson(readData(f), Task[].class, LocalDate.class);
                                completed = Arrays.asList(completedArr);
                                break;
                            case "canceled.json":
                                Task[] canceledArr = JsonParser.fromJson(readData(f), Task[].class, LocalDate.class);
                                canceled = Arrays.asList(canceledArr);
                                break;
                        }
                    }
                    pl.setInline(inline);
                    pl.setInprogress(inprogress);
                    pl.setCompleted(completed);
                    pl.setCanceled(canceled);

                    productLines.add(pl);
                }
                return new HashSet<>(productLines);
            } catch (IOException e) {
                log(e);
                return new HashSet<>();
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
            
            try {
                createFile(USERS_FILE);
                writeData(USERS_FILE, JsonParser.toJson(users), false);                
            } catch (IllegalAccessException e) {
                log(e);
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
            
            try {
                createFile(ITEMS_FILE);
                writeData(ITEMS_FILE, JsonParser.toJson(items), false);
            } catch (IllegalAccessException e) {
                log(e);
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
     * @throws IOException
     */
    public static void saveProducts(Factory factory) throws IOException {
        synchronized (FILE_LOCK) {
            List<Product> products = new ArrayList<>(factory.getWarehouse().getProducts());

            try {
                createFile(PRODUCTS_FILE);
                writeData(PRODUCTS_FILE, JsonParser.toJson(products), false);
            } catch (IllegalAccessException e) {
                log(e);
            }
        }
    }

    /**
     * Saves Productlines in ./files/[PL's name]: a file for the basic data of the productline,
     * and a file for each tasks list.
     * @param factory the factory that holds the productlines set
     * @throws IOException
     */
    public static void saveProductLines(Factory factory) throws IOException {
        synchronized (FILE_LOCK) {
            HashSet<ProductLine> productLines = new HashSet<>(factory.getAllLines());

            FILES.mkdirs();

            List<String> productLinesPaths;
            if (!PRODUCTLINESPATHS_FILE.exists()) {
                createFile(PRODUCTLINESPATHS_FILE);
                productLinesPaths = new ArrayList<>();
            } else {
                String pathsJson = readData(PRODUCTLINESPATHS_FILE);
                String[] temp = JsonParser.fromJson(pathsJson, String[].class);
                productLinesPaths = new ArrayList<>(Arrays.asList(temp));
            }

            try {
                for (ProductLine pl : productLines) {
                    String plPath = FILES.toString() + "\\" + pl.getLineName();

                    if (!productLinesPaths.contains(plPath)) {
                        productLinesPaths.add(plPath);
                    }
    
                    File file = new File(plPath, "Productline.json");
                    createFile(file);
                    writeData(file, JsonParser.toJson(pl), false);
    
                    file = new File(plPath, "inline.json");
                    createFile(file);
                    writeData(file, JsonParser.toJson(pl.getInlineTasks()), false);
    
                    file = new File(plPath, "inprogress.json");
                    createFile(file);
                    writeData(file, JsonParser.toJson(pl.getInprogressTasks()), false);

                    file = new File(plPath, "completed.json");
                    createFile(file);
                    writeData(file, JsonParser.toJson(pl.getCompletedTasks()), false);

                    file = new File(plPath, "canceled.json");
                    createFile(file);
                    writeData(file, JsonParser.toJson(pl.getCanceledTasks()), false);
                }
                writeData(PRODUCTLINESPATHS_FILE, JsonParser.toJson(productLinesPaths), false);
            } catch (IllegalAccessException e) {
                log(e);
            }

        }
    }

    /**
     * Logs an {@code Exception} along with its message in ./files/Exceptions.txt
     * @param exception the exception to log
     */
    public static void log(Exception exception) {
        synchronized (FILE_LOCK) {
            try {
                createFile(EXCEPTIONS_FILE);
                String logString = exception.getClass().getSimpleName() 
                                 + " occurred, Exception\'s message: "
                                 + exception.getMessage()
                                 + "\n";

                writeData(EXCEPTIONS_FILE, logString, true);
            } catch (Exception e) {
                System.out.println("Exception occurred while logging the exception. LOL :)");
            }
        }
    }


    /**
     * Creates the specified file along with its necessary directories.
     * @param file
     * @throws IOException
     */
    private static void createFile(File file) throws IOException{
        if (file == null) return;

        file.getParentFile().mkdirs();

        if (!file.isDirectory() && !file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * Writes the sent data to the specified file.
     * <p>
     *  NOTE: this method does not checks for the existance of the file you must do it manually.
     * </p> 
     * @param file the file to write the data on
     * @param data  the data to write
     * @param append if {@code true}, the data will be appended to the file 
     * @throws IOException
     */
    private static void writeData(File file, String data, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            writer.write(data);
        }
    }

    /**
     * Reads data from the specified file.
     * <p>
     *  NOTE: this method does not checks for the existance of the file you must do it manually.
     * </p> 
     * @param file the file to read from
     * @return a {@code String} containing the data
     * @throws IOException
     */
    private static String readData(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        return sb.toString();
    }
}
