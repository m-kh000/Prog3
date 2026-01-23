package core;

import core.User.UserInfo;
import exceptions.InvalidValuesException;
import exceptions.ItemInUseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import utils.FileUtils;

public class Factory {

    private static HashSet<ProductLine> allLines;
    private static Warehouse warehouse;
    private static boolean isInit = false;
    
    public static synchronized void initializeAll() {
        if (isInit) {
            return;
        }

        warehouse = new Warehouse();
        allLines = FileUtils.readProductLines();

        warehouse.initializeProducts();
        Factory.initializeProductLines();

        isInit = true;
    }
    private static synchronized void initializeProductLines() {
        for (ProductLine pl : allLines) {
            pl.initializeTasks();
        }
    }
    
    public static synchronized void add(Product p) throws InvalidValuesException {
        for(Product pr : warehouse.getProducts()) {
            if(p.getName().trim().toLowerCase().equals(pr.getName().trim().toLowerCase())) {
                throw new InvalidValuesException();
            }
        }
        warehouse.addProduct(p);
    }

    public static synchronized void add(Item i) {
        i.setName(i.getName().trim());
        warehouse.addItem(i);
    }

    public static synchronized void add(ProductLine pl) {
        allLines.add(pl);
    }

    public static void add(Task task, String plName) {
        plName = plName.trim().toLowerCase();
        for (ProductLine pl : allLines) {
            if (pl.getName().trim().toLowerCase().equals(plName)) {
                pl.addTask(task);
                break;
            }
        }
        FileUtils.log(task, "add");
    }

    // PREVIEWS : 
    public static synchronized ProductLine[] previewLines() {
        return allLines.toArray(new ProductLine[allLines.size()]);
    }

    public static synchronized Item[] previewItems() {
        return warehouse.getItems().toArray(new Item[warehouse.getItems().size()]);
    }

    public static synchronized Product[] previewProducts() {
        return warehouse.getProducts().toArray(new Product[warehouse.getProducts().size()]);
    }

    public static synchronized Task[] previewTasks() {
        List<Task> tasks = new ArrayList<>();
        for (ProductLine pl : allLines) {
            tasks.addAll(pl.getInline());
            tasks.addAll(pl.getCompleted());
            tasks.addAll(pl.getInprogress());
        }
        return tasks.toArray(new Task[tasks.size()]);
    }

    // GETTERS : 
    public static HashSet<ProductLine> getAllLines() {
        return allLines;
    }

    public static Warehouse getWarehouse() {
        return warehouse;
    }

    public static ProductLine getProductLine(String name) {
        for (ProductLine pl : allLines) {
            if (pl.getName().equals(name)) {
                return pl;
            }
        }

        return null;
    }
    public static ProductLine getProductLine(int id) {
        for (ProductLine pl : allLines) {
            if (pl.getId() == id) {
                return pl;
            }
        }

        return null;
    }

    public static void resetItem(Item i, String name, String category, double price, int quantityAvailable, int minQuantity) throws InvalidValuesException, NoSuchElementException {
        if (!warehouse.getItems().contains(i)) {
            throw new NoSuchElementException();
        }
        i.setName(name);
        i.setCategory(category);
        i.setPrice(price);
        i.setQuantityAvailable(quantityAvailable);
        i.setMinQuantity(minQuantity);
    }

    public static synchronized void deleteItem(String name) throws ItemInUseException {
        warehouse.removeItem(name);
    }

