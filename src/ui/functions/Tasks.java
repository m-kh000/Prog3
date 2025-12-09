package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.components.TaskPanel;
import core.Factory;

public class Tasks extends FunctionPanel {
    public Tasks(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("View All Tasks", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(new JScrollPane(createTasksPanel(factory)), BorderLayout.CENTER);
    }
    
    private JPanel createTasksPanel(Factory factory) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
}
