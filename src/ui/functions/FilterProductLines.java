package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.Manager;
import ui.components.ProductLinePanel;

public class FilterProductLines extends FunctionPanel {
    private JPanel ProductLinesPanel;
    private JComboBox<String> filterField;

    public FilterProductLines(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());

        // Components creation
        JPanel filterPanel = new JPanel(new GridLayout(1, 4));
        JLabel filterLabel = new JLabel("Filter by: ");
        JLabel filterLabel2 = new JLabel("a specific product");
        filterLabel.setFont(Manager.defaultFont(false, false));
        filterField = new JComboBox<>(factory.getProductNames());
        filterField.setFont(Manager.defaultFont(false, false));
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(Manager.defaultFont(true, false));

        // Listeners
        // Filter button click
        filterBtn.addActionListener(e -> {
            String filterValue = (String) filterField.getSelectedItem();
            updateProductLinesPanel(factory, filterValue);
        });
        
        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "filter");
        getActionMap().put("filter", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                filterBtn.doClick();
            }
        });

        // Layout setup
        filterPanel.add(filterLabel);
        filterPanel.add(filterLabel2);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter ProductLines", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        ProductLinesPanel = createProductLinesPanel();
        // Load all product lines initially
        for (core.ProductLine productLine : factory.previewLines()) {
            ProductLinesPanel.add(new ProductLinePanel(productLine));
            ProductLinesPanel.add(Box.createVerticalStrut(5));
        }
        
        add(new JScrollPane(ProductLinesPanel), BorderLayout.CENTER);
    }

    // Update product lines panel with filtered results
    private void updateProductLinesPanel(Factory factory, String filterValue) {
        ProductLinesPanel.removeAll();

        java.util.List<core.ProductLine> ProductLines = factory.filterProductLinesByProduct(filterValue);
        if (ProductLines != null && !ProductLines.isEmpty()) {
            for (core.ProductLine ProductLine : ProductLines) {
                ProductLinesPanel.add(new ProductLinePanel(ProductLine));
                ProductLinesPanel.add(Box.createVerticalStrut(5));
            }
        }
        else {
            ProductLinesPanel.add(new JLabel("No ProductLines found."));
        }

        ProductLinesPanel.revalidate();
        ProductLinesPanel.repaint();
    }

    // Create product lines panel with vertical layout
    private JPanel createProductLinesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
}