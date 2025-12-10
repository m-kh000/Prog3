package core;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Factory {
    private HashSet<Item> allItems;
    private HashSet<Product> allProducts;
    private HashSet<ProductLine> allLines;
    private List<User> users;
    private String notes;
    private Warehouse warehouse;

    public Factory() {
        this.allItems = new HashSet<>();
        this.allProducts = new HashSet<>();
        this.allLines = new HashSet<>();
        this.users = new ArrayList<>();
        this.notes = new String();
    }
    
    public Factory(HashSet <Item> allItems,HashSet<Product> allProducts,HashSet<ProductLine> allLines,List<User> users,String notes,Warehouse warehouse) {
        this.allItems = allItems;
        this.allProducts = allProducts;
        this.allLines = allLines;
        this.users = users;
        this.notes = notes;
        this.warehouse = warehouse;
    }

    synchronized public void add(Product p) {
        allProducts.add(p);
    }

    synchronized public void add(Item i) {
        allItems.add(i);
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
        return allItems.toArray(new Item[allItems.size()]);
    }

    synchronized public Product[] previewProducts() {
        return allProducts.toArray(new Product[allProducts.size()]);
    }

    /* Removed previewProducts and previewTasks methods */

    synchronized public void previewTasks(Product p) {
        p.previewTasks();
    }
    
    // GETTERS : 

    public HashSet<Item> getAllItems() {
        return allItems;
    }

    public HashSet<ProductLine> getAllLines() {
        return allLines;
    }

    public HashSet<Product> getAllProducts() {
        return allProducts;
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
        if(!allItems.contains(i)) return;
        i.setName(name);
        i.setCategory(category);
        i.setPrice(price);
        i.setQuantityAvailable(quantityAvailable);
        i.setMinQuantity(minQuantity);
    }

    synchronized public void deleteItem(Item i) {
        allItems.remove(i);
    }

    public List<Item> filterItemsByName(String filter) {
        filter = filter.trim();
        filter = filter.toLowerCase();
        List<Item> filteredList = new ArrayList<>();
        for(Item i:allItems){
            if(i.getName().equals(filter)){
                filteredList.add(i);
            }
        }
        return filteredList;
    }
    
    public List<Item> filterItemsByCategory(String filter) {
        filter = filter.trim();
        filter = filter.toLowerCase();
        List<Item> filteredList = new ArrayList<>();
        for(Item i:allItems){
            if(i.getName().equals(filter)){
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public List<Item> filterItemsByAvailable() {
        List<Item> filteredList = new ArrayList<>();
        for(Item i:allItems){
            if(i.isAvailable()){
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public List<Item> filterItemsByOut() {
        List<Item> filteredList = new ArrayList<>();
        for(Item i:allItems){
            if(i.isOut()){
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public List<Item> filterItemsByUnderMin() {
        List<Item> filteredList = new ArrayList<>();
        for(Item i:allItems){
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

    // public List<ProductLine> filterLinesByProduct(Product filter) {
    //     List<ProductLine> filteredList = new ArrayList<>();
    //     for(ProductLine pl : allLines){
    //         Product[] p = pl.previewProducts();
    //         boolean add = false;
    //         for(Product x : p){
    //             if(x.equals(filter)){
    //                 add = true;
    //                 break;
    //             }
    //         }
    //         if(add) filteredList.add(pl);
    //     }
    //     return filteredList;
    // }
    // TODO : discuss the method of I/O intended
        public void exportToFile() {
        }
    // TODO : demand clarification on the purpose of this method
        public void topOrderBetween(Date date1, Date date2) {
        }
}