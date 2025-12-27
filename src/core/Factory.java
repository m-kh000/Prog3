package core;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import utils.FileUtils;

public class Factory {
    private static HashSet<ProductLine> allLines;
    private static Warehouse warehouse;

    //all task methods use test case instead of actual tasks
    static Task testcase = new Task(new Product("plname"), 1,"cus",LocalDate.now(),LocalDate.now(),"status");


    public Factory() {
        Factory.allLines = new HashSet<>();
        Factory.warehouse = new Warehouse();
    }

    public Factory(HashSet<ProductLine> allLines,Warehouse warehouse) {
        Factory.allLines = new HashSet<>(allLines);
        Factory.warehouse = warehouse;
    }

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


    public static void addTask(String nameText, String quantityText, String customerText, LocalDate startDate,LocalDate deliveryDate) {
        // TODO Auto-generated method stub
        //or add(task)
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
            tasks.addAll(pl.getCompleted());
            tasks.addAll(pl.getInprogress());
            tasks.addAll(pl.getCanceled());
        }
        return new Task[]{testcase};
        //return tasks.toArray(new Task[tasks.size()]);//TODO when you do all pls and tasks
    }

    
    /* Removed previewProducts and previewTasks methods */

    // GETTERS : 

    public static HashSet<ProductLine> getAllLines() {
        return allLines;
    }
    
    public static  Warehouse getWarehouse() {
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

    // SETTERS :

    public static void resetItem(Item i, String name, String category, double price, int quantityAvailable, int minQuantity) {
        if(!warehouse.getItems().contains(i)) return;
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
        filter = filter.trim();
        filter = filter.toLowerCase();
        List<Item> filteredList = new ArrayList<>();
        for (Item i : warehouse.getItems()) {
            if (i.getName().toLowerCase().contains(filter)) {
                filteredList.add(i);
            }
        }
        return filteredList;
    }
    
    public static List<Item> filterItemsByCategory(String filter) {
        filter = filter.trim();
        filter = filter.toLowerCase();
        List<Item> filteredList = new ArrayList<>();
        for (Item i : warehouse.getItems()) {
            if (i.getName().toLowerCase().contains(filter)) {
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
            filteredList.addAll(pl.getInlineTasks());
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
            for (Task t : pl.get0PCInprogress()) {
                names.add(t.getName());
            }
            for (Task t : pl.getCanceled()) {
                names.add(t.getName());
            }
        }
        return new String[]{testcase.getName()};        
        //return names.toArray(new String[names.size()]);//TODO when you do all pls and tasks
    }

    public static void cancelTask(String selectedItem) {
        // TODO Auto-generated method stub
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

    public static List<ProductLine> filterProductLinesByProduct(String filterValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Deliver a specific completed task by removing it from the completed list in its
     * productline.
     * 
     * @param t the task to deliver
     * @return {@code true} if the task was found and removed or {@code false} otherwise
     */
    public static void deliverTask(ProductLine pl, int taskId) {
        pl.removeCompletedTask(taskId);
    }

    public static void getUsersInfo() {

    }

    public static void saveToTXT() throws IOException {
        FileUtils.saveItems();
        FileUtils.saveProducts(); 
        FileUtils.saveProductLines();
    }

    public void addTaskToProductLine(String pLtext, String productName, int int1, String customerText, String startText,
            String deliveryText) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTaskToProductLine'");
    }

    public void modifyStatus(String selectedLineName, String selectedStatus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyStatus'");
    }

    public static class UserInfo extends User{
        private String email;
        private String role;
        private String lastSeen;

        public UserInfo(String email, String role, String lastSeen) {
            this.email = super.getEmail();
            this.role = super.isManager() ? "manager" : "supervisor";
            this.lastSeen = super.getLastSeen();
        }

        public String getEmailInfo() {
            return this.email;
        }
        public String getRoleInfo() {
            return this.role;
        }
        public String getLastSeenInfo() {
            return this.lastSeen;
        }
    }
}