package core;

import exceptions.StorageInitializationException;
import jsonParser.annotations.JsonIgnore;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import utils.FileUtils;

public class Warehouse {
    private List<Item> items;
    private List<Product> products;
    @JsonIgnore
    private boolean isInit = false;

    public Warehouse() {
        try {
            items = FileUtils.readItems();
            products = FileUtils.readProducts();
        } catch (IOException e) {
            throw new StorageInitializationException("Failed to initialize Warehouse.");
        }
    }
    public Warehouse(List<Item> items, List<Product> products) {
        this.items = new ArrayList<>(items);
        this.products = new ArrayList<>(products);
    }
    
    public synchronized void initializeProducts() {
        if (this.isInit) {
            return;
        }

        for (Product p : products) {
            try {
                p.initializeRequiredItems();
            } catch (NoSuchElementException e) {
                FileUtils.log(e);
            }
        }
        
        this.isInit = true;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * Removes a specific item from the items list.
     * 
     * @param itemName the name of the item to remove
     */
    public void removeItem(String itemName) {
        items.removeIf(i -> i.getName().equals(itemName));
    }
    /**
     * Removes a specific product from the products list.
     * 
     * @param productName the name of the product to remove
     */
    public void removeProduct(String productName) {
        products.removeIf(p -> p.getName().equals(productName));
    }

    /**
     * @param itemName the name of the item
     * @return the wanted item if found or else null
     */
    public Item getItem(String itemName) {
        for (Item i : items) {
            if (i.getName().equals(itemName)) {
                return i;
            }
        }
        
        return null;
    }
    public List<Item> getItems() {
        return this.items;
    }
    public List<Product> getProducts() {
        return this.products;
    }
    
    /**
     * @param productName the name of the product
     * @return the wanted product if found or else null
     */
    public Product getProduct(String productName) {
        for (Product p : products) {
            if (p.getName().equals(productName)) {
                return p;
            }
        }
        
        return null;
    }

    /**
     * @param itemName the name of the item
     * @return {@code true} if the items list contains an {@code Item} with the given name
     *      and the available quantity is greater than zero or false otherwise
     */
    public boolean isItemAvailable(String itemName) {
        Item temp = this.getItem(itemName);

        return ((temp == null) ? false : (temp.getQuantityAvailable() > 0) ? true : false);
    }

    public void makeProduct(Product p) {
        getProduct(p.getName()).make();
        for (Map.Entry<Item, Integer> e : p.getRequiredItems().entrySet()) {
            Item i = getItem(e.getKey().getName());
            if (i == null) {
                continue;
            }

            i.take(e.getValue());
        }
    }
    public String[] getProductsNames() {
        List<String> names = new ArrayList<>();
        for(Product p : products){
            names.add(p.getName());
        }
        return names.toArray(new String[names.size()]);
    }
    public List<Product> getTopSaleProduct(LocalDate begDate,LocalDate enDate) {
        List<Product> filteredList = new ArrayList<>();
        List<Product> freq = new ArrayList<>();
        for(Product p : products){
            if(p.getPurchaseFrequencyBetween(begDate, enDate) > 0)
                freq.add(p);
        }
        Collections.sort(freq, (p1, p2) -> {return p2.getPurchaseFrequencyBetween(begDate,enDate) - p1.getPurchaseFrequencyBetween(begDate,enDate);});
        for(Product p : freq){
            filteredList.add(p);
            if(filteredList.size() == 10) break;
        }
        return filteredList;
    }
    public List<Product> filterProductsByProductLine(String filter) {
        HashSet<Product> filteredSet = new HashSet<>();
        filter = filter.trim().toLowerCase();
        for(ProductLine pl : Factory.getAllLines()) {
            if(pl.getName().trim().toLowerCase().equals(filter)) {
                for(Task t : pl.getCompletedTasks()) {
                    filteredSet.add(t.getProduct());
                }
                for(Task t : pl.getInprogress()) {
                    filteredSet.add(t.getProduct());
                }
                for(Task t : pl.getInline()) {
                    filteredSet.add(t.getProduct());
                }
                break;
            }
        }
        List<Product> filteredList = new ArrayList<>();
        filteredList.addAll(filteredSet);
        return filteredList;        
    }
}
