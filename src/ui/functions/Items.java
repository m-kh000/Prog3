package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;

public class Items extends FunctionPanel {
    public Items(JFrame frame, Factory factory) {
        setLayout(new GridLayout(6, 1, 20, 20));
        Color buttonColor = Color.decode("#5294ff");
        
        add(createBackButton(frame, factory, "supervisor"));
        
        JLabel title = new JLabel("Items");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        
        JButton addItemBtn = createStyledButton("Add Item", buttonColor);
        addItemBtn.addActionListener(e -> switchPanel(frame, new AddItem(frame, factory)));
        add(addItemBtn);
        
        JButton viewItemsBtn = createStyledButton("View Items", buttonColor);
        viewItemsBtn.addActionListener(e -> switchPanel(frame, new ViewItems(frame, factory)));
        add(viewItemsBtn);
        
        JButton modifyItemBtn = createStyledButton("Modify Item", buttonColor);
        modifyItemBtn.addActionListener(e -> switchPanel(frame, new ModifyItem(frame, factory)));
        add(modifyItemBtn);
        
        JButton deleteItemBtn = createStyledButton("Delete Item", buttonColor);
        deleteItemBtn.addActionListener(e -> switchPanel(frame, new DeleteItem(frame, factory)));
        add(deleteItemBtn);
    }
    
    private void switchPanel(JFrame frame, JPanel panel) {
frame.getContentPane().remove(this);
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }
}
