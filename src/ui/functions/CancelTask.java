package ui.functions;
import core.Factory;
import core.Factory.Task_id_pl;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import ui.FunctionPanel;
import ui.Manager;

public class CancelTask extends FunctionPanel {
    public CancelTask(JPanel centerPanel, JFrame frame) {
        
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        // Components creation
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 20, 20));
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Task:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        HashMap<String, Task_id_pl> completeTasksHashMap = Factory.get0PCTasksNames_ids();
        JComboBox<String> taskCombo = new JComboBox<>(completeTasksHashMap.keySet().toArray(new String[0]));
        taskCombo.setFont(Manager.defaultFont(true, false));
        taskCombo.setSelectedItem(null);
        
        JButton cancelBtn = new JButton("Cancel Task");
        cancelBtn.setFont(Manager.defaultFont(true, true));
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);        
        cancelBtn.setFocusPainted(false);
        cancelBtn.setOpaque(true);

        // Listeners
        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "cancel");
        getActionMap().put("cancel", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cancelBtn.doClick();
            }
        });

        // Cancel button click
        cancelBtn.addActionListener(e -> {
            int taskId = completeTasksHashMap.get((String)taskCombo.getSelectedItem()).taskId;
            Factory.cancelTask(taskId);
            taskCombo.removeAllItems();
            for(String task : Factory.get0PCTasksNames()) {
                taskCombo.addItem(task);
            }
            taskCombo.setSelectedItem(null);
            Manager.isEdited = true;
            JOptionPane.showMessageDialog(null, "Task cancelled successfully");
        });

        // Layout setup
        selectPanel.add(selectLabel);
        selectPanel.add(taskCombo);
        
        mainPanel.add(createTopPanel("Cancel Tasks", centerPanel, frame, "supervisor"));
        mainPanel.add(selectPanel);
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(cancelBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
}