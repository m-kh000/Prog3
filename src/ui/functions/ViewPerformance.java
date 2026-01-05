package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.components.ProductLinePanel;
import utils.FileUtils;

// ViewPerformance panel for displaying production line performance with real-time updates
public class ViewPerformance extends FunctionPanel {
    
    public ViewPerformance(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        // Components
        JPanel linesPanel = new JPanel();
        linesPanel.setLayout(new BoxLayout(linesPanel, BoxLayout.Y_AXIS));
        for (core.ProductLine line : factory.previewLines()) {
            linesPanel.add(new ProductLinePanel(line));
            linesPanel.add(Box.createVerticalStrut(5));
        }
        if (factory.previewLines().length == 0) {
            linesPanel.add(new JLabel("No lines found."));
        }
        
        JScrollPane scrollPane = new JScrollPane(linesPanel);
        
        // Layout setup
        add(createTopPanel("View Performance", centerPanel, frame, factory, "manager"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Real-time updates
        new Thread(() -> {
            while(true) {
                SwingUtilities.invokeLater(() -> {
                    revalidate();
                    repaint();
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    FileUtils.log(ex);
                    break;
                }
            }
        }).start();
    }
}
