package ui.functions;

import core.Factory;
import core.ProductLine;
import java.awt.*;
import javax.swing.*;

import ui.FunctionPanel;
import ui.LabelBox;
import ui.Manager;
import utils.FileUtils;

// AddProductionLine panel for creating new production lines
public class AddProductionLine extends FunctionPanel {

    public AddProductionLine(JPanel centerPanel, JFrame frame ) {
        setLayout(new GridLayout(8, 1, 10, 10));

        // Components
        LabelBox name = new LabelBox("Name:");
        
        JPanel statusPanel = new JPanel(new GridLayout(1, 2));
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> status = new JComboBox<>(new String[] { "Active","Maintenance","Stopped" });
        
        LabelBox priority = new LabelBox("Priority:");
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Listeners
        // Enter key functionality
        priority.getTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    submitBtn.doClick();
                }
            }
        });

        // Submit button listener
        submitBtn.addActionListener(e -> {
            if (name.isEmpty() || priority.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
                return;
            }
            String nameText = name.getText();
            String statusText = (String)status.getSelectedItem();
            String priorityText = priority.getText();
            try {
                Factory.add(new ProductLine(nameText, statusText, Integer.parseInt(priorityText)));
                name.reset();
                status.setSelectedIndex(0);
                priority.reset();
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Product line added successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Error while adding productline: " + ex.getMessage());
                FileUtils.log(ex);
            }
        });
        
        // Layout setup
        add(createTopPanel("Add Product Line:", centerPanel, frame, "manager"));
        add(name);
        
        statusPanel.add(statusLabel);
        statusPanel.add(status);
        add(statusPanel);
        
        add(priority);
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());
        add(submitBtn);
    }
}
