package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.LabelBox;
import core.Factory;

public class AddProductionLine extends FunctionPanel {
    public AddProductionLine(JFrame frame, Factory factory) {
        setLayout(new GridLayout(5, 1, 20, 20));
        
        add(createBackButton(frame, factory, "manager"));
        
        JLabel title = new JLabel("Add Production Line");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        
        add(new LabelBox("Name:", false));
        
        JPanel statusPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Stopped", "Maintenance"});
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        statusPanel.add(statusLabel);
        statusPanel.add(statusCombo);
        add(statusPanel);
        
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        add(submitBtn);
    }
}
