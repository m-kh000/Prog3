package utils;

import core.Factory;
import core.Item;
import core.Product;
import core.ProductLine;
import core.Task;
import core.User;
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
import jsonParser.*;

public class FileUtils {

    private static final Object FILE_LOCK = new Object();

    private static final File FILES = new File("./files");
    private static final File USERS_FILE = new File("./files/Users.json");
    private static final File ITEMS_FILE = new File("./files/Items.json");
    private static final File PRODUCTS_FILE = new File("./files/Products.json");
    private static final File PRODUCTLINESPATHS_FILE = new File("./files/ProductlinesPaths.json");
    private static final File EXCEPTIONS_FILE = new File("./files/Exceptions.txt");
    private static final File IDS_FILE = new File("./files/IDs.json");
    private static final File TASKSLOG_FILE = new File("./files/TasksLog.txt");

    /**
     * This method reads users from the Users.json file.
     *
     * <p>
     * reads the data from ./files/Users.json using a {@code BufferedReader}
     * that wraps a {@code FileReader} in a class level synchronization to make
     * the reading process thread safe.
     * </p>
     * <p>
     * if the Users.json file does not exist, the method will return an empty
     * {@code List<User>}.
     * </p>
     *
     * @return a {@code new List<User>} with the users if the file is found
     * and is not empty, or else returns an empty {@code List<User>}
     * @throws IOException
     */
    public static List<User> readUsers() throws IOException {
        synchronized (FILE_LOCK) {
            if (!USERS_FILE.exists()) {
                return new ArrayList<>();
            }

            User[] users = JsonParser.fromJson(readData(USERS_FILE), User[].class);
            return new ArrayList<>(Arrays.asList(users));
        }
    }

    /**
     * This method reads items from the Items.json file.
     *
     * <p>
     * reads the data from ./files/Items.json using a {@code BufferedReader}
     * that wraps a {@code FileReader} in a class level synchronization to make
     * the reading process thread safe.
     * </p>
     * <p>
     * if the Items.json file does not exist, the method will return an empty
     * {@code List<Item>}.
     * </p>
     *
     * @return a {@code new ArrayList<Item>} with the items if the file is found
     * and is not empty, or else returns an empty {@code ArrayList<Item>}
     * @throws IOException
     */
    public static List<Item> readItems() throws IOException {
        synchronized (FILE_LOCK) {
            if (!ITEMS_FILE.exists()) {
                return new ArrayList<>();
            }

            Item[] items = JsonParser.fromJson(readData(ITEMS_FILE), Item[].class);
            return new ArrayList<>(Arrays.asList(items));
        }
    }

    /**
     * This method reads products from the Products.json file.
     *
     * <p>
     * reads the data from ./files/Products.json using a {@code BufferedReader}
     * that wraps a {@code FileReader} in a class level synchronization to make
     * the reading process thread safe.
     * </p>
     * <p>
     * if the Products.json file does not exist, the method will return an empty
     * {@code List<Product>}.
     * </p>
     *
     * @return a {@code new ArrayList<Product>} with the products if the file is
     * found and is not empty, or else returns an empty
     * {@code ArrayList<Product>}
     * @throws IOException
     */
    public static List<Product> readProducts() throws IOException {
        synchronized (FILE_LOCK) {
            if (!PRODUCTS_FILE.exists()) {
                return new ArrayList<>();
            }

            Product[] products = JsonParser.fromJson(readData(PRODUCTS_FILE), Product[].class, String.class, Integer.class, LocalDate.class);
            return new ArrayList<>(Arrays.asList(products));
        }
    }

