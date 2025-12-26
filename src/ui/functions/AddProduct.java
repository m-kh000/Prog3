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
    private JPanel itemRequirementsPanel; // Store reference for refreshing

    public AddProduct(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        // Top panel with navigation
        add(createTopPanel("Add a Product", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);

        // Product name input panel
        LabelBox productNameBox = new LabelBox("Product Name:");
        add(productNameBox, BorderLayout.CENTER);

        // Scrollable panel for item requirements
        itemRequirementsPanel = new JPanel();
        itemRequirementsPanel.setLayout(new BoxLayout(itemRequirementsPanel, BoxLayout.Y_AXIS));
        itemRequirementsPanel.setBorder(BorderFactory.createTitledBorder("Item Requirements"));
        itemRequirementsPanel.add(new ItemRequirementRow(factory, true));

        JScrollPane scrollPane = new JScrollPane(itemRequirementsPanel);
        scrollPane.setPreferredSize(new Dimension(600, 450));
        add(scrollPane, BorderLayout.SOUTH);

        // Add product button
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        CustomBtn addProductButton = new CustomBtn("Add Product");
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

    // Get available items excluding already selected ones
    private String[] getAvailableItems(Factory factory) {
        String[] allItems = factory.getItemNames();
        java.util.List<String> availableItems = new java.util.ArrayList<>();
        java.util.Set<String> selectedItems = new java.util.HashSet<>();
        
        // Collect already selected items
        for (Component component : itemRequirementsPanel.getComponents()) {
            if (component instanceof ItemRequirementRow) {
                ItemRequirementRow row = (ItemRequirementRow) component;
                if (row.itemDropdown.getSelectedItem() != null) {
                    selectedItems.add(row.itemDropdown.getSelectedItem().toString());
                }
            }
        }
        
        // Add only non-selected items
        for (String item : allItems) {
            if (!selectedItems.contains(item)) {
                availableItems.add(item);
            }
        }
        
        return availableItems.toArray(new String[0]);
    }

    private class ItemRequirementRow extends JPanel {

        JComboBox<String> itemDropdown;
        LabelBox quantityInput;
        boolean isLast;
        JPanel coupleOfButtons = new JPanel(new GridLayout(1,2));
        CustomBtn addRowButton = new CustomBtn("+");
        CustomBtn removeRowButton = new CustomBtn("-");

        public ItemRequirementRow(Factory factory, boolean isLastRow) {
            setLayout(new BorderLayout());
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            
            // Item selection dropdown
            itemDropdown = new JComboBox<>(getAvailableItems(factory));
            itemDropdown.setFont(Manager.defaultFont(false, false));
            itemDropdown.setPreferredSize(new Dimension(100, 50));
            add(itemDropdown, BorderLayout.WEST);
            
            // Quantity input
            quantityInput = new LabelBox("Quantity:");
            add(quantityInput, BorderLayout.CENTER);
            
            this.isLast = isLastRow;
            addRowButton.setPreferredSize(new Dimension(100,50));
            removeRowButton.setPreferredSize(new Dimension(100,50));
            // Add/Remove buttons
            if (isLastRow) {
                removeRowButton.addActionListener(e -> {
                    // Remove this row and refresh
                    if(isLast && itemRequirementsPanel.getComponentCount() > 1){
                        ItemRequirementRow newLastRow = (ItemRequirementRow)(itemRequirementsPanel.getComponent(itemRequirementsPanel.getComponentCount() - 2));
                        newLastRow.isLast = true;
                        newLastRow.addRowButton.setEnabled(true);
                    }else if(itemRequirementsPanel.getComponentCount() == 1){
                        JOptionPane.showMessageDialog(null, "At least one item requirement is required.");
                        return;
                    }
                    itemRequirementsPanel.remove(ItemRequirementRow.this);
                    itemRequirementsPanel.revalidate();
                    itemRequirementsPanel.repaint();
                });
                addRowButton.addActionListener(e -> {
                    // Add new row and refresh
                    ItemRequirementRow newRow = new ItemRequirementRow(factory, true);
                    itemRequirementsPanel.add(newRow);
                    itemRequirementsPanel.revalidate();
                    itemRequirementsPanel.repaint();
                    addRowButton.setEnabled(false);
                });
                coupleOfButtons.add(addRowButton);
                coupleOfButtons.add(removeRowButton);
                add(coupleOfButtons, BorderLayout.EAST);
            } else {
                add(new JPanel(), BorderLayout.EAST);
            }
        }
    }

    private class CustomBtn extends JButton {
        public CustomBtn(String text) {
            super(text);
            setFont(Manager.defaultFont(true, true));
            setForeground(Color.decode("#5294ff"));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

}