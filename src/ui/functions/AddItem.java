package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.LabelBox;
import core.Factory;

public class AddItem extends FunctionPanel {
    public AddItem(JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(8, 1, 10, 10));
        
        add(createBackButton(frame, factory, "supervisor"));
        
        JLabel title = new JLabel("Add Item");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        
        add(new LabelBox("Name:", false));
        add(new LabelBox("Category:", false));
        add(new LabelBox("Price:", false));
        add(new LabelBox("Quantity:", false));
        add(new LabelBox("Min Quantity:", false));
        
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        add(submitBtn);
    }
}
