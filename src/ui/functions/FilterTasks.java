package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.components.TaskPanel;
import core.Factory;
import ui.Manager;
import java.util.List;

public class FilterTasks extends FunctionPanel {
    private JPanel tasksPanel;
    
    public FilterTasks(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        JPanel filterPanel = new JPanel(new GridLayout(1, 4));
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(Manager.defaultFont(false, true));
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"ProductLine or Product", "InProgress", "Completed"});
        filterCombo.setFont(Manager.defaultFont(false, true));
        JTextField filterField = new JTextField(20);
        filterField.setFont(Manager.defaultFont(false, true));
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(Manager.defaultFont(true, false));
        
        filterBtn.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            String filterValue = filterField.getText();
            updateTasksPanel(factory, filterType, filterValue);
        });
        
        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);
        
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter Tasks", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);
        
        tasksPanel = createTasksPanel();
        add(new JScrollPane(tasksPanel), BorderLayout.CENTER);
    }
    
    private void updateTasksPanel(Factory factory, String filterType, String filterValue) {
        tasksPanel.removeAll();
        
        List<core.Task> tasks = null;
        if(filterType.equals("ProductLine or Product")) {
            tasks = factory.filterTasksByProduct(filterValue);
        } else if(filterType.equals("InProgress")) {
            tasks = factory.filterTasksByInprogress();
        } else if(filterType.equals("Completed")) {
            tasks = factory.filterTasksByCompleted();
        }
        
        if(tasks != null) {
            for(core.Task task : tasks) {
                tasksPanel.add(new TaskPanel(task));
            }
        }
        
        tasksPanel.revalidate();
        tasksPanel.repaint();
    }
    
    private JPanel createTasksPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
}