    /**
     * This method reads productlines from the Items.json file.
     *
     * <p>
     * Reads the data from ./files as following:
     * <li> - reads the paths for each productline folder from
     * ProductlinesPaths.json </li>
     * <li> - goes to each productline folder and reads the data from its files
     * as following: </li>
     * <li> - reads the main data of the productline from Productline.json </li>
     * <li> - reads the content of the inline {@code List<Task>} from
     * inline.json </li>
     * <li> - reads the content of the inprogress {@code List<Task>} from
     * inprogress.json </li>
     * <li> - reads the content of the completed {@code List<Task>} from
     * completed.json </li>
     *
     * It uses a {@code BufferedReader} that wraps a {@code FileReader} in a
     * class level synchronization to make the reading process thread safe.
     * </p>
     *
     * <p>
     * if the ProductlinesPaths.json file does not exist, the method will return
     * an empty {@code HashSet<ProductLine>}.
     * </p>
     *
     * @return a {@code new HashSet<ProductLine>} with the productlines if the
     * files are found and are not empty, or else returns an empty
     * {@code HashSet<ProductLine>}
     */
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
                    File folder = new File(productLinesPaths[i]);
                    if (!folder.exists() || !folder.isDirectory()) {
                        throw new IOException("Path does not exist or is not a directory: " + folder.getAbsolutePath());
                    }

                    File[] plFiles = folder.listFiles();
                    if (plFiles == null || plFiles.length <= 0) {
                        throw new IOException("Couldn\'t find directory or directory is empty: " + folder.getAbsolutePath());
                    }

                    ProductLine pl = new ProductLine();
                    List<Task> inline = new ArrayList<>();
                    List<Task> inprogress = new ArrayList<>();
                    List<Task> completed = new ArrayList<>();
                    for (File f : plFiles) {
                        String fileName = f.getName();
                        switch (fileName) {
                            case "Productline.json":
                                pl = JsonParser.fromJson(readData(f), ProductLine.class);
                                break;
                            case "inline.json":
                                Task[] inlineArr = JsonParser.fromJson(readData(f), Task[].class);
                                inline = Arrays.asList(inlineArr);
                                break;
                            case "inprogress.json":
                                Task[] inprogressArr = JsonParser.fromJson(readData(f), Task[].class);
                                inprogress = Arrays.asList(inprogressArr);
                                break;
                            case "completed.json":
                                Task[] completedArr = JsonParser.fromJson(readData(f), Task[].class);
                                completed = Arrays.asList(completedArr);
                                break;
                        }
                    }
                    pl.setInline(inline);
                    pl.setInprogress(inprogress);
                    pl.setCompleted(completed);

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
     * Reads IDs from IDS.json file.
     * 
     * @return an array of integers containing the global ids for each class
     * @throws IOException
     */
    public static int[] readIDs() throws IOException {
        synchronized (FILE_LOCK) {
            if (!IDS_FILE.exists()) {
                return new int[] {1, 1, 1, 1};
            }

            int[] ids = JsonParser.fromJson(readData(IDS_FILE), int[].class);
            return ids;
        }
    }

    /**
     * This method saves the provided user to ./files/Users.json .
     *
     * <p>
     * Writes the data to ./files/Users.json using a {@code BufferedWriter} that
     * wraps a {@code FileWriter} in a class level synchronization to make the
     * writing process thread safe.
     * </p>
     * <p>
     * This method automatically creates the Users.json file with its parent
     * directories if it does not exist.
     * </p>
     *
     * @param newUser the new user to save
     */
    public static void saveUser(User newUser) throws IOException {
        synchronized (FILE_LOCK) {
            List<User> users = readUsers();
            users.add(newUser);

            try {
                createFile(USERS_FILE);
                writeData(USERS_FILE, JsonParser.toJson(users), false);
            } catch (IllegalAccessException e) {
                log(e);
            }
        }
    }

    /**
     * This method saves all the items in the warehouse in the factory.
     *
     * <p>
     * Writes the data to ./files/Items.json using a {@code BufferedWriter} that
     * wraps a {@code FileWriter} in a class level synchronization to make the
     * writing process thread safe.
     * </p>
     * <p>
     * This method automatically creates the Items.json file with its parent
     * directories if it does not exist.
     * </p>
     *
     */
    public static void saveItems() throws IOException {
        synchronized (FILE_LOCK) {
            List<Item> items = new ArrayList<>(Factory.getWarehouse().getItems());

            try {
                createFile(ITEMS_FILE);
                writeData(ITEMS_FILE, JsonParser.toJson(items), false);
            } catch (IllegalAccessException e) {
                log(e);
            }
        }
    }

