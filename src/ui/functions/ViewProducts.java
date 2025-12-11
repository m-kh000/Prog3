package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.components.ProductPanel;

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
        }
        return panel;
    }
}
