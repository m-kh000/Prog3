package ui.functions;

import exceptions.EmptyFieldException;
import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class ModifyStatusOfAProductionLine extends FunctionPanel {

    public ModifyStatusOfAProductionLine(JPanel centerPanel, JFrame frame, core.Factory factory) {
        // Main grid: 8 rows, 1 column
        setLayout(new GridLayout(8, 1, 10, 10));
        
        // Row 1: Top panel
        add(createTopPanel("Modify Status of a Production Line:", centerPanel, frame, factory, "manager"));
        
        // Row 2: Select production line
        JPanel linepanel = new JPanel(new GridLayout(1, 2));
        JLabel lineLabel = new JLabel("Select Production Line:");
        JComboBox<String> lineComboBox = new JComboBox<>(factory.getProductLineNames());
        linepanel.add(lineLabel);
        linepanel.add(lineComboBox);
        add(linepanel);

        // Row 3: Select new status
        JPanel statuspanel = new JPanel(new GridLayout(1, 2));
        JLabel statuslabel = new JLabel("New Status:");
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] { "Active","Maintenance","Stopped" });
        statuspanel.add(statuslabel);
        statuspanel.add(statusComboBox);
        add(statuspanel);

        // Rows 4-7: Empty panels
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());

        // Row 8: Submit button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        add(submitBtn);

        submitBtn.addActionListener(e -> {
            try {
                // Validate selections
                if (lineComboBox.getSelectedItem() == null || statusComboBox.getSelectedItem() == null) {
                    throw new EmptyFieldException();
                }
                
                // Get selected values and modify status
                String selectedLineName = (String)lineComboBox.getSelectedItem();
                String selectedStatus = (String)statusComboBox.getSelectedItem();
                factory.modifyStatus(selectedLineName, selectedStatus);
                
                // Reset selections
                lineComboBox.setSelectedIndex(0);
                statusComboBox.setSelectedIndex(0);
                
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Status modified successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }
}
