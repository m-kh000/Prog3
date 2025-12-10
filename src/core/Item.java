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

    public void take(int quantity) {
        if(!canTake(quantity)){
            return;
        }
        quantityAvailable -= quantity;
    }

    public boolean canTake(int quantity) {
        return quantity <= quantityAvailable;
    }

    public void add(int quantity) {
        quantityAvailable += quantity;
    }
    // TODO : discuss this methods purpose
        public void preview() {
            
        }

    public void resetCategoryPriceMinQuantity(int newQuantity) { 
        minQuantity = newQuantity;
    }

    public boolean isAvailable() { 
        return quantityAvailable != 0;
    }

    public boolean isOut() { 
        return quantityAvailable == 0;
    }

    public boolean isUnderMin() { 
        return quantityAvailable < minQuantity;
    }

    // GETTERS : 

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public static int getNextId() {
        return nextId;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public String getFileFormat() {
        return this.getId() + ", "
                + this.getName() + ", "
                + this.getCategory() + ", "
                + this.getPrice() + ", "
                + this.getQuantityAvailable() + ", "
                + this.getMinQuantity();
    }

    // SETTERS : 

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}