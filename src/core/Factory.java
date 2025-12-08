package core;
import java.util.Date;
import java.util.HashSet;

public class Factory {
    private HashSet<Item> allItems;
    private HashSet<Product> allProducts;
    private HashSet<ProductLine> allLines;
    private StringBuffer notes;

    public Factory() {
        this.allItems = new HashSet<>();
        this.allProducts = new HashSet<>();
        this.allLines = new HashSet<>();
        this.notes = new StringBuffer();
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