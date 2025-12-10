package core;

import java.io.IOException;
import java.util.List;

import exceptions.StorageInitializationException;
import utils.FileUtils;

public class Warehouse {
    private List<Item> items;

    public Warehouse() {
        try {
            items = FileUtils.readItems();
        } catch (IOException e) {
            // TODO: log the exception in Exceptions.txt
            throw new StorageInitializationException("Failed to initialize Warehouse.");
        }
    }

    public boolean isItemAvailable(String itemName) {
        return false;
    }
}
