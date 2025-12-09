package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.components.ItemPanel;
import core.Factory;

public class ViewItems extends FunctionPanel {
    public ViewItems(JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("View All Items");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(createBackButton(frame, factory, "supervisor"), BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
        
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        
        for (core.Item item : factory.previewItems()) {
            itemsPanel.add(new ItemPanel(item));
        }
        
        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
    }
}
