package ui.functions;

import core.Factory;
import core.Task;
import java.awt.*;
import javax.swing.*;
import ui.components.TaskPanel;

public class ViewAllTasks extends FunctionPanel {

    public ViewAllTasks(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());

        // Components
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        for (Task task : Factory.previewTasks()) {
            tasksPanel.add(new TaskPanel(task));
            tasksPanel.add(Box.createVerticalStrut(5));
        }
        if (Factory.previewTasks().length == 0) {
            tasksPanel.add(new JLabel("No tasks found."));
        }

        JScrollPane scrollPane = new JScrollPane(tasksPanel);

        // Layout setup
        add(createTopPanel("View All Tasks", centerPanel, frame, "supervisor"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
