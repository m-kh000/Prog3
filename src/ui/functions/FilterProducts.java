package ui.functions;

import core.Factory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import ui.Manager;
import ui.components.ProductPanel;

public class FilterProducts extends FunctionPanel {
    private JPanel ProductsPanel;
    private JComboBox<String> filterField;

    public FilterProducts(JPanel centerPanel, JFrame frame, Factory factory) {
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
            updateProductsPanel(factory, filterType, filterValue);
        });
        //enter key
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    filterBtn.doClick();
                }
            }
        });

        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter Products", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        ProductsPanel = createProductsPanel();
        add(new JScrollPane(ProductsPanel), BorderLayout.CENTER);
    }

    private void updateProductsPanel(Factory factory, String filterType, String filterValue) {
        ProductsPanel.removeAll();

        List<core.Product> Products = null;
        if (filterType.equals("One ProductLine")) {
            Products = factory.getWarehouse().filterProductsByProductLine(filterValue);
        } else {
            Products = factory.getWarehouse().getTopSaleProducts();
        }

        if (Products != null && !Products.isEmpty()) {
            for (core.Product Product : Products) {
                ProductsPanel.add(new ProductPanel(Product));
                ProductsPanel.add(Box.createVerticalStrut(5));
            }
        }
        else {
            ProductsPanel.add(new JLabel("No products found."));
        }

        ProductsPanel.revalidate();
        ProductsPanel.repaint();
    }

    private JPanel createProductsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
}
