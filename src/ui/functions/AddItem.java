package ui.functions;

import core.Item;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.LabelBox;
import ui.Manager;
import utils.FileUtils;


public class AddItem extends FunctionPanel {

    public AddItem(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        // Components creation
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        LabelBox name = new LabelBox("Name:");
        LabelBox category = new LabelBox("Category:");
        LabelBox price = new LabelBox("Price:");
        LabelBox quantity = new LabelBox("Quantity:");
        LabelBox minquantity = new LabelBox("min quantity:");
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));

        // Listeners
        // Enter key functionality
        minquantity.getTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    submitBtn.doClick();
                }
            }
        });

        // Submit button click
        submitBtn.addActionListener(e -> {
            if (name.isEmpty() || category.isEmpty() || price.isEmpty() || quantity.isEmpty() || minquantity.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
                return;
            }
            String nameText = name.getText();
            String categoryText = category.getText();
            String priceText = price.getText();
            String quantityText = quantity.getText();
            String minquantityText = minquantity.getText();
            try {
                Factory.add(new Item(nameText, categoryText, Integer.parseInt(priceText), Integer.parseInt(quantityText), Integer.parseInt(minquantityText)));
                name.reset();
                category.reset();
                price.reset();
                quantity.reset();
                minquantity.reset();                
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Item added successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                FileUtils.log(ex);
            }
        });

        // Layout setup
        mainPanel.add(createTopPanel("Add Item", centerPanel, frame, "supervisor"));
        mainPanel.add(name);
        mainPanel.add(category);
        mainPanel.add(price);
        mainPanel.add(quantity);
        mainPanel.add(minquantity);
        mainPanel.add(new JPanel());
        mainPanel.add(submitBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
}