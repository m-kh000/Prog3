package ui.functions;

import java.awt.*;
import javax.swing.*;

import exceptions.EmptyFieldException;
import ui.Manager;

public class DeleteItem extends FunctionPanel {

    public DeleteItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        // Main grid: 8 rows, 1 column
        setLayout(new GridLayout(8, 1, 20, 20));
        
        // Row 1: Top panel
        add(createTopPanel("Delete Items", centerPanel, frame, factory, "supervisor"));
        
        // Row 2: Select item
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Item:");
        selectLabel.setFont(Manager.defaultFont(false, false));
        JComboBox<String> itemCombo = new JComboBox<>(factory.getItemsNames());
        itemCombo.setFont(Manager.defaultFont(false, false));
        itemCombo.setSelectedItem(null);
        selectPanel.add(selectLabel);
        selectPanel.add(itemCombo);
        add(selectPanel);

        // Rows 3-7: Empty panels
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());
        
        // Row 8: Delete button
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setFont(Manager.defaultFont(true, true));
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setOpaque(true);
        add(deleteBtn);
        
        // Add Enter key functionality
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "delete");
        getActionMap().put("delete", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                deleteBtn.doClick();
            }
        });

        deleteBtn.addActionListener (e -> {
            // Delete selected item and update combo box
            try {
                if(itemCombo.getSelectedItem()!= null){
                factory.deleteItem((String)itemCombo.getSelectedItem());
                itemCombo.removeAllItems();
                for(String item : factory.getItemsNames()) {
                    itemCombo.addItem(item);
                }
                itemCombo.setSelectedItem(null);
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Item deleted successfully");
            }
            else
                throw new EmptyFieldException();
            }
            catch (Exception ee) {
                JOptionPane.showMessageDialog(null, ee.getMessage());
            }
        });
    }
}
