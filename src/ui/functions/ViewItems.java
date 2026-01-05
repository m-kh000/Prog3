package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.components.ItemPanel;
import core.Factory;

// ViewItems panel for displaying all items in the warehouse
public class ViewItems extends FunctionPanel {
    
    public ViewItems(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        // Components
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        for (core.Item item : factory.previewItems()) {
            itemsPanel.add(new ItemPanel(item));
            itemsPanel.add(Box.createVerticalStrut(5));
        }
        if (factory.previewItems().length == 0) {
            itemsPanel.add(new JLabel("No items found."));
        }
        
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        
        // Layout setup
        add(createTopPanel("View All Items", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
