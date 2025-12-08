// CenterManager.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CenterManager extends JPanel {
    public CenterManager() {
        setLayout(new GridLayout(4, 1, 0, 20));
        JLabel title = new JLabel("Manager");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        // Button color
        Color buttonColor = Color.decode("#5294ff");

        // Button 1: Add Production Line
        JButton addLine = createStyledButton("Add Production Line", buttonColor);
        addLine.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Add Production Line clicked");
            // Replace with: new AddProductionLinePage();
        });

        // Button 2: Modify Status
        JButton modifyStatus = createStyledButton("Modify Status of a Production Line", buttonColor);
        modifyStatus.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Modify Status clicked");
            // Replace with: new ModifyStatusPage();
        });

        // Button 3: View Performance
        JButton viewPerformance = createStyledButton("View Performance", buttonColor);
        viewPerformance.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "View Performance clicked");
            // Replace with: new ViewPerformancePage();
        });

        add(addLine);
        add(modifyStatus);
        add(viewPerformance);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }
}