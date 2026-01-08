package core;

import exceptions.InvalidValuesException;
import java.time.LocalDate;
import utils.IDInitializer;

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

    static {
        nextId = IDInitializer.getTasksGlobalID();
    }

    public Task() {}
    
    public Task(Product product, int requiredQuantity, String customerName, LocalDate startDate, LocalDate deliveryDate, String status) throws InvalidValuesException {
        if(requiredQuantity <= 0 || startDate.isAfter(deliveryDate)) {
            throw new InvalidValuesException("Invalid values in class Task!");
        }
        this.id = nextId++;
        this.product = product;
        this.product.increasePurchases();
        this.requiredQuantity = requiredQuantity;
        this.ready = 0;
        this.customerName = customerName;
        this.startDate = startDate;
        this.product.order(startDate);
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    // GETTERS : 

    public double getCompletionPercentage() {
        return ((double) ready / requiredQuantity);
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

    public String getName() {
        return requiredQuantity + " " + product.getName() + "(s) due to " + deliveryDate;
    }

    // SETTERS : 

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDeliveryDate(LocalDate deliveryDate) throws InvalidValuesException {
        if(startDate.isAfter(deliveryDate)) {
            throw new InvalidValuesException("Invalid values!");
        }
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

    public void setRequiredQuantity(int requiredQuantity) throws InvalidValuesException {
        if(requiredQuantity <= 0) {
            throw new InvalidValuesException("Invalid values!");
        }
        this.requiredQuantity = requiredQuantity;
    }

    public void setStartDate(LocalDate startDate) throws InvalidValuesException {
        if(startDate.isAfter(deliveryDate)) {
            throw new InvalidValuesException("Invalid values!");
        }
        this.startDate = startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void increaseReady() {
        this.ready++;
    }
}