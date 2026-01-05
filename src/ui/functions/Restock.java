package ui.functions;

import core.Factory;
import core.Item;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import ui.components.RestockPanel;

// Restock panel for managing item restocking operations
public class Restock extends FunctionPanel {
    
    public Restock(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        // Components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        List<Item> items = factory.getWarehouse().getItems();
        for (Item item : items) {
            RestockPanel panel = new RestockPanel(item, factory);
            mainPanel.add(panel);
            mainPanel.add(Box.createVerticalStrut(5));
        }
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Layout setup
        add(createTopPanel("Restock Items", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}