    /**
     * This method saves all products in the warehouse in the factory.
     *
     * <p>
     * Writes the data to ./files/Products.json using a {@code BufferedWriter}
     * that wraps a {@code FileWriter} in a class level synchronization to make
     * the writing process thread safe.
     * </p>
     * <p>
     * This method automatically creates the Products.json file with its parent
     * directories if it does not exist.
     * </p>
     *
     * @throws IOException
     */
    public static void saveProducts() throws IOException {
        synchronized (FILE_LOCK) {
            List<Product> products = new ArrayList<>(Factory.getWarehouse().getProducts());

            try {
                createFile(PRODUCTS_FILE);
                writeData(PRODUCTS_FILE, JsonParser.toJson(products), false);
            } catch (IllegalAccessException e) {
                log(e);
            }
        }
    }

    /**
     * Saves Productlines in ./files/[PL's name]: a .json file for the basic
     * data of the productline, and a .josn file for each tasks list.
     *
     * @throws IOException
     */
    public static void saveProductLines() throws IOException {
        synchronized (FILE_LOCK) {
            HashSet<ProductLine> productLines = new HashSet<>(Factory.getAllLines());

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
                    String plPath = FILES.toString() + "\\" + pl.getName();

                    if (!productLinesPaths.contains(plPath)) {
                        productLinesPaths.add(plPath);
                    }

                    File file = new File(plPath, "Productline.json");
                    createFile(file);
                    writeData(file, JsonParser.toJson(pl), false);

                    file = new File(plPath, "inline.json");
                    createFile(file);
                    writeData(file, JsonParser.toJson(pl.getInline()), false);

                    file = new File(plPath, "inprogress.json");
                    createFile(file);
                    writeData(file, JsonParser.toJson(pl.getInprogress()), false);

                    file = new File(plPath, "completed.json");
                    createFile(file);
                    writeData(file, JsonParser.toJson(pl.getCompleted()), false);

                }
                writeData(PRODUCTLINESPATHS_FILE, JsonParser.toJson(productLinesPaths), false);
            } catch (IllegalAccessException e) {
                log(e);
            }
        }
    }

    /**
     * Saves the next ID for each class in IDs.json file.
     *<p>
     *  Saves in an array of integers where [0] is the Item's id, [1] is the Product's id
     *  [2] is the Task's id and [3] is the PoductLine's id. 
     *</p>
     */
    public static void saveIDs() throws IOException {
        synchronized (FILE_LOCK) {
            int[] ids = new int[4];
            ids[0] = Item.getNextId();
            ids[1] = Product.getNextId();
            ids[2] = Task.getNextId();
            ids[3] = ProductLine.getNextId();
            try {
                createFile(IDS_FILE);
                writeData(IDS_FILE, JsonParser.toJson(ids), false);
            } catch (IllegalAccessException e) {
                log(e);
            }
        }
    }

    /**
     * Logs an {@code Exception} along with its message in
     * ./files/Exceptions.txt
     *
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
     * Logs a {@code Task task} to ./files/TasksLog.txt
     * 
     * @param task the task to log
     * @param status the status of the task (add-cancel-deliver)
     */
    public static void log(Task task, String status) {
        synchronized (FILE_LOCK) {
            try {
                createFile(TASKSLOG_FILE);
                String fileStatus = ((status.toLowerCase().equals("add")) ? "Added" : (status.toLowerCase().equals("cancel")) ? "Canceled" : "Delivered");
                StringBuilder sb = new StringBuilder();

                sb.append("Task: ")
                  .append(task.getName())
                  .append(", Customer: ")
                  .append(task.getCustomerName())
                  .append(", ")
                  .append(fileStatus)
                  .append(" on ")
                  .append(LocalDate.now().toString())
                  .append("\n");
                
                writeData(TASKSLOG_FILE, sb.toString(), true);
            } catch (IOException e) {
                log(e);
            }
        }
    }

    /**
     * Creates the specified file along with its necessary directories.
     *
     * @param file the file to create
     * @throws IOException
     */
    private static void createFile(File file) throws IOException {
        if (file == null) {
            return;
        }

        file.getParentFile().mkdirs();

        if (!file.isDirectory() && !file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * Writes the sent data to the specified file.
     * <p>
     * NOTE: this method does not checks for the existance of the file you must
     * do it manually.
     * </p>
     *
     * @param file the file to write the data on
     * @param data the data to write
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
     * NOTE: this method does not checks for the existance of the file you must
     * do it manually.
     * </p>
     *
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
