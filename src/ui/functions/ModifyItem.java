package ui.functions;

import javax.swing.*;
import java.awt.*;
import ui.LabelBox;
import core.Factory;

public class ModifyItem extends FunctionPanel {

    public ModifyItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(9, 1, 10, 10));

        add(BackBtn(centerPanel, frame, factory, "supervisor"));

        JLabel title = new JLabel("Modify Item");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);

        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Item:");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        JComboBox<String> itemCombo = new JComboBox<>(new String[]{"Item 1", "Item 2", "Item 3"});
        itemCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        selectPanel.add(selectLabel);
        selectPanel.add(itemCombo);
        add(selectPanel);

        add(new LabelBox("Name:", false));
        add(new LabelBox("Category:", false));
        add(new LabelBox("Price:", false));
        add(new LabelBox("Quantity:", false));
        add(new LabelBox("Min Quantity:", false));

        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 20));
        add(updateBtn);
    }
}
