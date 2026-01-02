package ui.functions;
import core.Factory;
import exceptions.EmptyFieldException;
import java.awt.*;
import javax.swing.*;
import ui.Manager;
import utils.FileUtils;

public class DeliverTask extends FunctionPanel {
    public DeliverTask(JPanel centerPanel, JFrame frame, Factory factory) {
        // Main grid: 8 rows, 1 column
        setLayout(new GridLayout(8, 1, 20, 20));
        
        // Row 1: Top panel
        add(createTopPanel("Deliver Tasks", centerPanel, frame, factory, "supervisor"));
        
        // Row 2: Select task
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select a completed Task to deliver:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> taskCombo = new JComboBox<>(factory.getCompletedTasksNames());
        taskCombo.setFont(Manager.defaultFont(false, false));
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
        JButton cancelBtn = new JButton("Deliver Task");
        cancelBtn.setFont(Manager.defaultFont(true, true));
        cancelBtn.setBackground(Color.GREEN);
        cancelBtn.setForeground(Color.WHITE);        
        cancelBtn.setFocusPainted(false);
        cancelBtn.setOpaque(true);
        add(cancelBtn);

        cancelBtn.addActionListener(e -> {
            try{
            if (taskCombo.getSelectedItem() == null) {
                throw new EmptyFieldException();
            }
            // Cancel selected task and update combo box
            factory.deliverTask((String)taskCombo.getSelectedItem());
            taskCombo.removeAllItems();
            for(String task : factory.getCompletedTasksNames()) {
                taskCombo.addItem(task);
            }
            taskCombo.setSelectedItem(null);
            Manager.isEdited = true;
            JOptionPane.showMessageDialog(null, "Task delivered successfully");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            FileUtils.log(ex);
        }
        });
    }
}