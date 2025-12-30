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

        //Row 1 view
        JButton viewItemsBtn = createStyledButton("View Items", buttonColor);
        viewItemsBtn.addActionListener(e -> UI.switchContent(new ViewItems(centerPanel, frame, factory)));
        add(viewItemsBtn);

        //Row 2 view
        JButton addItemBtn = createStyledButton("Add Item", buttonColor);
        addItemBtn.addActionListener(e -> UI.switchContent(new AddItem(centerPanel, frame, factory)));
        add(addItemBtn);

        //Row 3 modify
        JButton modifyItemBtn = createStyledButton("Modify Item", buttonColor);
        modifyItemBtn.addActionListener(e -> UI.switchContent(new ModifyItem(centerPanel, frame, factory)));
        add(modifyItemBtn);

        //Row 4 delete
        JButton deleteItemBtn = createStyledButton("Delete Item", buttonColor);
        deleteItemBtn.addActionListener(e -> UI.switchContent(new DeleteItem(centerPanel, frame, factory)));
        add(deleteItemBtn);
    }

}
