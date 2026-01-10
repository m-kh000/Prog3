package ui.functions;
import core.Factory;
import exceptions.EmptyFieldException;
import java.awt.*;
import javax.swing.*;

import ui.FunctionPanel;
import ui.Manager;
import utils.FileUtils;

// ModifyStatusOfAProductionLine panel for updating production line status
public class ModifyStatusOfAProductionLine extends FunctionPanel {

    public ModifyStatusOfAProductionLine(JPanel centerPanel, JFrame frame) {
        setLayout(new GridLayout(8, 1, 10, 10));
        
        // Components
        JPanel linePanel = new JPanel(new GridLayout(1, 2));
        JLabel lineLabel = new JLabel("Select Production Line:");
        lineLabel.setFont(Manager.defaultFont(false, false));   
        JComboBox<String> lineComboBox = new JComboBox<>(Factory.getProductLineNames());
        lineComboBox.setSelectedItem(null);
        
        JPanel statusPanel = new JPanel(new GridLayout(1, 2));
        JLabel statusLabel = new JLabel("New Status:");
        statusLabel.setFont(Manager.defaultFont(false, false));
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] { "Active","Maintenance","Stopped" });
        statusComboBox.setEnabled(false);;
        
        JLabel hint = new JLabel();
        hint.setFont(Manager.defaultFont(false, false));
        hint.setForeground(Color.GRAY);
        
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));

        // Listeners
        // Line selection listener - update hint
        lineComboBox.addActionListener(e -> {
            String selectedLineName = (String)lineComboBox.getSelectedItem();
            statusComboBox.setEnabled(true);
            if (selectedLineName != null) {
                hint.setText("Current Status: " + Factory.getProductLine(selectedLineName).getLineStatus());
                revalidate();
                repaint();
            }
        });

        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "filter");
        getActionMap().put("filter", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                submitBtn.doClick();
            }
        });

        // Submit button listener
        submitBtn.addActionListener(e -> {
            try {
                if (lineComboBox.getSelectedItem() == null || statusComboBox.getSelectedItem() == null) {
                    throw new EmptyFieldException();
                }
                
                String selectedLineName = (String)lineComboBox.getSelectedItem();
                String selectedStatus = (String)statusComboBox.getSelectedItem();
                Factory.modifyStatus(selectedLineName, selectedStatus);
                
                lineComboBox.setSelectedItem(null);
                statusComboBox.setEnabled(false);
                hint.setText("");
                
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Status modified successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Error while modifying productlines: " + ex.getMessage());
                FileUtils.log(ex);
            }
        });
        
        // Layout setup
        add(createTopPanel("Modify Status of a Production Line:", centerPanel, frame, "manager"));
        
        linePanel.add(lineLabel);
        linePanel.add(lineComboBox);
        add(linePanel);
        
        statusPanel.add(statusLabel);
        statusPanel.add(statusComboBox);
        add(statusPanel);
        
        add(hint);
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());
        add(submitBtn);
    }
}
