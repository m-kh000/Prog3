package ui;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.functions.*;
import utils.FileUtils;

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
        
        // Components creation
        JPanel mainPanel = new JPanel(new GridLayout(7, 1, 0, 20));
        Color buttonColor = Color.decode("#5294ff");

        JLabel title = new JLabel("Supervisor");
        title.setFont(Manager.defaultFont(true, true));
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel row1 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton itemsBtn = createStyledButton("Items", buttonColor);
        JButton filterItemsBtn = createStyledButton("Filter Items", buttonColor);

        JPanel row2 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton tasksBtn = createStyledButton("Tasks", buttonColor);
        JButton filterTasksBtn = createStyledButton("Filter Tasks", buttonColor);

        JPanel row3 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton productsBtn = createStyledButton("Products", buttonColor);
        JButton filterProductsBtn = createStyledButton("Filter Products", buttonColor);

        JButton filterProductionLinesBtn = createStyledButton("Filter Production Lines", buttonColor);
        JButton saveStatusBtn = createStyledButton("Save Status to TXT", buttonColor);
        JButton deliverTaskBtn = createStyledButton("Deliver a Task", buttonColor);

        // Listeners
        // Items button click
        itemsBtn.addActionListener(e -> UI.switchContent(new Items(centerPanel, frame, factory)));
        
        // Filter items button click
        filterItemsBtn.addActionListener(e -> UI.switchContent(new FilterItems(centerPanel, frame, factory)));
        
        // Tasks button click
        tasksBtn.addActionListener(e -> UI.switchContent(new Tasks(centerPanel, frame, factory)));
        
        // Filter tasks button click
        filterTasksBtn.addActionListener(e -> UI.switchContent(new FilterTasks(centerPanel, frame, factory)));
        
        // Products button click
        productsBtn.addActionListener(e -> UI.switchContent(new Products(centerPanel, frame, factory)));
        
        // Filter products button click
        filterProductsBtn.addActionListener(e -> UI.switchContent(new FilterProducts(centerPanel, frame, factory)));
        
        // Filter production lines button click
        filterProductionLinesBtn.addActionListener(e -> UI.switchContent(new FilterProductLines(centerPanel, frame, factory)));
        
        // Save status button click
        saveStatusBtn.addActionListener(e -> {
            if(Manager.isEdited)
            try {
                Factory.saveToTXT();
                Manager.isEdited = false;
                JOptionPane.showMessageDialog(frame, "Data saved successfully to TXT files.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error saving data: " + ex.getMessage());
                FileUtils.log(ex);
            }
            else{
                JOptionPane.showMessageDialog(frame, "No edits to save.");
            }
        });
        
        // Deliver task button click
        deliverTaskBtn.addActionListener(e -> UI.switchContent(new DeliverTask(centerPanel, frame, factory)));

        // Layout setup
        row1.add(itemsBtn);
        row1.add(filterItemsBtn);
        row2.add(tasksBtn);
        row2.add(filterTasksBtn);
        row3.add(productsBtn);
        row3.add(filterProductsBtn);
        
        mainPanel.add(title);
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(filterProductionLinesBtn);
        mainPanel.add(saveStatusBtn);
        mainPanel.add(deliverTaskBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    // Create styled button with specified color
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