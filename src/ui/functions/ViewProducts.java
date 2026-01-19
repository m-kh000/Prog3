package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;

import ui.FunctionPanel;
import ui.components.ProductPanel;

// ViewProducts panel for displaying all products in the warehouse
public class ViewProducts extends FunctionPanel {
    
    public ViewProducts(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());
        
        // Components
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        for (core.Product product : Factory.previewProducts()) {
            productsPanel.add(new ProductPanel(product));
            productsPanel.add(Box.createVerticalStrut(5));
        }
        if (Factory.previewProducts().length == 0) {
            productsPanel.add(new JLabel("No products found."));
        }
        
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Layout setup
        add(createTopPanel("View All Products", centerPanel, frame, "supervisor"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
