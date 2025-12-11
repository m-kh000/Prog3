package ui.functions;
import javax.swing.*;
import java.awt.*;
import core.Factory;
import ui.UI;
import ui.Manager;

public class Tasks extends FunctionPanel {
    public Tasks(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new GridLayout(4, 1, 20, 20));
        Color buttonColor = Color.decode("#5294ff");
        
        add(createTopPanel("Tasks", centerPanel, frame, factory, "supervisor"));
        
        JButton viewTasksBtn = createStyledButton("View All Tasks", buttonColor);
        viewTasksBtn.addActionListener(e -> UI.switchContent(new ViewAllTasks(centerPanel, frame, factory)));
        add(viewTasksBtn);
        
        JButton addTaskBtn = createStyledButton("Add Task", buttonColor);
        addTaskBtn.addActionListener(e -> UI.switchContent(new AddTask(centerPanel, frame, factory)));
        add(addTaskBtn);
        
        JButton cancelTaskBtn = createStyledButton("Cancel Task", buttonColor);
        cancelTaskBtn.addActionListener(e -> UI.switchContent(new CancelTask(centerPanel, frame, factory)));
        add(cancelTaskBtn);
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