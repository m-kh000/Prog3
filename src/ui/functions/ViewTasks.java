package ui.functions;
import core.Factory;
import core.Product;
import core.Task;
import exceptions.InvalidValuesException;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import utils.FileUtils;

// ViewTasks panel for displaying tasks with sample data
public class ViewTasks extends FunctionPanel {
    
    public ViewTasks(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        
        // Components
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        
        try {
            // Add sample task
            tasksPanel.add(new ui.components.TaskPanel(new Task(new Product("Chair"), 5, "John Doe", LocalDate.of(2023, 4, 10), LocalDate.of(2023, 4, 20), "In Progress")));
            tasksPanel.add(Box.createVerticalStrut(5));
            
            // Add actual tasks from factory
            for (core.Task task : factory.previewTasks()) {
                tasksPanel.add(new ui.components.TaskPanel(task));
                tasksPanel.add(Box.createVerticalStrut(5));
            }
            if (Factory.previewTasks().length == 0) {
                tasksPanel.add(new JLabel("No tasks found."));
            }
        } catch (InvalidValuesException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            FileUtils.log(e);
        }
        
        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        
        // Layout setup
        add(createTopPanel("View All Tasks", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
