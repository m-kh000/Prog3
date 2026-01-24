package ui.functions;
import core.Factory;
import exceptions.EmptyFieldException;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

import ui.FunctionPanel;
import ui.Manager;
import utils.FileUtils;
import core.ProductLine;

public class DeliverTask extends FunctionPanel {
    public DeliverTask(JPanel centerPanel, JFrame frame) {
        
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
        JLabel selectLabel = new JLabel("Select a completed Task to deliver:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        HashMap<String, ProductLine> completeTasksHashMap = Factory.getCompletedTasksNames_ids_pls();
        JComboBox<String> taskCombo = new JComboBox<>(completeTasksHashMap.keySet().toArray(new String[0]));
        taskCombo.setFont(Manager.defaultFont(false, false));
        taskCombo.setSelectedItem(null);
        
        JButton deliverBtn = new JButton("Deliver Task");
        deliverBtn.setFont(Manager.defaultFont(true, true));
        deliverBtn.setBackground(Color.GREEN);
        deliverBtn.setForeground(Color.WHITE);        
        deliverBtn.setFocusPainted(false);
        deliverBtn.setOpaque(true);

        // Listeners
        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "deliver");
        getActionMap().put("deliver", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                deliverBtn.doClick();
            }
        });

        // Deliver button click
        deliverBtn.addActionListener(e -> {
            try {
            if (taskCombo.getSelectedItem() == null) {
                throw new EmptyFieldException();
            }
            String taskName = (String) taskCombo.getSelectedItem();
            int taskId = Integer.parseInt(taskName.substring(1, taskName.indexOf(" ")));
            ProductLine pl = completeTasksHashMap.get(taskName);
            Factory.deliverTask(pl, taskId);
            taskCombo.removeAllItems();
            for(String task : Factory.getCompletedTasksNames_ids_pls().keySet()) {
                taskCombo.addItem(task);
            }
            taskCombo.setSelectedItem(null);
            Manager.isEdited = true;
            JOptionPane.showMessageDialog(null, "Task delivered successfully");
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            FileUtils.log(ex);
        }
        });

        // Layout setup
        selectPanel.add(selectLabel);
        selectPanel.add(taskCombo);
        
        mainPanel.add(createTopPanel("Deliver Tasks", centerPanel, frame, "supervisor"));
        mainPanel.add(selectPanel);
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(deliverBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
}