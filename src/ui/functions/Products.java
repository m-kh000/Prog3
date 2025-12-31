package ui.functions;
import java.awt.*;
import javax.swing.*;
import ui.UI;

public class Products extends FunctionPanel {
    public Products(JPanel centerPanel, JFrame frame, core.Factory factory) {
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
        
        mainPanel.add(createTopPanel("Products", centerPanel, frame, factory, "supervisor"));
        
        //Row 1 view
        JButton viewProductsBtn = createStyledButton("View All Products", buttonColor);
        viewProductsBtn.addActionListener(e -> UI.switchContent(new ViewProducts(centerPanel, frame, factory)));
        mainPanel.add(viewProductsBtn);
        
        //Row 2 view
        JButton addProductBtn = createStyledButton("Add Product", buttonColor);
        addProductBtn.addActionListener(e -> UI.switchContent(new AddProduct(centerPanel, frame, factory)));
        mainPanel.add(addProductBtn);
        
        add(mainPanel, BorderLayout.CENTER);
        
    }
    
}