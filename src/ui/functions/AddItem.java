package ui.functions;

import core.Item;
import java.awt.*;
import javax.swing.*;
import ui.LabelBox;
import ui.Manager;

public class AddItem extends FunctionPanel {

    public AddItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        // Main grid: 8 rows, 1 column
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 10, 10));

        // Row 1: Top panel
        mainPanel.add(createTopPanel("Add Item", centerPanel, frame, factory, "supervisor"));

        // Row 2: Type name
        LabelBox name = new LabelBox("Name:");
        mainPanel.add(name);
        
        // Row 3: Type category
        LabelBox category = new LabelBox("Category:");
        mainPanel.add(category);
        
        // Row 4: Type price
        LabelBox price = new LabelBox("Price:");
        mainPanel.add(price);
        
        // Row 5: Type quantity
        LabelBox quantity = new LabelBox("Quantity:");
        mainPanel.add(quantity);
        
        // Row 6: Type min quantity
        LabelBox minquantity = new LabelBox("min quantity:");
        mainPanel.add(minquantity);

        // Row 7: Empty panel
        mainPanel.add(new JPanel());

        // Row 8: Submit button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(submitBtn);
        
        add(mainPanel, BorderLayout.CENTER);
        
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
