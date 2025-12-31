package core;

import exceptions.InvalidValuesException;

public class Item {
    private static int nextId = 1;
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantityAvailable;
    private int minQuantity;

    public Item() {}
    
    public Item(String name, String category, double price, int quantityAvailable, int minQuantity) throws InvalidValuesException {
        if(price <= 0.0 || quantityAvailable < 0 || minQuantity <= 0) {
            throw new InvalidValuesException("Invalid values!");
        }
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

    public void add(int quantity) throws InvalidValuesException {
        if(quantity <= 0) {
            throw new InvalidValuesException("Invalid values!");
        }
        quantityAvailable += quantity;
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

    // SETTERS : 

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMinQuantity(int minQuantity) throws InvalidValuesException {
        if (minQuantity <= 0) {
            throw new InvalidValuesException("Invalid values!");
        }
        this.minQuantity = minQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) throws InvalidValuesException {
        if (price <= 0.0) {
            throw new InvalidValuesException("Invalid values!");
        }
        this.price = price;
    }

    public void setQuantityAvailable(int quantityAvailable) throws InvalidValuesException {
        if (quantityAvailable < 0) {
            throw new InvalidValuesException("Invalid values!");
        }
        this.quantityAvailable = quantityAvailable;
    }

    public void restock(int addQuantity) {
        if (addQuantity<=0) {
            throw new InvalidValuesException();
        }
        quantityAvailable += addQuantity;
    }
}