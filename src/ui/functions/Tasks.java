package ui.functions;
import java.awt.*;
import javax.swing.*;
import ui.UI;

public class Tasks extends FunctionPanel {
    public Tasks(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 20, 20));
        Color buttonColor = Color.decode("#5294ff");
        
        mainPanel.add(createTopPanel("Tasks", centerPanel, frame, "supervisor"));
        
        //Row 1 view
        JButton viewTasksBtn = createStyledButton("View All Tasks", buttonColor);
        viewTasksBtn.addActionListener(e -> UI.switchContent(new ViewAllTasks(centerPanel, frame)));
        mainPanel.add(viewTasksBtn);
        
        //Row 2 view
        JButton addTaskBtn = createStyledButton("Add Task", buttonColor);
        addTaskBtn.addActionListener(e -> UI.switchContent(new AddTask(centerPanel, frame)));
        mainPanel.add(addTaskBtn);
        
        //Row 3 modify
        JButton cancelTaskBtn = createStyledButton("Cancel Task", buttonColor);
        cancelTaskBtn.addActionListener(e -> UI.switchContent(new CancelTask(centerPanel, frame)));
        mainPanel.add(cancelTaskBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
}