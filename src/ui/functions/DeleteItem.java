package ui.functions;
import core.Factory;
import exceptions.EmptyFieldException;
import java.awt.*;
import javax.swing.*;

import ui.FunctionPanel;
import ui.Manager;
import utils.FileUtils;

public class DeleteItem extends FunctionPanel {

    public DeleteItem(JPanel centerPanel, JFrame frame) {
        
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        // Components creation
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 20, 20));
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Item:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> itemCombo = new JComboBox<>(Factory.getItemsNames());
        itemCombo.setFont(Manager.defaultFont(true, false));
        itemCombo.setSelectedItem(null);
        
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setFont(Manager.defaultFont(true, true));
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setOpaque(true);

        // Listeners
        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "delete");
        getActionMap().put("delete", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                deleteBtn.doClick();
            }
        });

        // Delete button click
        deleteBtn.addActionListener (e -> {
            try {
                if(itemCombo.getSelectedItem()!= null){
                Factory.deleteItem((String)itemCombo.getSelectedItem());
                itemCombo.removeAllItems();
                for(String item : Factory.getItemsNames()) {
                    itemCombo.addItem(item);
                }
                itemCombo.setSelectedItem(null);
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Item deleted successfully");
            }
            else
                throw new EmptyFieldException();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                FileUtils.log(ex);
            }
        });

        // Layout setup
        selectPanel.add(selectLabel);
        selectPanel.add(itemCombo);
        
        mainPanel.add(createTopPanel("Delete Items", centerPanel, frame, "supervisor"));
        mainPanel.add(selectPanel);
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(deleteBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
}