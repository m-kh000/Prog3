package ui.functions;
import core.Factory;
import core.Item;
import exceptions.EmptyFieldException;

import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.*;
import ui.LabelBox;
import ui.Manager;

public class AddProduct extends FunctionPanel {

    public AddProduct(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        // Top panel with navigation
        add(createTopPanel("Add a Product", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);

        // Product name input panel
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        LabelBox productNameBox = new LabelBox("Product Name:");
        productNameBox.setPreferredSize(new Dimension(300,300));
        namePanel.add(productNameBox);
        add(namePanel, BorderLayout.CENTER);

        // Scrollable panel for item requirements
        JPanel itemRequirementsPanel = new JPanel();
        itemRequirementsPanel.setLayout(new BoxLayout(itemRequirementsPanel, BoxLayout.Y_AXIS));
        itemRequirementsPanel.setBorder(BorderFactory.createTitledBorder("Item Requirements"));
        itemRequirementsPanel.add(new ItemRequirementRow(factory, true));

        JScrollPane scrollPane = new JScrollPane(itemRequirementsPanel);
        scrollPane.setPreferredSize(new Dimension(600, 450));
        add(scrollPane, BorderLayout.SOUTH);

        // Add product button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addProductButton = new JButton("Add Product");
        addProductButton.setFont(Manager.defaultFont(true, false));
        addProductButton.addActionListener(e -> {
            String productName = productNameBox.getText().trim();
            if (productName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Product name cannot be empty.");
                return;
            }
            
            try {
                // Collect item requirements from all rows
                HashMap<Item, Integer> itemRequirements = new HashMap<>();
                for (Component component : itemRequirementsPanel.getComponents()) {
                    if (component instanceof ItemRequirementRow) {
                        ItemRequirementRow row = (ItemRequirementRow) component;
                        if (row.itemDropdown.getSelectedItem() != null && !row.quantityInput.getText().trim().isEmpty()) {
                            Item selectedItem = factory.findItemByName(row.itemDropdown.getSelectedItem().toString());
                            int quantity = Integer.parseInt(row.quantityInput.getText().trim());
                            itemRequirements.put(selectedItem, quantity);
                        }
                        else{
                            throw new EmptyFieldException();
                        }
                    }
                }
                
                // Create and add the product
                factory.add(new core.Product(productName, itemRequirements, (HashSet<LocalDate>) null));
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(frame, "Product added successfully.");
                
                // Clear form
                productNameBox.reset();
                itemRequirementsPanel.removeAll();
                itemRequirementsPanel.add(new ItemRequirementRow(factory, true));
                itemRequirementsPanel.revalidate();
                itemRequirementsPanel.repaint();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter number quantities.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding product: " + ex.getMessage());
            }
        });
        
        buttonPanel.add(addProductButton);
        add(buttonPanel, BorderLayout.EAST);
    }

    // Inner class for item requirement rows
    private class ItemRequirementRow extends JPanel {
        JComboBox<String> itemDropdown;
        LabelBox quantityInput;
        
        public ItemRequirementRow(Factory factory, boolean isLastRow) {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(450, 40));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            
            // Item selection dropdown
            itemDropdown = new JComboBox<>(factory.getItemNames());
            itemDropdown.setFont(Manager.defaultFont(false, false));
            itemDropdown.setPreferredSize(new Dimension(100, 50));
            add(itemDropdown, BorderLayout.WEST);
            
            // Quantity input
            quantityInput = new LabelBox("Quantity:");
            add(quantityInput, BorderLayout.CENTER);
            
            // Add button for last row, empty panel for others
            if (isLastRow) {
                JButton addRowButton = new JButton("+");
                addRowButton.setFont(Manager.defaultFont(true, false));
                addRowButton.setFont(Manager.defaultFont(true, false));
                addRowButton.setForeground(Color.decode("#5294ff"));
                addRowButton.setBackground(UIManager.getColor("Panel.background"));
                addRowButton.setFocusPainted(false);
                addRowButton.setBorderPainted(false);
                addRowButton.setOpaque(false);
                addRowButton.setPreferredSize(new Dimension(100,50));
                addRowButton.addActionListener(e -> {
                    // Add new row and disable this button
                    ItemRequirementRow newRow = new ItemRequirementRow(factory, true);
                    ItemRequirementRow.this.getParent().add(newRow);
                    ItemRequirementRow.this.getParent().revalidate();
                    ItemRequirementRow.this.getParent().repaint();
                    addRowButton.setEnabled(false);
                });
                add(addRowButton, BorderLayout.EAST);
            } else {
                add(new JPanel(), BorderLayout.EAST);
            }
        }
    }
}