package ui.functions;

import core.Factory;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import ui.Manager;
import ui.components.TaskPanel;

public class FilterTasks extends FunctionPanel {

    private JPanel tasksPanel;
    private JComboBox<String> filterField;

    public FilterTasks(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new GridLayout(1, 4));
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(Manager.defaultFont(false, false));
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"ProductLine", "Product", "InProgress", "Completed"});
        filterCombo.setFont(Manager.defaultFont(false, false));
        filterField = new JComboBox<>();
        filterField.setFont(Manager.defaultFont(false, false));
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(Manager.defaultFont(true, false));

        filterCombo.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();

            filterField.removeAllItems();
            if (filterType.equals("ProductLine")) {
                String[] productLineNames = factory.getProductLineNames();
                for (String name : productLineNames) {
                    filterField.addItem(name);
                }
                filterField.setEnabled(true);
            } else if (filterType.equals("Product")) {
                String[] productNames = factory.getProductNames();
                for (String name : productNames) {
                    filterField.addItem(name);
                }
                filterField.setEnabled(true);
            } else {
                filterField.setEnabled(false);
            }
        });

        filterBtn.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            String filterValue = (String) filterField.getSelectedItem();
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
        if (filterType.equals("ProductLine") || filterType.equals("Product")) {
            //tasks = factory.filterTasksByProduct(filterValue);
        } else if (filterType.equals("InProgress")) {
            tasks = Factory.filterTasksByInprogress();
        } else if (filterType.equals("Completed")) {
            tasks = Factory.filterTasksByCompleted();
        }

        if (tasks != null) {
            for (core.Task task : tasks) {
                tasksPanel.add(new TaskPanel(task));
            }
        }
        else {
            tasksPanel.add(new JLabel("No tasks found."));
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
