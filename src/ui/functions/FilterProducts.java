package ui.functions;

import core.Factory;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import ui.FunctionPanel;
import ui.LabelBox;
import ui.Manager;
import ui.components.ProductPanel;
import utils.FileUtils;
import utils.Validator;

public class FilterProducts extends FunctionPanel {

    private JPanel productsPanel;
    private JComboBox<String> filterField;
    private JFrame datesFrame;
    private LabelBox fromDateBox;
    private LabelBox toDateBox;
    private JComboBox<String> filterCombo;

    public FilterProducts(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());

        // Filter panel
        JPanel filterPanel = createFilterPanel();
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter Products", centerPanel, frame, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        // Products panel
        productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        loadAllProducts();
        add(new JScrollPane(productsPanel) {{
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        }}, BorderLayout.CENTER);
    }

    //Top Panel
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4));

        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(Manager.defaultFont(false, false));

        filterCombo = new JComboBox<>(new String[]{"One ProductLine", "Top Sales"});
        filterCombo.setFont(Manager.defaultFont(false, false));
        filterCombo.setSelectedItem(null);

        filterField = new JComboBox<>(Factory.getProductLineNames());
        filterField.setFont(Manager.defaultFont(false, false));
        filterField.setEnabled(false);

        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(Manager.defaultFont(true, false));

        // Filter combo listener
        filterCombo.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            if ("One ProductLine".equals(filterType)) {
                filterField.setEnabled(true);
                filterField.removeAllItems();
                for (String name : Factory.getProductLineNames()) {
                    filterField.addItem(name);
                }
            } else {
                filterField.setEnabled(false);
                showDatesDialog();
            }
        });

        // Filter button listener
        filterBtn.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            if(filterType == null) {
                JOptionPane.showMessageDialog(null, "please fill out all fields");
                return;
            }
            if ("One ProductLine".equals(filterType)) {
                String productLine = (String) filterField.getSelectedItem();
                updateProductsPanel(Factory.getWarehouse().filterProductsByProductLine(productLine), false);
            }
        });

        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "filter");
        getActionMap().put("filter", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                filterBtn.doClick();
            }
        });

        panel.add(filterLabel);
        panel.add(filterCombo);
        panel.add(filterField);
        panel.add(filterBtn);
        return panel;
    }

    private void showDatesDialog() {
        if (datesFrame == null) {
            datesFrame = new JFrame("Select Date Range");
            datesFrame.setLayout(new BorderLayout());
            datesFrame.setSize(400, 250);
            datesFrame.setLocationRelativeTo(null);
            datesFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            datesFrame.add(new Label(),BorderLayout.WEST);
            datesFrame.add(new Label(),BorderLayout.EAST);

            JPanel datesMainPanel = new JPanel(new GridLayout(3, 1, 10, 10));

            fromDateBox = new LabelBox("from: ", false, true);
            toDateBox = new LabelBox("to: ", false, true);

            JButton submitBtn = createDialogButton("Submit");
            JButton cancelBtn = createDialogButton("Cancel");

            submitBtn.addActionListener(e -> {
                try {
                    LocalDate from = Validator.validateDate(fromDateBox.getText());
                    LocalDate to = Validator.validateDate(toDateBox.getText());
                    updateProductsPanel(Factory.getWarehouse().getTopSaleProduct(from, to), true);
                    datesFrame.setVisible(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error in dates: " + ex.getMessage());
                    FileUtils.log(ex);
                }
            });

            cancelBtn.addActionListener(e -> {
                datesFrame.setVisible(false);
                filterCombo.setSelectedIndex(0);
            });

            JPanel btnPanel = new JPanel(new GridLayout(1, 2, 0, 10));
            btnPanel.add(submitBtn);
            btnPanel.add(cancelBtn);

            datesMainPanel.add(fromDateBox);
            datesMainPanel.add(toDateBox);
            datesMainPanel.add(btnPanel);
            datesFrame.add(datesMainPanel,BorderLayout.CENTER);
        }
        datesFrame.setVisible(true);
    }

    private JButton createDialogButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(Manager.defaultFont(false, false));
        btn.setForeground(Color.decode("#5294ff"));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadAllProducts() {
        for (core.Product product : Factory.previewProducts()) {
            productsPanel.add(new ProductPanel(product));
            productsPanel.add(Box.createVerticalStrut(5));
        }
    }

    private void updateProductsPanel(List<core.Product> products, boolean highlightTop3) {
        productsPanel.removeAll();

        if (products != null && !products.isEmpty()) {
            Color[] topColors = {new Color(0x77aaff), new Color(0x4499ff), new Color(0x2244ff)};
            
            for (int i = 0; i < products.size(); i++) {
                ProductPanel panel = new ProductPanel(products.get(i));
                if (highlightTop3 && i < 3) {
                    panel.purchaseFrequency.setForeground(topColors[i]);
                }
                productsPanel.add(panel);
                productsPanel.add(Box.createVerticalStrut(5));
            }
        } else {
            productsPanel.add(new JLabel("No products found."));
        }

        productsPanel.revalidate();
        productsPanel.repaint();
    }
}