package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.components.ProductPanel;

// ViewProducts panel for displaying all products in the warehouse
public class ViewProducts extends FunctionPanel {
    
    public ViewProducts(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        // Components
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        for (core.Product product : factory.previewProducts()) {
            productsPanel.add(new ProductPanel(product));
            productsPanel.add(Box.createVerticalStrut(5));
        }
        if (factory.previewProducts().length == 0) {
            productsPanel.add(new JLabel("No products found."));
        }
        
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        
        // Layout setup
        add(createTopPanel("View All Products", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
