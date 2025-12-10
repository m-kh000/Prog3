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
    }
}
