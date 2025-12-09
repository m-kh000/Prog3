package ui;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.functions.*;

public class CenterManager extends JPanel {
    public CenterManager(JFrame frame, Factory factory) {
        setLayout(new GridLayout(4, 1, 0, 20));
        JLabel title = new JLabel("Manager");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        Color buttonColor = Color.decode("#5294ff");

        JButton addLine = createStyledButton("Add Production Line", buttonColor);
        addLine.addActionListener(e -> {
            frame.getContentPane().remove(this);
            frame.add(new AddProductionLine(frame, factory), BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        JButton modifyStatus = createStyledButton("Modify Status of a Production Line", buttonColor);
        modifyStatus.addActionListener(e -> {
            frame.getContentPane().remove(this);
            frame.add(new ModifyStatusOfAProductionLine(frame, factory), BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        JButton viewPerformance = createStyledButton("View Performance", buttonColor);
        viewPerformance.addActionListener(e -> {
            frame.getContentPane().remove(this);
            frame.add(new ViewPerformance(frame, factory), BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
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