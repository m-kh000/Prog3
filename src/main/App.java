package main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import core.*;
import utils.*;
import ui.UI;

public class App {
    public static void main(String[] args) throws Exception {
        Factory factory = new Factory();
        
        factory.add(new Item("Screws", "Hardware", 0.5, 100, 20));
        factory.add(new Item("Bolts", "Hardware", 0.75, 50, 30));
        factory.add(new Item("Wood", "Material", 10.0, 15, 25));
        
        Product chair = new Product("Chair");
        Product table = new Product("Table");
        factory.add(chair);
        factory.add(table);
        
        new UI(factory);
        
        // Factory f = new Factory();
        // HashMap<Item, Integer> hm = new HashMap<>();
        // hm.put(new Item("flour", "test", 500, 1000, 100), 10);
        // HashSet<LocalDate> hs = new HashSet<>();
        // LocalDate l = LocalDate.of(2006, 4, 11);
        // hs.add(l);
        // f.add(new Product("bread", hm, hs));

        // FileUtils.saveProducts(f);
        
        // List<Product> p = FileUtils.readProducts();
        // System.out.println(p.get(0).getRequiredItems());
    }
}
