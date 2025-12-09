package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.components.ProductLinePanel;
import core.Factory;

public class FilterProductionLines extends FunctionPanel {
    public FilterProductionLines(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("Filter Production Lines", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(new JScrollPane(createLinesPanel(factory)), BorderLayout.CENTER);
    }
    
    private JPanel createLinesPanel(Factory factory) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (core.ProductLine line : factory.previewLines()) {
            panel.add(new ProductLinePanel(line));
        }
        return panel;
    }
}
