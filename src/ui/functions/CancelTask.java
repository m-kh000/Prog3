package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class CancelTask extends FunctionPanel {
    public CancelTask(JPanel centerPanel, JFrame frame) {
        
        setLayout(new GridLayout(8, 1, 20, 20));
        
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
        JComboBox<String> taskCombo = new JComboBox<>(Factory.get0PCTasksNames());
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
            Factory.cancelTask((String)taskCombo.getSelectedItem());
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