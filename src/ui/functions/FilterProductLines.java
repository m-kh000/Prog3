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
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(Manager.defaultFont(false, false));
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"One ProductLine", "Top Sales"});
        filterCombo.setFont(Manager.defaultFont(false, false));
        filterField = new JComboBox<>(factory.getProductLineNames());
        filterField.setFont(Manager.defaultFont(false, false));
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(Manager.defaultFont(true, false));

        filterCombo.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();

            filterField.removeAllItems();
            if (filterType.equals("One ProductLine")) {
                filterField.setEnabled(true);
            } else {
                filterField.setEnabled(false);
            }
        });

        filterBtn.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            String filterValue = (String) filterField.getSelectedItem();
            updateProductLinesPanel(factory, filterType, filterValue);
        });

        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter ProductLines", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        ProductLinesPanel = createProductLinesPanel();
        add(new JScrollPane(ProductLinesPanel), BorderLayout.CENTER);
    }

    private void updateProductLinesPanel(Factory factory, String filterType, String filterValue) {
        ProductLinesPanel.removeAll();

        java.util.List<core.ProductLine> ProductLines = null;
        if (filterType.equals("")){}
        if (ProductLines != null) {
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
