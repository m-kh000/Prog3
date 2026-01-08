package ui.components;

import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class TaskPanel extends JPanel {

    JLabel readyValue;
    JLabel statusValue;
    core.Task task;
    Timer refreshTimer;

    public TaskPanel(core.Task task) {
        this.task = task;
        setLayout(new GridLayout(1, 5, 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        Manager.taskAutorefresh = true;

        // ID and icon combined
        ImageIcon icon = new ImageIcon("icons/task.png");
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JPanel idIconPanel = new JPanel();
        idIconPanel.setLayout(new BoxLayout(idIconPanel, BoxLayout.X_AXIS));
        JLabel idValue = new JLabel(String.valueOf(task.getId()) + "  ");
        idValue.setForeground(Color.DARK_GRAY);
        idValue.setFont(Manager.defaultFont(false, false));
        idIconPanel.add(idValue);
        idIconPanel.add(new JLabel(new ImageIcon(img)));
        add(idIconPanel);

        // Product name
        JLabel nameLabel = new JLabel(task.getProduct().getName());
        nameLabel.setFont(Manager.defaultFont(false, false));
        add(nameLabel);

        // Required quantity with hint
        JLabel quantityValue = new JLabel(String.valueOf(task.getRequiredQuantity()));
        quantityValue.setForeground(Color.BLUE);
        quantityValue.setFont(Manager.defaultFont(false, true, ""));
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.X_AXIS));
        JLabel quantityHint = new JLabel("Required");
        quantityHint.setForeground(Color.GRAY);
        quantityHint.setFont(Manager.hintFont());
        quantityPanel.add(quantityValue);
        quantityPanel.add(quantityHint);
        add(quantityPanel);

        // Ready quantity with hint
        readyValue = new JLabel(String.valueOf(task.getReady()));
        readyValue.setForeground(Color.ORANGE);
        readyValue.setFont(Manager.defaultFont(false, true, ""));
        JPanel readyPanel = new JPanel();
        readyPanel.setLayout(new BoxLayout(readyPanel, BoxLayout.X_AXIS));
        JLabel readyHint = new JLabel("Ready");
        readyHint.setForeground(Color.GRAY);
        readyHint.setFont(Manager.hintFont());
        readyPanel.add(readyValue);
        readyPanel.add(readyHint);
        add(readyPanel);

        // Status with hint
        boolean isCompleted = task.getReady() >= task.getRequiredQuantity();
        statusValue = new JLabel(isCompleted ? "Completed" : "Pending");
        statusValue.setForeground(isCompleted ? Color.GREEN : Color.RED);
        statusValue.setFont(Manager.defaultFont(false, false));
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        JLabel statusHint = new JLabel(" Status");
        statusHint.setForeground(Color.GRAY);
        statusHint.setFont(Manager.hintFont());
        statusPanel.add(statusValue);
        statusPanel.add(statusHint);
        add(statusPanel);

        // Auto-refresh timer (every 2 seconds)
        refreshTimer = new Timer(2000, e -> refreshTasksPanel());
        refreshTimer.start();

    }

    private void refreshTasksPanel() {
        if (!Manager.taskAutorefresh) {
            refreshTimer.stop();
            return;
        }

        readyValue.setText(String.valueOf(task.getReady()));
        boolean isCompleted = task.getReady() >= task.getRequiredQuantity();
        statusValue.setText(isCompleted ? "Completed" : "Pending");
        statusValue.setForeground(isCompleted ? Color.GREEN : Color.RED);
        getParent().revalidate();
        getParent().repaint();
        System.out.println("refresh");

    }
}
