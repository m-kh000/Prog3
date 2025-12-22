package ui.functions;

import core.Item;
import exceptions.EmptyFieldException;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import ui.LabelBox;
import ui.Manager;

public class ModifyItem extends FunctionPanel {

    public ModifyItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        // Main grid: 8 rows, 1 column
        setLayout(new GridLayout(8, 1, 10, 10));

        // Row 1: Top panel
        add(createTopPanel("Modify Items", centerPanel, frame, factory, "supervisor"));
        
        // Row 2: Select item
        JPanel selectPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        JLabel selectLabel = new JLabel("Select Item:");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        String[] allitems = factory.getItemsNames();
        JComboBox<String> itemCombo = new JComboBox<>(allitems);
        itemCombo.setSelectedItem(null);
        itemCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        selectPanel.add(selectLabel);
        selectPanel.add(itemCombo);
        add(selectPanel);

        // Row 3: Type name
        LabelBox name = new LabelBox("Name:", false);
        add(name);
        
        // Row 4: Type category
        LabelBox cat = new LabelBox("Category:", false);
        add(cat);
        
        // Row 5: Type price
        LabelBox price = new LabelBox("Price:", false);
        add(price);
        
        // Row 6: Type quantity
        LabelBox quan = new LabelBox("Quantity:", false);
        add(quan);
        
        // Row 7: Type min quantity
        LabelBox minquan = new LabelBox("Min Quantity:", false);
        add(minquan);

        // Row 8: Update button
        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 20));
        add(updateBtn);
        // Auto-populate fields when item is selected
        itemCombo.addActionListener(e -> {
            String selectedName = (String)itemCombo.getSelectedItem();
            if (selectedName != null) {
                List<Item> items = factory.filterItemsByName(selectedName);
                if (!items.isEmpty()) {
                    Item item = items.get(0);
                    name.setText(item.getName());
                    cat.setText(item.getCategory());
                    price.setText(String.valueOf(item.getPrice()));
                    quan.setText(String.valueOf(item.getQuantityAvailable()));
                    minquan.setText(String.valueOf(item.getMinQuantity()));
                }
            }
        });
        updateBtn.addActionListener(e -> {
            try {
                // Validate selection and input fields
                if (itemCombo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Please select an item first");
                    return;
                }
                
                if (name.getText().isEmpty() || cat.getText().isEmpty() || price.getText().isEmpty() || quan.getText().isEmpty() || minquan.getText().isEmpty()) {
                    throw new EmptyFieldException();
                }
                
                // Find and update the item
                String selectedItemName = (String)itemCombo.getSelectedItem();
                List<Item> items = factory.filterItemsByName(selectedItemName);
                if (!items.isEmpty()) {
                    Item item = items.get(0);
                    item.setName(name.getText());
                    item.setCategory(cat.getText());
                    item.setPrice(Double.parseDouble(price.getText()));
                    item.setQuantityAvailable(Integer.parseInt(quan.getText()));
                    item.setMinQuantity(Integer.parseInt(minquan.getText()));
                }
                
                // Update combo box with new item names
                itemCombo.removeAllItems();
                String[] updatedItems = factory.getItemsNames();
                for(String itemName : updatedItems) {
                    itemCombo.addItem(itemName);
                }
                itemCombo.setSelectedItem(null);
                
                // Clear all input fields
                name.reset();
                cat.reset();
                price.reset();
                quan.reset();
                minquan.reset();
                
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Item updated successfully!");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for price and quantities");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error updating item: " + ex.getMessage());
            }
        });
    }
}
