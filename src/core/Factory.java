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

    public void add(Product p) {
    }

    public void add(Item i) {
    }

    public void add(Task t) {
    }

    public void add(ProductLine pl) {
    }

    public void changeStatusOfLine(String newStatus) {
    }

    public void previewLines() {
    }

    public void previewNotes() {
    }

    public void previewItems() {
    }

    public void previewProducts() {
    }

    public void previewProducts(ProductLine pl) {
    }

    public void previewTasks(ProductLine pl) {
    }

    public void previewTasks(Product p) {
    }

    public void resetItem() {
    }

    public void deleteItem(Item i) {
    }

    public void cancelTask(Task t) {
    }

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

    public void exportToFile() {
    }

    public void topOrderBetween(Date date1, Date date2) {
    }
}