package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class CancelTask extends FunctionPanel {
    public CancelTask(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("Cancel Tasks", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        
        JPanel boxes = new JPanel(new GridLayout(7, 1, 20, 20));

        JPanel emp = new JPanel();
        JPanel emp2 = new JPanel();
        JPanel emp3 = new JPanel();
        JPanel emp4 = new JPanel();
        boxes.add(emp);
        
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
            JLabel selectLabel = new JLabel("Select Task:");
            selectLabel.setFont(Manager.defaultFont(false, false));
            JComboBox<String> taskCombo = new JComboBox<>(factory.getTasksNames());
            taskCombo.setFont(Manager.defaultFont(false, false));
            taskCombo.setSelectedItem(null);
            selectPanel.add(selectLabel);
            selectPanel.add(taskCombo);
            boxes.add(selectPanel);

        boxes.add(emp2);
        boxes.add(emp3);
        boxes.add(emp4);
        JButton cancelBtn = new JButton("Cancel Task");
        cancelBtn.setFont(Manager.defaultFont(true, true));
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);        
        cancelBtn.setFocusPainted(false);
        cancelBtn.setOpaque(true);
        
        boxes.add(cancelBtn);

        cancelBtn.addActionListener(e -> {
            factory.cancelTask((String)taskCombo.getSelectedItem());
            taskCombo.removeAllItems();
            for(String task : factory.getTasksNames()) {
                taskCombo.addItem(task);
            }
            taskCombo.setSelectedItem(null);
            JOptionPane.showMessageDialog(null, "Task cancelled successfully");
        });
        add(boxes);
    }
}