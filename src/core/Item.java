package core;
public class Item {
    private static int nextId = 1;
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantityAvailable;
    private int minQuantity;

    public Item(String name, String category, double price, int quantityAvailable, int minQuantity) {
        this.id = nextId++;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.minQuantity = minQuantity;
    }

    public void take(int quantity) {}

    public boolean canTake(int quantity) { return false; }

    public boolean add(int quantity) { return false; }

    public boolean preview() { return false; }

    public boolean resetCategoryPriceMinQuantity() { return false; }

    public boolean delete() { return false; }

    public boolean isName(String name) { return false; }

    public boolean isCategory(String category) { return false; }

    public boolean isAvailable() { return false; }

    public boolean isOut() { return false; }

    public boolean isUnderMin() { return false; }
}