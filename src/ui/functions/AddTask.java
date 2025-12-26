package ui.functions;
import core.Factory;
import core.Task;
import exceptions.EmptyFieldException;
import java.awt.*;
import javax.swing.*;
import javax.xml.validation.Validator;
import ui.LabelBox;
import ui.Manager;

public class AddTask extends FunctionPanel {
    public AddTask(JPanel centerPanel, JFrame frame, Factory factory) {
        // Main grid: 8 rows, 1 column
        setLayout(new GridLayout(8, 1, 10, 20));
        
        // Row 1: Top panel
        add(createTopPanel("Add Task", centerPanel, frame, factory, "supervisor"));
        
        // Row 2: Select product
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Product:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> product = new JComboBox<String>(factory.getProductNames());
        product.setFont(Manager.defaultFont(true, false));
        product.setSelectedItem(null);
        selectPanel.add(selectLabel);
        selectPanel.add(product);
        add(selectPanel);
        
        // Row 3: Select product line
        JPanel selectPLPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectPLLabel = new JLabel("Select Product Line:");
        selectPLLabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> PL = new JComboBox<String>(factory.getProductLineNames());
        PL.setFont(Manager.defaultFont(true, false));
        PL.setSelectedItem(null);
        selectPLPanel.add(selectPLLabel);
        selectPLPanel.add(PL);
        add(selectPLPanel);
        
        // Row 4: Type quantity
        LabelBox quantity = new LabelBox("Required Quantity:", false);
        add(quantity);
        
        // Row 5: Type customer
        LabelBox customer = new LabelBox("Customer Name:", false);
        add(customer);
        
        // Row 6: Type start date
        LabelBox start = new LabelBox("Start Date:", false);
        add(start);
        
        // Row 7: Type delivery date
        LabelBox delivery = new LabelBox("Delivery Date:", false);
        add(delivery);

        // Row 8: Submit button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(Manager.defaultFont(true, false));
        add(submitBtn);

        submitBtn.addActionListener(e -> {
            try {
                if (product.getSelectedItem() == null || quantity.getText().isEmpty() || customer.getText().isEmpty() || start.getText().isEmpty() || delivery.getText().isEmpty()) {
                    throw new EmptyFieldException();
                }
                
                String productName = (String) product.getSelectedItem();
                String PLtext = (String) PL.getSelectedItem();
                String quantityText = quantity.getText();
                String customerText = customer.getText();
                String startText = start.getText();
                String deliveryText = delivery.getText();
                
                // Clear fields
                product.setSelectedItem(null);
                PL.setSelectedItem(null);
                quantity.reset();
                customer.reset();
                start.reset();
                delivery.reset();

                factory.add(new Task(factory.findProductByName(productName), Integer.parseInt(quantityText), customerText, utils.Validator.validateDate(startText), utils.Validator.validateDate(deliveryText),"inline"),PLtext);
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(frame, "Task added successfully");
                
            } catch (EmptyFieldException ex) {
                JOptionPane.showMessageDialog(frame, "Please fill all the fields");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });
    }
}