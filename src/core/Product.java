package core;

import exceptions.InvalidValuesException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import jsonParser.annotations.JsonIgnore;
import utils.Dates;
import utils.FileUtils;
import utils.IDInitializer;

public class Product {
    private static int nextId = 1;
    private int id;
    private String name;
    private int quantityAvailable;
    private HashMap<String, Integer> requiredItemsNames;
    @JsonIgnore
    private HashMap<Item, Integer> requiredItems;
    private ArrayList<LocalDate> orderRegistry;
    private int purchaseFrequency;
    @JsonIgnore
    private boolean isInit = false;

    static {
        nextId = IDInitializer.getProductsGlobalID();
    }

    public Product() {}
    
    public Product(String name, HashMap<String, Integer> requiredItemsNames,ArrayList<LocalDate> orderRegistry) throws NoSuchElementException {
        this.id = nextId++;
        this.name = name;
        this.orderRegistry = orderRegistry;
        this.requiredItemsNames = new HashMap<>(requiredItemsNames);
        initializeRequiredItems();
    }

    public void initializeRequiredItems() {
        if (this.isInit) {
            return;
        }

        HashMap<Item, Integer> hm = new HashMap<>();
        for (Map.Entry<String, Integer> entry : requiredItemsNames.entrySet()) {
            try {
                hm.put(Factory.findItemByName(entry.getKey()), entry.getValue());
            } catch (NoSuchElementException e) {
                FileUtils.log(e);
            }
        }
        this.requiredItems = hm;
        
        this.isInit = true;
    }

    public void addItem(Item i, int quantity) throws InvalidValuesException {
        if(quantity == 0) {
            throw new InvalidValuesException("Item quantity can not be zero.");
        }
        if(quantity < 0) {
            throw new InvalidValuesException("Item can not be negative.");
        }
        if(requiredItems.containsKey(i)) {
            throw new InvalidValuesException("Item duplicated.");
        }
        requiredItems.put(i, quantity);
    }

    public void order(LocalDate date) {
        orderRegistry.add(date);
    }

    void increasePurchases(){
        purchaseFrequency++;
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

    public HashMap<Item, Integer> getRequiredItems() {
        return this.requiredItems;
    }

    public int getPurchaseFrequency() {
        return purchaseFrequency;
    }

    public void make() {
        this.quantityAvailable++;
    }

    public int reqItemCount() {
        return requiredItems.size();
    }

    public int getPurchaseFrequencyBetween(LocalDate begDate, LocalDate enDate) {
        int freq = 0;
        
        for(LocalDate ordered : orderRegistry) {
            if(Dates.isBetween(ordered, begDate, enDate)) {
                freq++;
            }
        }
        
        return freq;
    }
}