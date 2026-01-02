package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.components.ProductPanel;
import javax.swing.JLabel;

public class ViewProducts extends FunctionPanel {
    public ViewProducts(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("View All Products", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(new JScrollPane(createProductsPanel(factory)), BorderLayout.CENTER);
    }
    
    private JPanel createProductsPanel(Factory factory) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (core.Product product : factory.previewProducts()) {
            panel.add(new ProductPanel(product));
            panel.add(Box.createVerticalStrut(5));
        }
        if (factory.previewProducts().length == 0) {
            panel.add(new JLabel("No products found."));
        }
        return panel;
    }
}
