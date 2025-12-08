package core;
import java.util.ArrayList;
import java.util.HashSet;

public class ProductLine implements Runnable {
    private static int nextId = 1;
    private int id;
    private String name;
    private String status;
    private ArrayList<Task> completed;
    private ArrayList<Task> inprogress;
    private ArrayList<Task> inline;
    private ArrayList<Task> canceled;
    private HashSet<Product> products;

    public ProductLine(String name, String status) {
        this.id = nextId++;
        this.name = name;
        this.status = status;
        this.completed = new ArrayList<>();
        this.inprogress = new ArrayList<>();
        this.inline = new ArrayList<>();
        this.canceled = new ArrayList<>();
        this.products = new HashSet<>();
    }

    public void run() {
    }

    public void previewTasks() {
    }

    public void previewProducts() {
    }

    // public boolean madeProduct() {
    // }

    public void cancelTask(Task task) {
    }
}