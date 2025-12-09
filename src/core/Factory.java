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
    private StringBuffer notes;

    public Factory() {
        this.allItems = new HashSet<>();
        this.allProducts = new HashSet<>();
        this.allLines = new HashSet<>();
        this.users = new ArrayList<>();
        this.notes = new StringBuffer();
    }
    public Factory(HashSet <Item> allItems,HashSet<Product> allProducts,HashSet<ProductLine> allLines,List<User> users,StringBuffer notes) {
        this.allItems = allItems;
        this.allProducts = allProducts;
        this.allLines = allLines;
        this.users = users;
        this.notes = notes;
    }

    synchronized public void add(Product p) {
        allProducts.add(p);
    }

    synchronized public void add(Item i) {
        allItems.add(i);
    }

    public void add(Task t) { 
        // joseph this is yours to deal with, take it to your class
    }

    synchronized public void add(ProductLine pl) {
        allLines.add(pl);
    }

    public void changeStatusOfLine(String newStatus) { 
        // this is also yours
    }
    
    // PREVIEWS : 

    synchronized public ProductLine[] previewLines() {
        return allLines.toArray(new ProductLine[allLines.size()]);
    }

    synchronized public StringBuffer previewNotes() {
        return notes;
    }

    synchronized public Item[] previewItems() {
        return allItems.toArray(new Item[allItems.size()]);
    }

    synchronized public Product[] previewProducts() {
        return allProducts.toArray(new Product[allProducts.size()]);
    }

    synchronized public void previewProducts(ProductLine pl) {
        pl.previewProducts();
    }

    synchronized public void previewTasks(ProductLine pl) {
        pl.previewTasks();
    }

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

    public StringBuffer getNotes() {
        return notes;
    }
    //TODO: addUser(User u)
    // SETTERS :

    public void setNotes(StringBuffer notes) {
        this.notes = notes;
    }

    // TODO : discuss resetting items
        public void resetItem() {
        }

    synchronized public void deleteItem(Item i) {
        allItems.remove(i);
    }

    public void cancelTask(Task t) { 
        // this is also yours
    }
    // TODO : discuss filtering
        public void filterItemsByName() {
        }

        public void filterItemsByCategory() {
        }

        public void filterItemsByAvailable() {
        }

        public void filterItemsByOut() {
        }

        public void filterItemsByUnderMin() {
        }

        public void filterTasksByInprogress() {
        }

        public void filterTasksByCompleted() {
        }

        public void filterLinesByProduct(Product p) {
        }
    // TODO : discuss the method of I/O intended
        public void exportToFile() {
        }
    // TODO : demand clarification on the purpose of this method
        public void topOrderBetween(Date date1, Date date2) {
        }
}