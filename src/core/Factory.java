package core;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Factory {
    private HashSet<ProductLine> allLines;
    private List<User> users;
    private String notes;
    private Warehouse warehouse;

    public Factory() {
        this.allLines = new HashSet<>();
        this.users = new ArrayList<>();
        this.notes = new String();
        this.warehouse = new Warehouse();
    }

    public Factory(HashSet<ProductLine> allLines,List<User> users,String notes,Warehouse warehouse) {
        this.allLines = allLines;
        this.users = users;
        this.notes = notes;
        this.warehouse = warehouse;
    }

    synchronized public void add(Product p) {
        warehouse.addProduct(p);
    }

    synchronized public void add(Item i) {
        i.setName(i.getName().trim());
        warehouse.addItem(i);
    }
    
    public void addUser(User u){
        users.add(u);
    }

    synchronized public void add(ProductLine pl) {
        allLines.add(pl);
    }
    
    // PREVIEWS : 

    synchronized public ProductLine[] previewLines() {
        return allLines.toArray(new ProductLine[allLines.size()]);
    }

    synchronized public String previewNotes() {
        return notes;
    }

    synchronized public Item[] previewItems() {
        return warehouse.getItems().toArray(new Item[warehouse.getItems().size()]);
    }

    synchronized public Product[] previewProducts() {
        return warehouse.getProducts().toArray(new Product[warehouse.getProducts().size()]);
    }

    /* Removed previewProducts and previewTasks methods */

    // GETTERS : 

    public HashSet<ProductLine> getAllLines() {
        return allLines;
    }

    public List<User> getUsersList() {
        return this.users;
    }

    public String getNotes() {
        return notes;
    }
    
    public List<User> getUsers() {
        return users;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    // SETTERS :

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void resetItem(Item i,String name, String category, double price, int quantityAvailable, int minQuantity) {
        if(!warehouse.getItems().contains(i)) return;
        i.setName(name);
        i.setCategory(category);
        i.setPrice(price);
        i.setQuantityAvailable(quantityAvailable);
        i.setMinQuantity(minQuantity);
    }

    synchronized public void deleteItem(String name) {
        warehouse.removeItem(name);
    }

    public List<Item> filterItemsByName(String filter) {
        filter = filter.trim();
        filter = filter.toLowerCase();
        List<Item> filteredList = new ArrayList<>();
        for(Item i:warehouse.getItems()){
            if(i.getName().toLowerCase().contains(filter)){
                filteredList.add(i);
            }
        }
        return filteredList;
    }
    
    public List<Item> filterItemsByCategory(String filter) {
        filter = filter.trim();
        filter = filter.toLowerCase();
        List<Item> filteredList = new ArrayList<>();
        for(Item i:warehouse.getItems()){
            if(i.getName().toLowerCase().contains(filter)){
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public List<Item> filterItemsByAvailable() {
        List<Item> filteredList = new ArrayList<>();
        for(Item i:warehouse.getItems()){
            if(i.isAvailable()){
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public List<Item> filterItemsByOut() {
        List<Item> filteredList = new ArrayList<>();
        for(Item i:warehouse.getItems()){
            if(i.isOut()){
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public List<Item> filterItemsByUnderMin() {
        List<Item> filteredList = new ArrayList<>();
        for(Item i:warehouse.getItems()){
            if(i.isUnderMin()){
                filteredList.add(i);
            }
        }
        return filteredList;
    }
    
    public List<Task> filterTasksByInprogress() {
        List<Task> filteredList = new ArrayList<>();
        for(ProductLine pl : allLines){
            filteredList.addAll(pl.getInprogress());
        }
        return filteredList;
    }

    public List<Task> filterTasksByCompleted() {
        List<Task> filteredList = new ArrayList<>();
        for(ProductLine pl : allLines){
            filteredList.addAll(pl.getCompleted());
        }
        return filteredList;
    }

    public List<Product> topOrderBetween(LocalDate start, LocalDate end) {
        List<Product> list = new ArrayList<>();
        for(Product p:warehouse.getProducts()){
            if(p.wasOrderedBetween(start, end)){
                list.add(p);
            }
        }
        return list;
    }

    public String[] getItemsNames() {
        List<String> names = new ArrayList<>();
        for(Item i : warehouse.getItems()){
            names.add(i.getName());
        }
        return names.toArray(new String[names.size()]);
    }
}