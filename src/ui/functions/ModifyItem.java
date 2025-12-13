package ui.functions;

import core.Item;
import exceptions.EmptyFieldException;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import ui.LabelBox;

public class ModifyItem extends FunctionPanel {

    public ModifyItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("Modify Items", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        
        JPanel boxws = new JPanel(new GridLayout(8, 1, 10, 10));

        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Item:");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        String[] allitems = factory.getItemsNames();
        JComboBox<String> itemCombo = new JComboBox<>(allitems);
        itemCombo.setSelectedItem(null);
        itemCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        selectPanel.add(selectLabel);
        selectPanel.add(itemCombo);
        boxws.add(selectPanel);

        LabelBox name = new LabelBox("Name:", false);
        LabelBox cat = new LabelBox("Catigory:", false);
        LabelBox price = new LabelBox("Price:", false);
        LabelBox quan = new LabelBox("Quantity:", false);
        LabelBox minquan = new LabelBox("Min Quantity:", false);
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
        boxws.add(name);
        boxws.add(cat);
        boxws.add(price);
        boxws.add(quan);
        boxws.add(minquan);

        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 20));
        boxws.add(updateBtn);
        updateBtn.addActionListener(e -> {
            try {
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
                
                // Update combo box
                itemCombo.removeAllItems();
                String[] updatedItems = factory.getItemsNames();
                for(String itemName : updatedItems) {
                    itemCombo.addItem(itemName);
                }
                itemCombo.setSelectedItem(null);
                
                // Clear fields
                name.setText("");
                cat.setText("");
                price.setText("");
                quan.setText("");
                minquan.setText("");
                
                JOptionPane.showMessageDialog(null, "Item updated successfully!");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for price and quantities");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error updating item: " + ex.getMessage());
            }
        });
        add(boxws);
    }
}
