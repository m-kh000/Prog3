package core;

import exceptions.InvalidValuesException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import jsonParser.annotations.JsonIgnore;
import utils.FileUtils;
import utils.IDInitializer;

public class Task {
    private static int nextId = 1;
    private int id;
    private String productName;
    @JsonIgnore
    private Product product;
    private int requiredQuantity;
    private int ready;
    private String customerName;
    private LocalDate startDate;
    private LocalDate deliveryDate;
    private String status;
    @JsonIgnore
    private boolean isInit = false;

    static {
        nextId = IDInitializer.getTasksGlobalID();
    }

    public Task() {}
    
    public Task(String productName, int requiredQuantity, String customerName, LocalDate startDate, LocalDate deliveryDate, String status) throws InvalidValuesException {
        if(requiredQuantity <= 0 || startDate.isAfter(deliveryDate)) {
            throw new InvalidValuesException("Invalid values in class Task!");
        }
        this.id = nextId++;
        this.productName = productName;
        this.requiredQuantity = requiredQuantity;
        this.ready = 0;
        this.customerName = customerName;
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        initializeTask();
        this.product.increasePurchases();
        this.product.order(startDate);
    }

    public synchronized void initializeTask() {
        if (this.isInit) {
            return;
        }

        try {
            this.product = Factory.findProductByName(productName);
        } catch (NoSuchElementException e) {
            FileUtils.log(e);
        }

        this.isInit = true;
    }

    // GETTERS : 

    public double getCompletionPercentage() {
        return ((double) ready / requiredQuantity);
    }
    
    public String getCustomerName() {
        return customerName;
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
    
    public String getProductName() {
        return productName;
    }

    public int getReady() {
        return ready;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public String getName() {
        return String.format("#%04d", id ) + " " + requiredQuantity + " " + productName + "(s) due to " + deliveryDate;
    }

    public void increaseReady() {
        this.ready++;
    }
}