package ui.functions;

import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class DeleteItem extends FunctionPanel {

    public DeleteItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("Delete Items", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        
        JPanel boxes = new JPanel(new GridLayout(7, 1, 20, 20));

        JPanel emp = new JPanel();
        JPanel emp2 = new JPanel();
        JPanel emp3 = new JPanel();
        JPanel emp4 = new JPanel();
        boxes.add(emp);
        
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
            JLabel selectLabel = new JLabel("Select Item:");
            selectLabel.setFont(Manager.defaultFont(false, false));
            JComboBox<String> itemCombo = new JComboBox<>(factory.getItemsNames());
            itemCombo.setFont(Manager.defaultFont(false, false));
            itemCombo.setSelectedItem(null);
            selectPanel.add(selectLabel);
            selectPanel.add(itemCombo);
            boxes.add(selectPanel);

        boxes.add(emp2);
        boxes.add(emp3);
        boxes.add(emp4);
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setFont(Manager.defaultFont(true, true));
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setOpaque(true);
        boxes.add(deleteBtn);

        deleteBtn.addActionListener(e -> {
            factory.deleteItem((String)itemCombo.getSelectedItem());
            itemCombo.removeAllItems();
            for(String item : factory.getItemsNames()) {
                itemCombo.addItem(item);
            }
            itemCombo.setSelectedItem(null);
            JOptionPane.showMessageDialog(null, "Item deleted successfully");
        });
        add(boxes);
    }
}
