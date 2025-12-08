package core;
public class Item {
    private static int nextId = 1;
    private int id;
    private StringBuffer name;
    private StringBuffer category;
    private double price;
    private int quantityAvailable;
    private int minQuantity;

    public Item(StringBuffer name, StringBuffer category, double price, int quantityAvailable, int minQuantity) {
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
}