package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class CancelTask extends FunctionPanel {
    public CancelTask(JPanel centerPanel, JFrame frame, Factory factory) {
        // Main grid: 8 rows, 1 column
        setLayout(new GridLayout(8, 1, 20, 20));
        
        // Row 1: Top panel
        add(createTopPanel("Cancel Tasks", centerPanel, frame, factory, "supervisor"));
        
        // Row 2: Select task
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Task:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> taskCombo = new JComboBox<>(factory.get0PCTasksNames());
        taskCombo.setFont(Manager.defaultFont(true, false));
        taskCombo.setSelectedItem(null);
        selectPanel.add(selectLabel);
        selectPanel.add(taskCombo);
        add(selectPanel);

        // Rows 3-7: Empty panels
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());
        
        // Row 8: Cancel button
        JButton cancelBtn = new JButton("Cancel Task");
        cancelBtn.setFont(Manager.defaultFont(true, true));
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);        
        cancelBtn.setFocusPainted(false);
        cancelBtn.setOpaque(true);
        add(cancelBtn);
        
        // Add Enter key functionality
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "cancel");
        getActionMap().put("cancel", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cancelBtn.doClick();
            }
        });

        cancelBtn.addActionListener(e -> {
            // Cancel selected task and update combo box
            factory.cancelTask((String)taskCombo.getSelectedItem());
            taskCombo.removeAllItems();
            for(String task : factory.get0PCTasksNames()) {
                taskCombo.addItem(task);
            }
            taskCombo.setSelectedItem(null);
            Manager.isEdited = true;
            JOptionPane.showMessageDialog(null, "Task cancelled successfully");
        });
    }

}
