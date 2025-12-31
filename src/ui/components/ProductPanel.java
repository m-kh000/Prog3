package ui.components;
import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class ProductPanel extends JPanel {
    public ProductPanel(core.Product product) {
        setLayout(new GridLayout(1, 4, 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // ID and icon combined
        ImageIcon icon = new ImageIcon("icons/product.png");
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JPanel idIconPanel = new JPanel();
        idIconPanel.setLayout(new BoxLayout(idIconPanel, BoxLayout.X_AXIS));
        JLabel idValue = new JLabel(String.valueOf(product.getId()) + "  ");
        idValue.setForeground(Color.DARK_GRAY);
        idValue.setFont(Manager.defaultFont(false, false));
        idIconPanel.add(idValue);
        idIconPanel.add(new JLabel(new ImageIcon(img)));
        add(idIconPanel);
        
        // Name
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(Manager.defaultFont(false, false));
        add(nameLabel);
        
        add(new JLabel("" + product.getPurchaseFrequency()));
        // Items count with hint
        JLabel itemsValue = new JLabel(String.valueOf(product.reqItemCount()));
        itemsValue.setForeground(Color.BLUE);
        itemsValue.setFont(Manager.defaultFont(false, false));
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.X_AXIS));
        JLabel itemsHint = new JLabel("Items");
        itemsHint.setForeground(Color.GRAY);
        itemsHint.setFont(Manager.hintFont());
        itemsPanel.add(itemsValue);
        itemsPanel.add(itemsHint);
        add(itemsPanel);
    }
}
