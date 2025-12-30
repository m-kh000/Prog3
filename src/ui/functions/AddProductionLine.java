package ui.functions;

import core.Factory;
import core.ProductLine;
import java.awt.*;
import javax.swing.*;
import ui.LabelBox;
import ui.Manager;

public class AddProductionLine extends FunctionPanel {

    public AddProductionLine(JPanel centerPanel, JFrame frame, Factory factory) {
        // Main grid: 8 rows, 1 column
        setLayout(new GridLayout(8, 1, 10, 10));

        // Row 1: Top panel
        add(createTopPanel("Add Product Line:", centerPanel, frame, factory, "manager"));

        // Row 2: Type name
        LabelBox name = new LabelBox("Name:");
        add(name);

        // Row 3: Select status
        JPanel statuspanel = new JPanel(new GridLayout(1, 2));
        JLabel statuslabel = new JLabel("Status:");
        statuslabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> status = new JComboBox<>(new String[] { "Active","Maintenance","Stopped" });
        statuspanel.add(statuslabel);
        statuspanel.add(status);
        add(statuspanel);

        // Row 4: Type priority
        LabelBox priority = new LabelBox("Priority:");
        add(priority);

        // Rows 5-7: Empty panels
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());

        // Row 8: Submit button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        add(submitBtn);
        
        // Add Enter key functionality
        priority.getTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    submitBtn.doClick();
                }
            }
        });

        submitBtn.addActionListener(e -> {
            if (name.isEmpty() || priority.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
                return;
            }
            String nameText = name.getText();
            String statusText = (String)status.getSelectedItem();
            String priorityText = priority.getText();
            try {
                factory.add(new ProductLine(nameText, statusText, Integer.parseInt(priorityText)));
                name.reset();
                status.setSelectedIndex(0);
                priority.reset();
                Manager.isEdited = true;
                JOptionPane.showMessageDialog(null, "Product line added successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }
}
