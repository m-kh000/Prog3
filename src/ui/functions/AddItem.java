package ui.functions;

import javax.swing.*;
import java.awt.*;
import ui.LabelBox;
import core.Factory;
import core.Item;
import javax.swing.JOptionPane;

public class AddItem extends FunctionPanel {

    public AddItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(8, 1, 10, 10));

        add(createTopPanel("Add Item", centerPanel, frame, factory, "supervisor"));

        LabelBox name = new LabelBox("Name:");
        LabelBox category = new LabelBox("Category:");
        LabelBox price = new LabelBox("Price:");
        LabelBox quantity = new LabelBox("Quantity:");
        LabelBox minquantity = new LabelBox("min quantity:");
        add(name);
        add(category);
        add(price);
        add(quantity);
        add(minquantity);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        add(submitBtn);

        submitBtn.addActionListener(e -> {
            String nameText = name.getText();
            String categoryText = category.getText();
            String priceText = price.getText();
            String quantityText = quantity.getText();
            String minquantityText = minquantity.getText();
            if (nameText.isEmpty() || categoryText.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || minquantityText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
                return;
            }
            try {
                factory.add(new Item(nameText, categoryText, Integer.parseInt(priceText), Integer.parseInt(quantityText), Integer.parseInt(minquantityText)));
                JOptionPane.showMessageDialog(null, "Item added successfully");
                name.setText("");
                category.setText("");
                price.setText("");
                quantity.setText("");
                minquantity.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }
}
