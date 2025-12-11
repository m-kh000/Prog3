package main;

import core.*;
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
        //  HashMap<Item, Integer> hm = new HashMap<>();
        //  hm.put(new Item("flour", "test", 500, 1000, 100), 10);
        //  HashSet<LocalDate> hs = new HashSet<>();
        //  LocalDate l = LocalDate.of(2006, 4, 11);
        //  hs.add(l);
        // f.add(new Product("bread", hm, hs));

        // FileUtils.saveProducts(f);
        
        // List<Product> p = FileUtils.readProducts();
        // System.out.println(p.get(0).getRequiredItems());

        Warehouse w = new Warehouse();

        w.addItem(new Item("Screws", "Hardware", 0.5, 100, 20));
        w.addItem(new Item("Bolts", "Hardware", 0.75, 50, 30));
        // Product c = new Product("Chair", hm, hs);
        // w.addProduct(c);
        // w.addProduct(table);

        // String json = JsonParser.toJson(w);
        // System.out.println(json);

        // Warehouse ww = JsonParser.fromJson(json, Warehouse.class, Item.class, Integer.class, null);

        // System.out.println(ww.getItem("Bolts"));
        // System.out.println(ww.getProduct("Chair"));
    }
}
