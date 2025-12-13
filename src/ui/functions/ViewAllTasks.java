package ui.functions;
import core.Factory;
import core.Task;
import java.awt.*;
import javax.swing.*;
import ui.components.TaskPanel;

public class ViewAllTasks extends FunctionPanel {
    public ViewAllTasks(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("View All Tasks", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(new JScrollPane(createTasksPanel(factory)), BorderLayout.CENTER);
    }
    
    private JPanel createTasksPanel(Factory factory) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (Task task : factory.previewTasks()) {
            panel.add(new TaskPanel(task));
        } 
        return panel;
    }
}