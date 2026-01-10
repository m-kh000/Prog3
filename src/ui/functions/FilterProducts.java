package ui.functions;

import core.Factory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;

import ui.FunctionPanel;
import ui.Manager;
import ui.components.ProductPanel;

public class FilterProducts extends FunctionPanel {
    private JPanel ProductsPanel;
    private JComboBox<String> filterField;

    public FilterProducts(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());

        // Components creation
        JPanel filterPanel = new JPanel(new GridLayout(1, 4));
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(Manager.defaultFont(false, false));
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"One ProductLine", "Top Sales"});
        filterCombo.setFont(Manager.defaultFont(false, false));
        filterCombo.setSelectedItem(null);
        filterField = new JComboBox<>(Factory.getProductLineNames());
        filterField.setFont(Manager.defaultFont(false, false));
        filterField.setEnabled(false);
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(Manager.defaultFont(true, false));

        // Listeners
        // Filter combo selection changes
        filterCombo.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            filterField.removeAllItems();
            if (filterType.equals("One ProductLine")) {
                filterField.setEnabled(true);
                filterCombo.removeAll();
                for (String productLineName : Factory.getProductLineNames()) {
                    filterField.addItem(productLineName);
                }
            } else {
                filterField.setEnabled(false);
            }
        });

        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "filter");
        getActionMap().put("filter", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                filterBtn.doClick();
            }
        });

        // Filter button click
        filterBtn.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            String filterValue = (String) filterField.getSelectedItem();
            updateProductsPanel(filterType, filterValue);
        });

        // Layout setup
        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter Products", centerPanel, frame, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        ProductsPanel = createProductsPanel();
        // Load all products initially
        for (core.Product product : Factory.previewProducts()) {
            ProductsPanel.add(new ProductPanel(product));
            ProductsPanel.add(Box.createVerticalStrut(5));
        }
        
        add(new JScrollPane(ProductsPanel), BorderLayout.CENTER);
    }

    // Update products panel with filtered results
    private void updateProductsPanel(String filterType, String filterValue) {
        ProductsPanel.removeAll();
        boolean colorful = false;
        int counter = 0;
        List<core.Product> Products = null;
        if (filterType.equals("One ProductLine")) {
            Products = Factory.getWarehouse().filterProductsByProductLine(filterValue);
        } else {
            Products = Factory.getWarehouse().getTopSaleProducts();
            colorful = true;
        }

        if (Products != null && !Products.isEmpty()) {
            for (core.Product Product : Products) {
                ProductPanel ppanel = new ProductPanel(Product);
                if(colorful){
                    if(counter == 0){
                        ppanel.purchaseFrequency.setForeground(new Color(0xaa0000));
                        counter++;
                    }else if(counter == 1){
                        ppanel.purchaseFrequency.setForeground(new Color(0x0000bb));
                        counter++;
                    }else if(counter == 2){
                        ppanel.purchaseFrequency.setForeground(new Color(0x00cc00));
                        colorful=false;
                    }
                }
                ProductsPanel.add(ppanel);
                ProductsPanel.add(Box.createVerticalStrut(5));
            }
        }
        else {
            ProductsPanel.add(new JLabel("No products found."));
        }

        ProductsPanel.revalidate();
        ProductsPanel.repaint();
    }

    // Create products panel with vertical layout
    private JPanel createProductsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
}
