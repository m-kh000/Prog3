package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import core.*;
import jsonParser.JsonParser;
import ui.UI;
import utils.FileUtils;

public class App {
    public static void main(String[] args) throws Exception {
        // Factory factory = new Factory();
        
        // factory.add(new Item("Screws", "Hardware", 0.5, 100, 20));
        // factory.add(new Item("Bolts", "Hardware", 0.75, 50, 30));
        // factory.add(new Item("Wood", "Material", 10.0, 15, 25));
        
        // Product chair = new Product("Chair");
        // Product table = new Product("Table");
        
        // factory.add(chair);
        // factory.add(table);
        
        // new UI(factory);
        
         Factory f = new Factory();
          HashMap<Item, Integer> hm = new HashMap<>();
          hm.put(new Item("flour", "test", 500, 1000, 100), 10);
          HashSet<LocalDate> hs = new HashSet<>();
          LocalDate l = LocalDate.of(2006, 4, 11);
          hs.add(l);
        // f.add(new Product("bread", hm, hs));

        // FileUtils.saveProducts(f);
        
        // List<Product> p = FileUtils.readProducts();
        // System.out.println(p.get(0).getRequiredItems());

        // Warehouse w = new Warehouse();

        // w.addItem(new Item("Screws", "Hardware", 0.5, 100, 20));
        // w.addItem(new Item("Bolts", "Hardware", 0.75, 50, 30));
         Product c = new Product("Chair", hm, hs);
        // w.addProduct(c);
        // w.addProduct(table);

        // String productsJson = JsonParser.toJson(w.getProducts());
        // System.out.println(productsJson);
        // String itemsJson = JsonParser.toJson(w.getItems());
        // System.out.println(itemsJson);

        // List<Item> ll = new ArrayList<>(Arrays.asList(JsonParser.fromJson(itemsJson, Item[].class, null)));
        // List<Product> pp = new ArrayList<>(Arrays.asList(JsonParser.fromJson(productsJson, Product[].class, Item.class, Integer.class, null)));
        
        // Warehouse ww = new Warehouse(ll, pp);

        // System.out.println(ww.getItem("Bolts").getMinQuantity());
        // System.out.println(ww.getProduct("Chair").getRequiredQuantityOf("flour"));
        
        //  f.add(new ProductLine("test", "working"));
        //  f.getProductLine("test").addTask(new Task(c, 10, "Joseph", LocalDate.now(), LocalDate.of(2029, 5, 1), "todo", 10));
    }
}
