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

    public void add(int quantity) {}

    public void order(LocalDate date) {}

    public boolean wasOrderedBetween(LocalDate start, LocalDate end) { return false; }

    public void previewTasks() {}
}