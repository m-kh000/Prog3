package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.components.ProductPanel;
import core.Factory;

public class FilterProducts extends FunctionPanel {
    public FilterProducts(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("Filter Products", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
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
