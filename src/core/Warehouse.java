package core;

import java.io.IOException;
import java.util.List;

import exceptions.StorageInitializationException;
import utils.FileUtils;

public class Warehouse {
    private List<Item> items;
    private List<Product> products;

    public Warehouse() {
        try {
            items = FileUtils.readItems();
            products = FileUtils.readProducts();
        } catch (IOException e) {
            // TODO: log the exception in Exceptions.txt
            throw new StorageInitializationException("Failed to initialize Warehouse.");
        }
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * Removes a specific item from the items list.
     * 
     * @param itemName the name of the item to remove
     */
    public void removeItem(String itemName) {
        items.removeIf(i -> i.getName().equals(itemName));
    }
    /**
     * Removes a specific product from the products list.
     * 
     * @param productName the name of the product to remove
     */
    public void removeProduct(String productName) {
        products.removeIf(p -> p.getName().equals(productName));
    }

    /**
     * @param itemName the name of the item
     * @return the wanted item if found or else null
     */
    public Item getItem(String itemName) {
        for (Item i : items) {
            if (i.getName().equals(itemName)) {
                return i;
            }
        }
        
        return null;
    }
    /**
     * @param productName the name of the product
     * @return the wanted product if found or else null
     */
    public Product getProduct(String productName) {
        for (Product p : products) {
            if (p.getName().equals(productName)) {
                return p;
            }
        }
        
        return null;
    }

    /**
     * @param itemName the name of the item
     * @return {@code true} if the items list contains an {@code Item} with the given name
     *      and the available quantity is greater than zero or false otherwise
     */
    public boolean isItemAvailable(String itemName) {
        Item temp = this.getItem(itemName);

        return ((temp == null) ? false : (temp.getQuantityAvailable() > 0) ? true : false);
    }
    // /**
    //  * @param productName the name of the product
    //  * @return {@code true} if the products list contains a {@code Product} with the given name
    //  *      and the available quantity is greater than zero or false otherwise
    //  */
    // public boolean isProductAvailable(String productName) {
    //     Product temp = this.getProduct(productName);

    //     return ((temp == null) ? false : (temp.getQuantityAvailable() > 0) ? true : false);
    // }
}
