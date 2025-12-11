package ui.functions;

import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.Manager;
import ui.UI;

public class Items extends FunctionPanel {

    public Items(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new GridLayout(6, 1, 20, 20));
        Color buttonColor = Color.decode("#5294ff");

        add(createTopPanel("Items", centerPanel, frame, factory, "supervisor"));


        JButton addItemBtn = createStyledButton("Add Item", buttonColor);
        addItemBtn.addActionListener(e -> UI.switchContent(new AddItem(centerPanel, frame, factory)));
        add(addItemBtn);

        JButton viewItemsBtn = createStyledButton("View Items", buttonColor);
        viewItemsBtn.addActionListener(e -> UI.switchContent(new ViewItems(centerPanel, frame, factory)));
        add(viewItemsBtn);

        JButton modifyItemBtn = createStyledButton("Modify Item", buttonColor);
        modifyItemBtn.addActionListener(e -> UI.switchContent(new ModifyItem(centerPanel, frame, factory)));
        add(modifyItemBtn);

        JButton deleteItemBtn = createStyledButton("Delete Item", buttonColor);
        deleteItemBtn.addActionListener(e -> UI.switchContent(new DeleteItem(centerPanel, frame, factory)));
        add(deleteItemBtn);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(Manager.defaultFont(true, false));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }
}
