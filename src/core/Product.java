package core;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

public class Product {
    private static int nextId = 1;
    private int id;
    private String name;
    private HashMap<Item, Integer> requiredItems;
    private HashSet<LocalDate> orderedIn;
    private HashSet<Task> tasks;

    public Product(String name) {
        this.id = nextId++;
        this.name = name;
        this.requiredItems = new HashMap<>();
        this.orderedIn = new HashSet<>();
        this.tasks = new HashSet<>();
    }
    public Product(String name,HashMap<Item, Integer> requiredItems,HashSet<LocalDate> orderedIn,HashSet<Task> tasks){
        this.id = nextId++;
        this.name = name;
        this.orderedIn = orderedIn;
        this.requiredItems = requiredItems;
        this.tasks = tasks;
    }

    public void addItem(Item i, int quantity) {
        requiredItems.put(i, quantity);
    }

    public void order(LocalDate date) {}

    // TODO : discuss the purpose of this method
        public boolean wasOrderedBetween(LocalDate start, LocalDate end) { 
            return false;
        }

    // PREVIEWS : 
    public Object[] previewTasks() {
        return tasks.toArray();
    }
    // GETTERS : 
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public static int getNextId() {
        return nextId;
    }

    public HashSet<LocalDate> getOrderedIn() {
        return orderedIn;
    }

    public HashMap<Item, Integer> getRequiredItems() {
        return requiredItems;
    }

    public HashSet<Task> getTasks() {
        return tasks;
    }

    // SETTERS : 
    
    public void setName(String name) {
        this.name = name;
    }
}