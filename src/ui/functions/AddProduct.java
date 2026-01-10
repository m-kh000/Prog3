package ui.functions;
import core.Factory;
import core.Item;
import utils.FileUtils;
import exceptions.EmptyFieldException;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.*;

import ui.FunctionPanel;
import ui.LabelBox;
import ui.Manager;

// AddProduct panel for creating new products with item requirements
public class AddProduct extends FunctionPanel {
    private JPanel itemRequirementsPanel;

    public AddProduct(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        // Components
        JPanel mainPanel = new JPanel(new BorderLayout());
        LabelBox productNameBox = new LabelBox("Product Name:");
        
        itemRequirementsPanel = new JPanel();
        itemRequirementsPanel.setLayout(new BoxLayout(itemRequirementsPanel, BoxLayout.Y_AXIS));
        itemRequirementsPanel.setBorder(BorderFactory.createTitledBorder("Item Requirements"));
        itemRequirementsPanel.add(new ItemRequirementRow(true));

        JScrollPane scrollPane = new JScrollPane(itemRequirementsPanel);
        scrollPane.setPreferredSize(new Dimension(600, 450));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        CustomBtn addProductButton = new CustomBtn("Add Product");
        addProductButton.setFont(Manager.defaultFont(true, false));
        
        // Listeners
        // Enter key functionality
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "addProduct");
        getActionMap().put("addProduct", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addProductButton.doClick();
            }
        });
        
        // Add product button listener
        addProductButton.addActionListener(e -> {
            try {
                String productName = productNameBox.getText().trim();
                if (productName.isEmpty()) {
                    throw new EmptyFieldException();
                }
                
                HashMap<Item, Integer> itemRequirements = new HashMap<>();
                core.Product newP = new core.Product(productName, itemRequirements, new HashSet<LocalDate>());
                for (Component component : itemRequirementsPanel.getComponents()) {
                    if (component instanceof ItemRequirementRow) {
                        ItemRequirementRow row = (ItemRequirementRow) component;
                        if (row.itemDropdown.getSelectedItem() != null && !row.quantityInput.getText().trim().isEmpty()) {
                            Item selectedItem = Factory.findItemByName(row.itemDropdown.getSelectedItem().toString());
                            int quantity = Integer.parseInt(row.quantityInput.getText().trim());
                            newP.addItem(selectedItem, quantity);
                        } else {
                            throw new EmptyFieldException();
                        }
                    }
                }
                
                Factory.add(newP);
                Manager.isEdited = true;
                
                productNameBox.reset();
                itemRequirementsPanel.removeAll();
                itemRequirementsPanel.add(new ItemRequirementRow( true));
                itemRequirementsPanel.revalidate();
                itemRequirementsPanel.repaint();

                JOptionPane.showMessageDialog(frame, "Product added successfully.");
                
            } catch(EmptyFieldException ex){
                JOptionPane.showMessageDialog(frame, ex.getMessage());
                FileUtils.log(ex);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,"Error while adding product: " + ex.getMessage());
                FileUtils.log(ex);
            }
        });
        
        // Layout setup
        mainPanel.add(createTopPanel("Add a Product", centerPanel, frame, "supervisor"), BorderLayout.NORTH);
        mainPanel.add(productNameBox, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        buttonPanel.add(addProductButton);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    // Get available items excluding already selected ones
    private String[] getAvailableItems() {
        String[] allItems = Factory.getItemNames();
        java.util.List<String> availableItems = new java.util.ArrayList<>();
        java.util.Set<String> selectedItems = new java.util.HashSet<>();
        
        for (Component component : itemRequirementsPanel.getComponents()) {
            if (component instanceof ItemRequirementRow) {
                ItemRequirementRow row = (ItemRequirementRow) component;
                if (row.itemDropdown.getSelectedItem() != null) {
                    selectedItems.add(row.itemDropdown.getSelectedItem().toString());
                }
            }
        }
        
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

        public ItemRequirementRow( boolean isLastRow) {
            setLayout(new BorderLayout());
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            
            itemDropdown = new JComboBox<>(getAvailableItems());
            itemDropdown.setFont(Manager.defaultFont(false, false));
            itemDropdown.setPreferredSize(new Dimension(100, 50));
            add(itemDropdown, BorderLayout.WEST);
            
            quantityInput = new LabelBox("Quantity:");
            add(quantityInput, BorderLayout.CENTER);
            
            this.isLast = isLastRow;
            addRowButton.setPreferredSize(new Dimension(100,50));
            removeRowButton.setPreferredSize(new Dimension(100,50));
            if (isLastRow) {
                removeRowButton.addActionListener(e -> {
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
                    ItemRequirementRow newRow = new ItemRequirementRow(true);
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