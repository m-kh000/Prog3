package core;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;

import exceptions.InvalidValuesException;
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
    private HashSet<LocalDate> orderedIn;
    private int purchaseFrequency;
    @JsonIgnore
    private boolean isInit = false;

    static {
        nextId = IDInitializer.getProductsGlobalID();
    }

    public Product() {}
    
    public Product(String name) {
        this.id = nextId++;
        this.name = name;
        this.requiredItemsNames = new HashMap<>();
        this.requiredItems = new HashMap<>();
        this.orderedIn = new HashSet<>();
    }
    public Product(String name, HashMap<String, Integer> requiredItemsNames,HashSet<LocalDate> orderedIn) throws NoSuchElementException {
        this.id = nextId++;
        this.name = name;
        this.orderedIn = orderedIn;
        this.requiredItemsNames = new HashMap<>(requiredItemsNames);
        initializeRequiredItems();
    }

    public void initializeRequiredItems() {
        if (isInit) {
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
        if(quantity <= 0 || requiredItems.containsKey(i)) {
            throw new InvalidValuesException("Invalid values!");
        }
        requiredItems.put(i, quantity);
    }

    public void order(LocalDate date) {
        orderedIn.add(date);
    }

    public boolean wasOrderedBetween(LocalDate start, LocalDate end) { 
        for(LocalDate l : getOrderedIn()){
            if(Dates.isBetween(l, start, end)){
                return true;
            }
        }
        return false;
    }

    void increasePurchases(){
        purchaseFrequency++;
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
        return this.requiredItems;
    }

    private Item getItem(String name) {
        for (Item i : requiredItems.keySet()) {
            if (name.equals(i.getName())) {
                return i;
            }
        }
        return null;
    }

    public int getRequiredQuantityOf(String name) {
        Integer temp = requiredItems.get(getItem(name));
        return (temp == null) ? 0 : temp;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public int getPurchaseFrequency() {
        return purchaseFrequency;
    }

    // SETTERS : 
    
    public void setName(String name) {
        this.name = name;
    }

    public void make() {
        this.quantityAvailable++;
    }

    public int reqItemCount() {
        return requiredItems.size();
    }

    public int getPurchaseFrequencyBetween(LocalDate begDate, LocalDate enDate) {
        int freq = 0;
        for(LocalDate ordered : orderedIn) {
            if(Dates.isBetween(ordered, begDate, enDate)) {
                freq++;
            }
        }
        return freq;
    }
}