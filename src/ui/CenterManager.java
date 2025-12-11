package ui;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.functions.*;

public class CenterManager extends JPanel {
    public CenterManager(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new GridLayout(4, 1, 0, 20));
        JLabel title = new JLabel("Manager");
        title.setFont(Manager.defaultFont(true, true));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        Color buttonColor = Color.decode("#5294ff");

        JButton addLine = createStyledButton("Add Production Line", buttonColor);
        addLine.addActionListener(e -> UI.switchContent(new AddProductionLine(centerPanel, frame, factory)));

        JButton modifyStatus = createStyledButton("Modify Status of a Production Line", buttonColor);
        modifyStatus.addActionListener(e -> UI.switchContent(new ModifyStatusOfAProductionLine(centerPanel, frame, factory)));

        JButton viewPerformance = createStyledButton("View Performance", buttonColor);
        viewPerformance.addActionListener(e -> UI.switchContent(new ViewPerformance(centerPanel, frame, factory)));

        add(addLine);
        add(modifyStatus);
        add(viewPerformance);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(Manager.defaultFont(true, false));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }
}