package ui.components;
import javax.swing.*;
import java.awt.*;

public class ProductPanel extends JPanel {
    public ProductPanel(core.Product product) {
        setLayout(new GridLayout(1, 3, 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JLabel idLabel = new JLabel("ID: " + product.getId());
        add(idLabel);
        
        ImageIcon icon = new ImageIcon("prod.png");
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(img));
        add(iconLabel);
        
        JLabel nameLabel = new JLabel(product.getName());
        add(nameLabel);
    }
}
