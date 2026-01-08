package ui.functions;

import core.Item;
import core.Factory;
import exceptions.EmptyFieldException;
import java.awt.*;
import java.util.List;
import javax.swing.*;

import ui.FunctionPanel;
import ui.LabelBox;
import ui.Manager;
import utils.FileUtils;

// ModifyItem panel for editing existing item properties
public class ModifyItem extends FunctionPanel {

    public ModifyItem(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        // Components
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Item:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        String[] allitems = Factory.getItemsNames();
        JComboBox<String> itemCombo = new JComboBox<>(allitems);
        itemCombo.setSelectedItem(null);
        itemCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        
        LabelBox name = new LabelBox("Name:", false);
        LabelBox cat = new LabelBox("Category:", false);
        LabelBox price = new LabelBox("Price:", false);
        LabelBox quan = new LabelBox("Quantity:", false);
        LabelBox minquan = new LabelBox("Min Quantity:", false);
        
        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Listeners
        // Auto-populate fields when item is selected
        itemCombo.addActionListener(e -> {
            String selectedName = (String)itemCombo.getSelectedItem();
            if (selectedName != null) {
                List<Item> items = Factory.filterItemsByName(selectedName);
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
        
        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "filter");
        getActionMap().put("filter", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updateBtn.doClick();
            }
        });
        
        // Update button listener
        updateBtn.addActionListener(e -> {
            try {
                if (itemCombo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Please select an item first");
                    return;
                }
                
                if (name.getText().isEmpty() || cat.getText().isEmpty() || price.getText().isEmpty() || quan.getText().isEmpty() || minquan.getText().isEmpty()) {
                    throw new EmptyFieldException();
                }
                
                String selectedItemName = (String)itemCombo.getSelectedItem();
                List<Item> items = Factory.filterItemsByName(selectedItemName);
                if (!items.isEmpty()) {
                    Item item = items.get(0);
                    item.setName(name.getText());
                    item.setCategory(cat.getText());
                    item.setPrice(Double.parseDouble(price.getText()));
                    item.setQuantityAvailable(Integer.parseInt(quan.getText()));
                    item.setMinQuantity(Integer.parseInt(minquan.getText()));
                }
                
                itemCombo.removeAllItems();
                String[] updatedItems = Factory.getItemsNames();
                for(String itemName : updatedItems) {
                    itemCombo.addItem(itemName);
                }
                itemCombo.setSelectedItem(null);
                
                name.reset();
                cat.reset();
                price.reset();
                quan.reset();
                minquan.reset();
                
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Item updated successfully!");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for price and quantities");
                FileUtils.log(ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error while modifying items: " + ex.getMessage());
                FileUtils.log(ex);
            }
        });
        
        // Layout setup
        mainPanel.add(createTopPanel("Modify Items", centerPanel, frame, "supervisor"));
        
        selectPanel.add(selectLabel);
        selectPanel.add(itemCombo);
        mainPanel.add(selectPanel);
        
        mainPanel.add(name);
        mainPanel.add(cat);
        mainPanel.add(price);
        mainPanel.add(quan);
        mainPanel.add(minquan);
        mainPanel.add(updateBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
}
