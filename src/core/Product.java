package core;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Product {
    private static int nextId = 1;
    private int id;
    private String name;
    private Map<Item, Integer> requiredItems;
    private HashSet<LocalDate> orderedIn;

    public Product() {}
    
    public Product(String name) {
        this.id = nextId++;
        this.name = name;
        this.requiredItems = new HashMap<>();
        this.orderedIn = new HashSet<>();
    }
    public Product(String name,Map<Item, Integer> requiredItems,HashSet<LocalDate> orderedIn){
        this.id = nextId++;
        this.name = name;
        this.orderedIn = orderedIn;
        this.requiredItems = requiredItems;
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
        return new HashMap<>(requiredItems);
    }

    // SETTERS : 
    
    public void setName(String name) {
        this.name = name;
    }
}