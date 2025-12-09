package ui.components;
import java.awt.*;
import javax.swing.*;

public class TaskPanel extends JPanel {
    public TaskPanel(core.Task task) {
        setLayout(new GridLayout(1, 5, 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JLabel idLabel = new JLabel("ID: " + task.getId());
        add(idLabel);
        
        ImageIcon icon = new ImageIcon("task.png");
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(img));
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
