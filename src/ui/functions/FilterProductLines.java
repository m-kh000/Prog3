package ui.functions;
import javax.swing.*;
import java.awt.*;

import ui.Manager;
import ui.components.ProductLinePanel;
import ui.components.ProductPanel;
import core.Factory;

public class FilterProductLines extends FunctionPanel {
    private JPanel ProductLinesPanel;
    private JComboBox<String> filterField;

    public FilterProductLines(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new GridLayout(1, 4));
        JLabel filterLabel = new JLabel("Filter by: ");
        JLabel filterLabel2 = new JLabel("a specific product");
        filterLabel.setFont(Manager.defaultFont(false, false));
        filterField = new JComboBox<>(factory.getProductNames());
        filterField.setFont(Manager.defaultFont(false, false));
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(Manager.defaultFont(true, false));

        filterBtn.addActionListener(e -> {
            String filterValue = (String) filterField.getSelectedItem();
            updateProductLinesPanel(factory, filterValue);
        });

        filterPanel.add(filterLabel);
        filterPanel.add(filterLabel2);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter ProductLines", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        ProductLinesPanel = createProductLinesPanel();
        add(new JScrollPane(ProductLinesPanel), BorderLayout.CENTER);
    }

    private void updateProductLinesPanel(Factory factory, String filterValue) {
        ProductLinesPanel.removeAll();

        java.util.List<core.ProductLine> ProductLines = factory.filterProductLinesByProduct(filterValue);
        if (ProductLines != null && !ProductLines.isEmpty()) {
            for (core.ProductLine ProductLine : ProductLines) {
                ProductLinesPanel.add(new ProductLinePanel(ProductLine));
            }
        }
        else {
            ProductLinesPanel.add(new JLabel("No ProductLines found."));
        }

        ProductLinesPanel.revalidate();
        ProductLinesPanel.repaint();
    }

    private JPanel createProductLinesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
}
