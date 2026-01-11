package ui.functions;
import core.Factory;
import exceptions.EmptyFieldException;
import java.awt.*;
import javax.swing.*;

import ui.FunctionPanel;
import ui.Manager;
import utils.FileUtils;

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
        JComboBox<String> taskCombo = new JComboBox<>(Factory.getCompletedTasksNames());
        taskCombo.setFont(Manager.defaultFont(false, false));
        taskCombo.setSelectedItem(null);
        
        JButton deliverBtn = new JButton("Deliver Task");
        deliverBtn.setFont(Manager.defaultFont(true, true));
        deliverBtn.setBackground(Color.GREEN);
        deliverBtn.setForeground(Color.WHITE);        
        deliverBtn.setFocusPainted(false);
        deliverBtn.setOpaque(true);

        // Listeners
        // Deliver button click
        deliverBtn.addActionListener(e -> {
            try {
            if (taskCombo.getSelectedItem() == null) {
                throw new EmptyFieldException();
            }
            //  TODO: use Factory.deliverTask(pl, taskID);
            //  WARNING: the method you are currently using is deprecated and will be removed soon!!!
            Factory.deliverTask((String)taskCombo.getSelectedItem());
            taskCombo.removeAllItems();
            for(String task : Factory.getCompletedTasksNames()) {
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