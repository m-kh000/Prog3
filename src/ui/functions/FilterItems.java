package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.components.ItemPanel;
import core.Factory;

public class FilterItems extends FunctionPanel {
    public FilterItems(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("Filter Items", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(new JScrollPane(createItemsPanel(factory)), BorderLayout.CENTER);
    }
    
    private JPanel createItemsPanel(Factory factory) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (core.Item item : factory.previewItems()) {
            panel.add(new ItemPanel(item));
        }
        return panel;
    }
}
