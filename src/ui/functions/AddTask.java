package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.LabelBox;
import ui.Manager;
import core.Factory;

public class AddTask extends FunctionPanel {
    public AddTask(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new GridLayout(7, 1, 10, 10));
        
        add(createTopPanel("Add Task", centerPanel, frame, factory, "supervisor"));
        
        add(new LabelBox("Product Name:", false));
        add(new LabelBox("Required Quantity:", false));
        add(new LabelBox("Customer Name:", false));
        add(new LabelBox("Start Date:", false));
        add(new LabelBox("Delivery Date:", false));
        
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(Manager.defaultFont(true, false));
        add(submitBtn);
    }
}