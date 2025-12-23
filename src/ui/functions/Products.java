package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.Manager;
import ui.UI;

public class Products extends FunctionPanel {
    public Products(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(6, 1, 20, 20));
        Color buttonColor = Color.decode("#5294ff");
        
        add(createTopPanel("Products", centerPanel, frame, factory, "supervisor"));
        
        JButton viewProductsBtn = createStyledButton("View All Products", buttonColor);
        viewProductsBtn.addActionListener(e -> UI.switchContent(new ViewProducts(centerPanel, frame, factory)));
        add(viewProductsBtn);
        
        JButton filterProductsBtn = createStyledButton("Add Product", buttonColor);
        filterProductsBtn.addActionListener(e -> UI.switchContent(new AddProduct(centerPanel, frame, factory)));
        add(filterProductsBtn);
        
        add(new JPanel()); // Empty panel
        add(new JPanel()); // Empty panel
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