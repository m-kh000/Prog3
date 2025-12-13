package ui.functions;
import core.Factory;
import utils.Validator;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import ui.LabelBox;
import ui.Manager;

public class AddTask extends FunctionPanel {
    public AddTask(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new GridLayout(7, 1, 10, 10));
        
        add(createTopPanel("Add Task", centerPanel, frame, factory, "supervisor"));
        
        // add(new LabelBox("Product Name:", false));
        // add(new LabelBox("Required Quantity:", false));
        // add(new LabelBox("Customer Name:", false));
        // add(new LabelBox("Start Date:", false));
        // add(new LabelBox("Delivery Date:", false));
        LabelBox name = new LabelBox("Product Name:");
        LabelBox quantity = new LabelBox("Required Quantity:");
        LabelBox customer = new LabelBox("Customer Name:");
        LabelBox start = new LabelBox("Start Date:");
        LabelBox delivery = new LabelBox("Delivery Date:");
        add(name);
        add(quantity);
        add(customer);
        add(start);
        add(delivery);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(Manager.defaultFont(true, false));
        add(submitBtn);

        submitBtn.addActionListener(e -> {
            String nameText = name.getText();
            String quantityText = quantity.getText();
            String customerText = customer.getText();
            String startText = start.getText();
            String deliveryText = delivery.getText();
            LocalDate startDate = utils.Validator.validateDate(startText);
            LocalDate deliveryDate = utils.Validator.validateDate(deliveryText);
            factory.addTask(nameText, quantityText, customerText, startDate, deliveryDate);
            name.setText("");
            quantity.setText("");
            customer.setText("");
            customer.setText("");
            start.setText("");
            delivery.setText("");
            JOptionPane.showMessageDialog(frame, "Task added successfully");
        });
    }
}