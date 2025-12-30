package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.Manager;
import ui.UI;

public class Tasks extends FunctionPanel {
    public Tasks(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new GridLayout(6, 1, 20, 20));
        Color buttonColor = Color.decode("#5294ff");
        
        add(createTopPanel("Tasks", centerPanel, frame, factory, "supervisor"));
        
        //Row 1 view
        JButton viewTasksBtn = createStyledButton("View All Tasks", buttonColor);
        viewTasksBtn.addActionListener(e -> UI.switchContent(new ViewAllTasks(centerPanel, frame, factory)));
        add(viewTasksBtn);
        
        //Row 2 view
        JButton viewTaskBtn = createStyledButton("View Task", buttonColor);
        JButton addTaskBtn = createStyledButton("Add Task", buttonColor);
        addTaskBtn.addActionListener(e -> UI.switchContent(new AddTask(centerPanel, frame, factory)));
        add(addTaskBtn);
        
        //Row 3 modify
        JButton modifyTaskBtn = createStyledButton("Modify Task", buttonColor);
        JButton cancelTaskBtn = createStyledButton("Cancel Task", buttonColor);
        cancelTaskBtn.addActionListener(e -> UI.switchContent(new CancelTask(centerPanel, frame, factory)));
        add(cancelTaskBtn);
    }
    
}