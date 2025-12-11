package ui.functions;
import core.Factory;
import core.Product;
import core.Task;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class ViewTasks extends FunctionPanel {
    public ViewTasks(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("View All Tasks", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(new JScrollPane(createTasksPanel(factory)), BorderLayout.CENTER);
    }
    
    private JPanel createTasksPanel(Factory factory) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new ui.components.TaskPanel(new Task(new Product("Chair"), 5, "John Doe", LocalDate.of(2023, 4, 10), LocalDate.of(2023, 4, 20), "In Progress", null, 0.0)));
        for (core.Task task : factory.previewTasks()) {
            panel.add(new ui.components.TaskPanel(task));
        }
        return panel;
    }
}
