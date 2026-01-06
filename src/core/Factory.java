package core;

import core.User.UserInfo;
import exceptions.InvalidValuesException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import utils.FileUtils;

public class Factory {

    private static HashSet<ProductLine> allLines;
    private static Warehouse warehouse;

    public Factory() {
        Factory.allLines = new HashSet<>();
        Factory.warehouse = new Warehouse();
    }

    public Factory(HashSet<ProductLine> allLines, Warehouse warehouse) {
        Factory.allLines = new HashSet<>(allLines);
        Factory.warehouse = warehouse;
    }
//TODO make sure names are unique here or in warehouse and throw exception
    public static synchronized void add(Product p) {
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
            tasks.addAll(pl.getCanceledTasks());
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

    public static synchronized void deleteItem(String name) {
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
            if(t.getProduct().getName().trim().toLowerCase().contains(filterValue)) {
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

    public static void cancelTask(String taskToBeCanceled) {
        for (ProductLine pl : allLines) {
            for (Task task : pl.get0PCTasks()) {
                if (task.getName().equals(taskToBeCanceled)) {
                    pl.cancelTask(task);
                }
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
     * @param t the task to deliver
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

    public static void employAndAssignProductLines() {
        for (ProductLine pl : allLines) {
            utils.ThreadManager.employ(pl);
        }
        utils.ThreadManager.assign();
    }

    public static void modifyStatus(String selectedLineName, String selectedStatus) {
        selectedStatus = selectedStatus.trim().toLowerCase();
        for (ProductLine pl : allLines) {
            if (pl.getName().trim().toLowerCase().equals(selectedLineName)) {
                pl.setStatus(selectedStatus);
            }
        }
    }

    public static String[] getCompletedTasksNames() {
        List<String> names = new ArrayList<>();
        for (ProductLine pl : allLines) {
            for (Task t : pl.getCompleted()) {
                names.add(t.getName());
            }
        }
        return names.toArray(new String[names.size()]);
    }

    public static void deliverTask(String selectedItem) {
        selectedItem = selectedItem.trim().toLowerCase();
        for (ProductLine pl : allLines) {
            for (Task t : pl.getCompleted()) {
                if (t.getName().trim().toLowerCase().equals(selectedItem)) {
                    pl.getCompleted().remove(t);
                }
            }
        }
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

        throw new NoSuchElementException();
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
}
