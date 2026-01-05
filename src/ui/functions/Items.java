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
        
        // Components creation
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 20, 20));
        Color buttonColor = Color.decode("#5294ff");

        JButton viewItemsBtn = createStyledButton("View Items", buttonColor);
        JButton restockBtn = createStyledButton("Restock Items", buttonColor);
        JButton addItemBtn = createStyledButton("Add Item", buttonColor);
        JButton modifyItemBtn = createStyledButton("Modify Item", buttonColor);
        JButton deleteItemBtn = createStyledButton("Delete Item", buttonColor);

        // Listeners
        // View items button click
        viewItemsBtn.addActionListener(e -> UI.switchContent(new ViewItems(centerPanel, frame, factory)));
        
        // Restock button click
        restockBtn.addActionListener(e -> UI.switchContent(new Restock(centerPanel, frame, factory)));
        
        // Add item button click
        addItemBtn.addActionListener(e -> UI.switchContent(new AddItem(centerPanel, frame, factory)));
        
        // Modify item button click
        modifyItemBtn.addActionListener(e -> UI.switchContent(new ModifyItem(centerPanel, frame, factory)));
        
        // Delete item button click
        deleteItemBtn.addActionListener(e -> UI.switchContent(new DeleteItem(centerPanel, frame, factory)));

        // Layout setup
        mainPanel.add(createTopPanel("Items", centerPanel, frame, factory, "supervisor"));
        mainPanel.add(viewItemsBtn);
        mainPanel.add(restockBtn);
        mainPanel.add(addItemBtn);
        mainPanel.add(modifyItemBtn);
        mainPanel.add(deleteItemBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
}