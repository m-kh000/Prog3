package ui;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.functions.*;

public class CenterSupervisor extends JPanel {

    public CenterSupervisor(JFrame frame, Factory factory) {
        setLayout(new GridLayout(7, 1, 0, 20));
        Color buttonColor = Color.decode("#5294ff");

        JLabel title = new JLabel("Supervisor");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);

        JPanel row1 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton itemsBtn = createStyledButton("Items", buttonColor);
        itemsBtn.addActionListener(e -> switchPanel(frame, new Items(frame, factory)));
        JButton filterItemsBtn = createStyledButton("Filter Items", buttonColor);
        filterItemsBtn.addActionListener(e -> switchPanel(frame, new FilterItems(frame, factory)));
        row1.add(itemsBtn);
        row1.add(filterItemsBtn);
        add(row1);

        JPanel row2 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton tasksBtn = createStyledButton("Tasks", buttonColor);
        tasksBtn.addActionListener(e -> switchPanel(frame, new Tasks(frame, factory)));
        JButton filterTasksBtn = createStyledButton("Filter Tasks", buttonColor);
        filterTasksBtn.addActionListener(e -> switchPanel(frame, new FilterTasks(frame, factory)));
        row2.add(tasksBtn);
        row2.add(filterTasksBtn);
        add(row2);

        JPanel row3 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton productsBtn = createStyledButton("Products", buttonColor);
        productsBtn.addActionListener(e -> switchPanel(frame, new Products(frame, factory)));
        JButton filterProductsBtn = createStyledButton("Filter Products", buttonColor);
        filterProductsBtn.addActionListener(e -> switchPanel(frame, new FilterProducts(frame, factory)));
        row3.add(productsBtn);
        row3.add(filterProductsBtn);
        add(row3);

        JButton filterProductionLinesBtn = createStyledButton("Filter Production Lines", buttonColor);
        filterProductionLinesBtn.addActionListener(e -> switchPanel(frame, new FilterProductionLines(frame, factory)));
        add(filterProductionLinesBtn);

        JButton mostRequestedBtn = createStyledButton("Most Requested", buttonColor);
        mostRequestedBtn.addActionListener(e -> switchPanel(frame, new MostRequested(frame, factory)));
        JButton saveStatusBtn = createStyledButton("Save Status to TXT", buttonColor);
        saveStatusBtn.addActionListener(e -> switchPanel(frame, new SaveStatusToTXT(frame, factory)));
        add(mostRequestedBtn);
        add(saveStatusBtn);
    }

    private void switchPanel(JFrame frame, JPanel panel) {
        frame.getContentPane().remove(this);
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
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
