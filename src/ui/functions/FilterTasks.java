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

    public FilterTasks(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());

        // Components creation
        JPanel filterPanel = new JPanel(new GridLayout(1, 4));
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"ProductLine", "Product", "InProgress", "Completed"});
        filterCombo.setSelectedItem(null);
        filterCombo.setFont(Manager.defaultFont(false, false));
        filterField = new JComboBox<>();
        filterField.setEnabled(false);
        filterField.setFont(Manager.defaultFont(false, false));
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(Manager.defaultFont(true, false));

        // Listeners
        // Filter combo selection changes
        filterCombo.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();

            filterField.removeAllItems();
            if (filterType.equals("ProductLine")) {
                String[] productLineNames = Factory.getProductLineNames();
                for (String name : productLineNames) {
                    filterField.addItem(name);
                }
                filterField.setEnabled(true);
                filterField.revalidate();
                filterField.repaint();

            } else if (filterType.equals("Product")) {
                String[] productNames = Factory.getProductNames();
                for (String name : productNames) {
                    filterField.addItem(name);
                }
                filterField.setEnabled(true);
                filterField.revalidate();
                filterField.repaint();
            } else {
                filterField.setSelectedItem(null);
                filterField.setEnabled(false);
            }
        });

        // Filter button click
        filterBtn.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            String filterValue = (String) filterField.getSelectedItem();
            updateTasksPanel(filterType, filterValue);
        });

        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "filter");
        getActionMap().put("filter", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                filterBtn.doClick();
            }
        });

        // Layout setup
        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter Tasks", centerPanel, frame, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        tasksPanel = createTasksPanel();
        // Load all tasks initially
        for (core.Task task : Factory.previewTasks()) {
            tasksPanel.add(new TaskPanel(task));
            tasksPanel.add(Box.createVerticalStrut(5));
        }
        add(new JScrollPane(tasksPanel), BorderLayout.CENTER);
    }

    // Update tasks panel with filtered results
    private void updateTasksPanel(String filterType, String filterValue) {
        tasksPanel.removeAll();

        List<core.Task> tasks = null;
        if (filterType.equals("Product")) {
            tasks = Factory.filterTasksByProduct(filterValue);
        } else if (filterType.equals("ProductLine")) {
            tasks = Factory.filterTasksByProductLine(filterValue);
        } else if (filterType.equals("InProgress")) {
            tasks = Factory.filterTasksByInprogress();
        } else if (filterType.equals("Completed")) {
            tasks = Factory.filterTasksByCompleted();
        }

        if (tasks != null || !tasks.isEmpty()) {
            for (core.Task task : tasks) {
                tasksPanel.add(new TaskPanel(task));
                tasksPanel.add(Box.createVerticalStrut(5));
            }
        } else {
            tasksPanel.add(new JLabel("No tasks found."));
        }

        tasksPanel.revalidate();
        tasksPanel.repaint();
    }

    // Create tasks panel with vertical layout
    private JPanel createTasksPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
}
