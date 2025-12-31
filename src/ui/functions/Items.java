package ui.functions;

import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.UI;

public class Items extends FunctionPanel {

    public Items(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 20, 20));
        Color buttonColor = Color.decode("#5294ff");

        mainPanel.add(createTopPanel("Items", centerPanel, frame, factory, "supervisor"));

        //Row 1 view
        JButton viewItemsBtn = createStyledButton("View Items", buttonColor);
        viewItemsBtn.addActionListener(e -> UI.switchContent(new ViewItems(centerPanel, frame, factory)));
        mainPanel.add(viewItemsBtn);

        //Row 2 restock
        JButton restockBtn = createStyledButton("Restock Items", buttonColor);
        restockBtn.addActionListener(e -> UI.switchContent(new Restock(centerPanel, frame, factory)));
        mainPanel.add(restockBtn);

        //Row 2 view
        JButton addItemBtn = createStyledButton("Add Item", buttonColor);
        addItemBtn.addActionListener(e -> UI.switchContent(new AddItem(centerPanel, frame, factory)));
        mainPanel.add(addItemBtn);

        //Row 3 modify
        JButton modifyItemBtn = createStyledButton("Modify Item", buttonColor);
        modifyItemBtn.addActionListener(e -> UI.switchContent(new ModifyItem(centerPanel, frame, factory)));
        mainPanel.add(modifyItemBtn);

        //Row 4 delete
        JButton deleteItemBtn = createStyledButton("Delete Item", buttonColor);
        deleteItemBtn.addActionListener(e -> UI.switchContent(new DeleteItem(centerPanel, frame, factory)));
        mainPanel.add(deleteItemBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }

}
