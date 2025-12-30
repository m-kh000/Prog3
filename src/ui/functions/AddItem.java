package ui.functions;

import core.Item;
import java.awt.*;
import javax.swing.*;
import ui.LabelBox;
import ui.Manager;

public class AddItem extends FunctionPanel {

    public AddItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        // Main grid: 8 rows, 1 column
        setLayout(new GridLayout(8, 1, 10, 10));

        // Row 1: Top panel
        add(createTopPanel("Add Item", centerPanel, frame, factory, "supervisor"));

        // Row 2: Type name
        LabelBox name = new LabelBox("Name:");
        add(name);
        
        // Row 3: Type category
        LabelBox category = new LabelBox("Category:");
        add(category);
        
        // Row 4: Type price
        LabelBox price = new LabelBox("Price:");
        add(price);
        
        // Row 5: Type quantity
        LabelBox quantity = new LabelBox("Quantity:");
        add(quantity);
        
        // Row 6: Type min quantity
        LabelBox minquantity = new LabelBox("min quantity:");
        add(minquantity);

        // Row 7: Empty panel
        add(new JPanel());

        // Row 8: Submit button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        add(submitBtn);
        
        // Add Enter key functionality
        minquantity.getTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    submitBtn.doClick();
                }
            }
        });

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
                factory.add(new Item(nameText, categoryText, Integer.parseInt(priceText), Integer.parseInt(quantityText), Integer.parseInt(minquantityText)));
                name.reset();
                category.reset();
                price.reset();
                quantity.reset();
                minquantity.reset();                
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Item added successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }
}
