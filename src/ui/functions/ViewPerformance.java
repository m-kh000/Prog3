package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.components.ProductLinePanel;

public class ViewPerformance extends FunctionPanel {
    public ViewPerformance(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("View Performance", centerPanel, frame, factory, "manager"), BorderLayout.NORTH);
        add(new JScrollPane(createItemsPanel(factory)), BorderLayout.CENTER);
    }
    
    private JPanel createItemsPanel(Factory factory) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (core.ProductLine line : factory.previewLines()) {
            panel.add(new ProductLinePanel(line));
        }
        if (factory.previewLines().length == 0) {
            panel.add(new JLabel("No lines found."));
        }
        return panel;
    }
}
