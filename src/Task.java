import java.time.LocalDate;

public class Task {
    private static int nextId = 1;
    private int id;
    private Product product;
    private int requiredQuantity;
    private int ready;
    private String customerName;
    private LocalDate startDate;
    private LocalDate deliveryDate;
    private String status;
    private ProductLine assignedLine;
    private double completionPercentage;
    private Factory factory;

    public Task(Product product, int requiredQuantity, String customerName, LocalDate startDate, LocalDate deliveryDate, String status, ProductLine assignedLine, double completionPercentage, Factory factory) {
        this.id = nextId++;
        this.product = product;
        this.requiredQuantity = requiredQuantity;
        this.ready = 0;
        this.customerName = customerName;
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.assignedLine = assignedLine;
        this.completionPercentage = completionPercentage;
        this.factory = factory;
    }

    // public boolean doTask() {
    // }

    // public boolean isCompleted() {
    // }

    // public double percentage() {
    // }

    // public void cancelTask() {
    // }
}