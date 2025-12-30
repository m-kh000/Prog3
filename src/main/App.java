package main;

import core.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import ui.UI;
import utils.FileUtils;

public class App {
    public static void main(String[] args) throws Exception {
        new UI();
        
        //  Factory f = new Factory();
           HashMap<Item, Integer> hm = new HashMap<>();
           hm.put(new Item("flour", "test", 500, 1000, 100), 10);
           HashSet<LocalDate> hs = new HashSet<>();
           LocalDate l = LocalDate.of(2006, 4, 11);
           hs.add(l);
        // // f.add(new Product("bread", h+m, hs));

        // // FileUtils.saveProducts(f);
        
        // // List<Product> p = FileUtils.readProducts();
        // // System.out.println(p.get(0).getRequiredItems());

        // // Warehouse w = new Warehouse();

        // // w.addItem(new Item("Screws", "Hardware", 0.5, 100, 20));
        // // w.addItem(new Item("Bolts", "Hardware", 0.75, 50, 30));
        Product c = new Product("Chair", hm, hs);
        // // w.addProduct(c);
        // // w.addProduct(table);

        // // String productsJson = JsonParser.toJson(w.getProducts());
        // // System.out.println(productsJson);
        // // String itemsJson = JsonParser.toJson(w.getItems());
        // // System.out.println(itemsJson);

        // // List<Item> ll = new ArrayList<>(Arrays.asList(JsonParser.fromJson(itemsJson, Item[].class, null)));
        // // List<Product> pp = new ArrayList<>(Arrays.asList(JsonParser.fromJson(productsJson, Product[].class, Item.class, Integer.class, null)));
        
        // // Warehouse ww = new Warehouse(ll, pp);

        // // System.out.println(ww.getItem("Bolts").getMinQuantity());
        // // System.out.println(ww.getProduct("Chair").getRequiredQuantityOf("flour"));
        
        // //  f.add(new ProductLine("test", "working"));
        // //  f.getProductLine("test").addTask(new Task(c, 10, "Joseph", LocalDate.now(), LocalDate.of(2029, 5, 1), "todo", 10));

        Factory f = new Factory();
        ProductLine pl = new ProductLine("test", "working", 5);
        pl.addTask(new Task(c, 10, "Joseph", l, l, "good"));
        Factory.add(pl);
        
        FileUtils.saveProductLines();

        HashSet<ProductLine> pls = FileUtils.readProductLines();

        for (ProductLine p : pls) {
          System.out.println(p.getName());
          System.out.println(p.getId());
          System.out.println(p.getLineStatus());
          // System.out.println(p.getInlineTasks().get(0));
          System.out.println(p.getInprogress());
          System.out.println(p.getCompletedTasks());
          System.out.println(p.getCanceledTasks());
        }
    }
}