    // FILTERS :
    public static List<Item> filterItemsByName(String filter) {
        filter = filter.trim().toLowerCase();
        List<Item> filteredList = new ArrayList<>();
        for (Item i : warehouse.getItems()) {
            if (i.getName().trim().toLowerCase().contains(filter)) {
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public static List<Item> filterItemsByCategory(String filter) {
        filter = filter.trim().toLowerCase();
        List<Item> filteredList = new ArrayList<>();
        for (Item i : warehouse.getItems()) {
            if (i.getCategory().trim().toLowerCase().contains(filter)) {
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public static List<Item> filterItemsByAvailable() {
        List<Item> filteredList = new ArrayList<>();
        for (Item i : warehouse.getItems()) {
            if (i.isAvailable()) {
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public static List<Item> filterItemsByOut() {
        List<Item> filteredList = new ArrayList<>();
        for (Item i : warehouse.getItems()) {
            if (i.isOut()) {
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public static List<Item> filterItemsByUnderMin() {
        List<Item> filteredList = new ArrayList<>();
        for (Item i : warehouse.getItems()) {
            if (i.isUnderMin()) {
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public static List<Task> filterTasksByInprogress() {
        List<Task> filteredList = new ArrayList<>();
        for (ProductLine pl : allLines) {
            filteredList.addAll(pl.getInprogress());
            filteredList.addAll(pl.getInline());
        }
        return filteredList;
    }

    public static List<Task> filterTasksByCompleted() {
        List<Task> filteredList = new ArrayList<>();
        for (ProductLine pl : allLines) {
            filteredList.addAll(pl.getCompleted());
        }
        return filteredList;
    }

    public static List<ProductLine> filterProductLinesByProduct(String filterValue) {
        List<ProductLine> filteredList = new ArrayList<>();
        for (ProductLine pl : allLines) {
            if (pl.hasProduct(filterValue)) {
                filteredList.add(pl);
            }
        }
        return filteredList;
    }

    public static List<Task> filterTasksByProduct(String filterValue) {
        filterValue = filterValue.trim().toLowerCase();
        List<Task> filteredList = new ArrayList<>();
        for(Task t : previewTasks()) {
            if(t.getProductName().trim().toLowerCase().contains(filterValue)) {
                filteredList.add(t);
            }
        }
        return filteredList;
    }

    public static List<Task> filterTasksByProductLine(String filterValue) {
        filterValue = filterValue.trim().toLowerCase();
        List<Task> filteredList = new ArrayList<>();
        ProductLine pl = findPLByName(filterValue);
        filteredList.addAll(pl.getInline());
        filteredList.addAll(pl.getInprogress());
        filteredList.addAll(pl.getCompleted());
        return filteredList;
    }

    public static List<Product> topOrderBetween(LocalDate start, LocalDate end) {
        List<Product> list = new ArrayList<>();
        for (Product p : warehouse.getProducts()) {
            if (p.wasOrderedBetween(start, end)) {
                list.add(p);
            }
        }
        return list;
    }

    public static String[] getItemsNames() {
        List<String> names = new ArrayList<>();
        for (Item i : warehouse.getItems()) {
            names.add(i.getName());
        }
        return names.toArray(new String[names.size()]);
    }

    public static String[] get0PCTasksNames() {
        List<String> names = new ArrayList<>();
        for (ProductLine pl : allLines) {
            for (Task t : pl.get0PCTasks()) {
                names.add(t.getName());
            }
        }
        return names.toArray(new String[names.size()]);
    }

    public static void cancelTask(int taskId) {
        Task t = null;
        boolean flag = false;
        for (ProductLine pl : allLines) {
            for (Task task : pl.get0PCTasks()) {
                if (task.getId() == taskId) {
                    t = task;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                pl.cancelTask(t);
                FileUtils.log(t, "cancel");
                break;
            }
        }
    }

    public static void makeProduct(Product p) {
        warehouse.makeProduct(p);
    }

    public static String[] getProductNames() {
        List<String> names = new ArrayList<>();
        for (Product p : warehouse.getProducts()) {
            names.add(p.getName());
        }
        return names.toArray(new String[names.size()]);
    }

    public static String[] getProductLineNames() {
        List<String> names = new ArrayList<>();
        for (ProductLine pl : allLines) {
            names.add(pl.getName());
        }
        return names.toArray(new String[names.size()]);
    }

    /**
     * Deliver a specific completed task by removing it from the completed list
     * in its productline.
     *
     * @param pl the productline that contains the task
     * @param taskId the id of the task to be delivered
     * 
     * @throws NoSuchElementException if the task is not found
     */
    public static void deliverTask(ProductLine pl, int taskId) throws NoSuchElementException {
        pl.removeCompletedTask(taskId);
    }

    /**
     * Not implemented right now, left until we decide about the Email system.
     *
     * @return
     */
    public static UserInfo[] getUsersInfo() {
        return null;
    }

    public static void saveToTXT() throws IOException {
        FileUtils.saveItems();
        FileUtils.saveProducts();
        FileUtils.saveProductLines();
        FileUtils.saveIDs();
    }

    public static void modifyStatus(String selectedLineName, String selectedStatus) {
        ProductLine pl = findPLByName(selectedLineName);
        selectedStatus = selectedStatus.trim().toLowerCase();
        pl.setStatus(selectedStatus);
    }

    public static HashMap<String, Task_id_pl> getCompletedTasksNames_ids_pls() {
        HashMap<String, Task_id_pl> names = new HashMap<>();
        for (ProductLine pl : allLines) {
            for (Task t : pl.getCompleted()) {
                names.put(t.getName(), new Task_id_pl(t.getId(), pl));
            }
        }
        return names;
    }

    public static String[] getItemNames() {
        List<String> names = new ArrayList<>();
        for (Item i : warehouse.getItems()) {
            names.add(i.getName());
        }
        return names.toArray(new String[names.size()]);
    }

    public static Item findItemByName(String itemName) throws NoSuchElementException {
        itemName = itemName.trim().toLowerCase();
        for (Item i : warehouse.getItems()) {
            if (i.getName().trim().toLowerCase().equals(itemName)) {
                return i;
            }
        }

        throw new NoSuchElementException("Couldn\'t find the item " + itemName + " in the warehouse");
    }

    public static Product findProductByName(String productName) throws NoSuchElementException {
        productName = productName.trim().toLowerCase();
        for (Product p : warehouse.getProducts()) {
            if (p.getName().trim().toLowerCase().equals(productName)) {
                return p;
            }
        }

        throw new NoSuchElementException();
    }

    private static ProductLine findPLByName(String filterValue) {
        filterValue = filterValue.trim().toLowerCase();
        for(ProductLine pl : allLines) {
            if(pl.getName().trim().toLowerCase().equals(filterValue)) {
                return pl;
            }
        }
        throw new NoSuchElementException();
    }

    public static HashMap<String, Task_id_pl> get0PCTasksNames_ids() {
        HashMap<String, Task_id_pl> names = new HashMap<>();
        for (ProductLine pl : allLines) {
            for (Task t : pl.get0PCTasks()) {
                names.put(t.getName(), new Task_id_pl(t.getId(), pl));
            }
        }
        return names;
    }
    public static class Task_id_pl{
        public int taskId;
        public ProductLine pl;

        public Task_id_pl(int taskId, ProductLine pl) {
            this.taskId = taskId;
            this.pl = pl;
        }
    }

    
    public static void cancelTasksByItemName(String itemName) {
        for(ProductLine pl : allLines) {
            pl.removeByItemName(itemName);
        }
    }

    public static boolean itemInUse(String itemName) {
        boolean inUse = false;
        for(ProductLine pl : allLines) {
            if(pl.itemInUse(itemName)) {
                inUse = true;
                break;
            }
        }
        return inUse;
    }
}