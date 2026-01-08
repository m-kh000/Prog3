package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.components.ProductLinePanel;

// ViewPerformance panel for displaying production line performance with real-time updates
public class ViewPerformance extends FunctionPanel {
    
    public ViewPerformance(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());
        
        // Components
        JPanel linesPanel = new JPanel();
        linesPanel.setLayout(new BoxLayout(linesPanel, BoxLayout.Y_AXIS));
        for (core.ProductLine line : Factory.previewLines()) {
            linesPanel.add(new ProductLinePanel(line));
            linesPanel.add(Box.createVerticalStrut(5));
        }
        if (Factory.previewLines().length == 0) {
            linesPanel.add(new JLabel("No lines found."));
        }
        
        JScrollPane scrollPane = new JScrollPane(linesPanel);
        
        // Layout setup
        add(createTopPanel("View Performance", centerPanel, frame, "manager"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
    }
}
