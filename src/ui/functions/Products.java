package ui.functions;
import java.awt.*;
import javax.swing.*;
import ui.UI;

public class Products extends FunctionPanel {
    public Products(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(6, 1, 20, 20));
        Color buttonColor = Color.decode("#5294ff");
        
        add(createTopPanel("Products", centerPanel, frame, factory, "supervisor"));
        
        //Row 1 view
        JButton viewProductsBtn = createStyledButton("View All Products", buttonColor);
        viewProductsBtn.addActionListener(e -> UI.switchContent(new ViewProducts(centerPanel, frame, factory)));
        add(viewProductsBtn);
        
        //Row 2 view
        JButton addProductBtn = createStyledButton("Add Product", buttonColor);
        addProductBtn.addActionListener(e -> UI.switchContent(new AddProduct(centerPanel, frame, factory)));
        add(addProductBtn);
        
    }
    
}