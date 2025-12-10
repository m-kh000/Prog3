package core;

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

    public Task() {}
    
    public Task(Product product, int requiredQuantity, String customerName, LocalDate startDate, LocalDate deliveryDate, String status, ProductLine assignedLine, double completionPercentage) {
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
    }

    // public boolean doTask() {
    // }

    // public boolean isCompleted() {
    // }

    // public double percentage() {
    // }

    // public void cancelTask() {
    // }

    // GETTERS : 

    public ProductLine getAssignedLine() {
        return assignedLine;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }
    
    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public int getId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }
    
    public Product getProduct() {
        return product;
    }

    public int getReady() {
        return ready;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getStatus() {
        return status;
    }

    // SETTERS : 

    public void setAssignedLine(ProductLine assignedLine) {
        this.assignedLine = assignedLine;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setNextId(int nextId) {
        Task.nextId = nextId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}