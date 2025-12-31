package ui;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.functions.*;

public class CenterSupervisor extends JPanel {

    public CenterSupervisor(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        JPanel mainPanel = new JPanel(new GridLayout(7, 1, 0, 20));
        Color buttonColor = Color.decode("#5294ff");

        JLabel title = new JLabel("Supervisor");
        title.setFont(Manager.defaultFont(true, true));
        title.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(title);

        JPanel row1 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton itemsBtn = createStyledButton("Items", buttonColor);
        itemsBtn.addActionListener(e -> UI.switchContent(new Items(centerPanel, frame, factory)));
        JButton filterItemsBtn = createStyledButton("Filter Items", buttonColor);
        filterItemsBtn.addActionListener(e -> UI.switchContent(new FilterItems(centerPanel, frame, factory)));
        row1.add(itemsBtn);
        row1.add(filterItemsBtn);
        mainPanel.add(row1);

        JPanel row2 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton tasksBtn = createStyledButton("Tasks", buttonColor);
        tasksBtn.addActionListener(e -> UI.switchContent(new Tasks(centerPanel, frame, factory)));
        JButton filterTasksBtn = createStyledButton("Filter Tasks", buttonColor);
        filterTasksBtn.addActionListener(e -> UI.switchContent(new FilterTasks(centerPanel, frame, factory)));
        row2.add(tasksBtn);
        row2.add(filterTasksBtn);
        mainPanel.add(row2);

        JPanel row3 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton productsBtn = createStyledButton("Products", buttonColor);
        productsBtn.addActionListener(e -> UI.switchContent(new Products(centerPanel, frame, factory)));
        JButton filterProductsBtn = createStyledButton("Filter Products", buttonColor);
        filterProductsBtn.addActionListener(e -> UI.switchContent(new FilterProducts(centerPanel, frame, factory)));
        row3.add(productsBtn);
        row3.add(filterProductsBtn);
        mainPanel.add(row3);

        JButton filterProductionLinesBtn = createStyledButton("Filter Production Lines", buttonColor);
        filterProductionLinesBtn.addActionListener(e -> UI.switchContent(new FilterProductLines(centerPanel, frame, factory)));
        mainPanel.add(filterProductionLinesBtn);

        JButton saveStatusBtn = createStyledButton("Save Status to TXT", buttonColor);
        saveStatusBtn.addActionListener(e -> {
            if(Manager.isEdited)
            try {
                factory.saveToTXT();
                Manager.isEdited = false;
                JOptionPane.showMessageDialog(frame, "Data saved successfully to TXT files.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error saving data: " + ex.getMessage());
            }
            else{
                JOptionPane.showMessageDialog(frame, "No edits to save.");
            }
        });
        mainPanel.add(saveStatusBtn);

        //deliver a task button
        JButton deliverTaskBtn = createStyledButton("Deliver a Task", buttonColor);
        deliverTaskBtn.addActionListener(e -> UI.switchContent(new DeliverTask(centerPanel, frame, factory)));
        mainPanel.add(deliverTaskBtn);
        
        add(mainPanel, BorderLayout.CENTER);
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
