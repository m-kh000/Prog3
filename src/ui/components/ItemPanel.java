package ui.components;
import javax.swing.*;
import java.awt.*;

public class ItemPanel extends JPanel {
    public ItemPanel(core.Item item) {
        setLayout(new GridLayout(1, 5, 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JLabel idLabel = new JLabel("ID: " + item.getId());
        add(idLabel);
        
        ImageIcon icon = new ImageIcon("item.png");
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(img));
        add(iconLabel);
        
        JLabel nameLabel = new JLabel(item.getName().toString());
        add(nameLabel);
        
        JLabel quantityLabel = new JLabel("Qty: " + item.getQuantityAvailable());
        quantityLabel.setForeground(item.isUnderMin() ? Color.RED : Color.GREEN);
        add(quantityLabel);
        
        JLabel priceLabel = new JLabel("$" + item.getPrice());
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setForeground(Color.GRAY);
        add(priceLabel);
    }
}
