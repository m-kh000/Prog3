package main;

import ui.UI;
import core.*;

public class App {
    public static void main(String[] args) throws Exception {
        Factory factory = new Factory();
        
        factory.add(new Item(new StringBuffer("Screws"), new StringBuffer("Hardware"), 0.5, 100, 20));
        factory.add(new Item(new StringBuffer("Bolts"), new StringBuffer("Hardware"), 0.75, 50, 30));
        factory.add(new Item(new StringBuffer("Wood"), new StringBuffer("Material"), 10.0, 15, 25));
        
        Product chair = new Product("Chair");
        Product table = new Product("Table");
        factory.add(chair);
        factory.add(table);
        
        new UI(factory);
    }
}
