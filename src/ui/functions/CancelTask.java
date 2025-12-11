package ui.functions;
import javax.swing.*;
import java.awt.*;
import ui.Manager;
import core.Factory;

public class CancelTask extends FunctionPanel {
    public CancelTask(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("Cancel Task", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        
        JPanel boxes = new JPanel(new GridLayout(3, 1, 20, 20));
        
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Task:");
        selectLabel.setFont(Manager.defaultFont(false, true));
        JComboBox<String> taskCombo = new JComboBox<>(new String[]{"Task 1", "Task 2", "Task 3"});
        taskCombo.setFont(Manager.defaultFont(false, true));
        selectPanel.add(selectLabel);
        selectPanel.add(taskCombo);
        boxes.add(selectPanel);
        
        JButton cancelBtn = new JButton("Cancel Task");
        cancelBtn.setFont(Manager.defaultFont(true, true));
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        boxes.add(cancelBtn);
        
        add(boxes, BorderLayout.CENTER);
    }
}