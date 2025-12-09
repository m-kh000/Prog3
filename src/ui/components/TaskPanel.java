package ui.components;
import javax.swing.*;
import java.awt.*;

public class TaskPanel extends JPanel {
    public TaskPanel(core.Task task) {
        setLayout(new GridLayout(1, 5, 10, 0));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JLabel idLabel = new JLabel("ID: " + task.getId());
        add(idLabel);
        
        JLabel iconLabel = new JLabel(new ImageIcon("tsk.png"));
        add(iconLabel);
        
        JLabel productLabel = new JLabel(task.getProduct().getName());
        add(productLabel);
        
        JLabel quantityLabel = new JLabel("Req: " + task.getRequiredQuantity());
        add(quantityLabel);
        
        boolean isCompleted = task.getReady() >= task.getRequiredQuantity();
        JLabel readyLabel = new JLabel(isCompleted ? "Ready" : "Not Ready");
        readyLabel.setForeground(isCompleted ? Color.GREEN : Color.RED);
        add(readyLabel);
    }
}
