package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.components.ProductLinePanel;
import utils.FileUtils;

public class ViewPerformance extends FunctionPanel {
    public ViewPerformance(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("View Performance", centerPanel, frame, factory, "manager"), BorderLayout.NORTH);
        add(new JScrollPane(createItemsPanel(factory)), BorderLayout.CENTER);
        while(true){
            revalidate();
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                FileUtils.log(ex);
            }
        }
    }
    
    private JPanel createItemsPanel(Factory factory) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (core.ProductLine line : factory.previewLines()) {
            panel.add(new ProductLinePanel(line));
            panel.add(Box.createVerticalStrut(5));
        }
        if (factory.previewLines().length == 0) {
            panel.add(new JLabel("No lines found."));
        }
        return panel;
    }
}
