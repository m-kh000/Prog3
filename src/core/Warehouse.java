package core;

import exceptions.ItemInUseException;
import exceptions.StorageInitializationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import jsonParser.annotations.JsonIgnore;
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
    public void removeItem(String itemName) throws ItemInUseException {
        if(Factory.itemInUse(itemName)) {
            throw new ItemInUseException();
        }
        Factory.cancelTasksByItemName(itemName);
        removeProductsByItemName(itemName);
        Item toRemove = new Item();
        for(Item i : items) {
            if(i.getName().equals(itemName)) {
                toRemove = i;
                break;
            }
        }
        items.remove(toRemove);
    }

    private void removeProductsByItemName(String itemName) {
        List <Product> toRemove = new ArrayList<>();
        for(Product p : products) {
            for(Item i : p.getRequiredItems().keySet()) {
                if(i.getName().equals(itemName)) {
                    toRemove.add(p);
                    break;
                }
            }
        }
        products.removeAll(toRemove);
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

    public List<Item> getItems() {
        return this.items;
    }

    public List<Product> getProducts() {
        return this.products;
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
                for(Task t : pl.getCompleted()) {
                    if(t.getProduct() == null) continue;
                    filteredSet.add(t.getProduct());
                }
                for(Task t : pl.getInprogress()) {
                    if(t.getProduct() == null) continue;
                    filteredSet.add(t.getProduct());
                }
                for(Task t : pl.getInline()) {
                    if(t.getProduct() == null) continue;
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
