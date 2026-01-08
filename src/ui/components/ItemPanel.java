package ui.components;
import core.Item;
import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class ItemPanel extends JPanel {
    Timer refreshTimer;
    JLabel quantityValue;
    Item item;
    public ItemPanel(core.Item item) {
        this.item = item;
        setLayout(new GridLayout(1, 4, 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        Manager.itemAutorefresh = true;
        
        // ID and icon combined
        ImageIcon icon = new ImageIcon("icons/item.png");
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JPanel idIconPanel = new JPanel();
        idIconPanel.setLayout(new BoxLayout(idIconPanel, BoxLayout.X_AXIS));
        JLabel idValue = new JLabel(String.valueOf(item.getId()) + "  ");
        idValue.setForeground(Color.DARK_GRAY);
        idValue.setFont(Manager.defaultFont(false, false));
        idIconPanel.add(idValue);
        idIconPanel.add(new JLabel(new ImageIcon(img)));
        add(idIconPanel);
        
        // Name
        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(Manager.defaultFont(false, false));
        add(nameLabel);
        
        // Quantity with hint
        quantityValue = new JLabel(String.valueOf(item.getQuantityAvailable()));
        quantityValue.setForeground(item.isUnderMin() ? Color.RED : Color.GREEN);
        quantityValue.setFont(Manager.defaultFont(false, false));
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.X_AXIS));
        JLabel quantityHint = new JLabel("Qty");
        quantityHint.setForeground(Color.GRAY);
        quantityHint.setFont(Manager.hintFont());
        quantityPanel.add(quantityValue);
        quantityPanel.add(quantityHint);
        add(quantityPanel);
        
        // Price with hint
        JLabel priceValue = new JLabel("$" + item.getPrice());
        priceValue.setForeground(Color.DARK_GRAY);
        priceValue.setFont(Manager.defaultFont(false, false));
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));
        JLabel priceHint = new JLabel("Price");
        priceHint.setForeground(Color.GRAY);
        priceHint.setFont(Manager.hintFont());
        pricePanel.add(priceValue);
        pricePanel.add(priceHint);
        add(pricePanel);
        
        // Category with hint
        JLabel categoryValue = new JLabel(item.getCategory());
        categoryValue.setForeground(Color.DARK_GRAY);
        categoryValue.setFont(Manager.defaultFont(false, false));
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.X_AXIS));
        JLabel categoryHint = new JLabel("Category");
        categoryHint.setForeground(Color.GRAY);
        categoryHint.setFont(Manager.hintFont());
        categoryPanel.add(categoryValue);
        categoryPanel.add(categoryHint);
        add(categoryPanel);
        // Auto-refresh timer (every 3 seconds)
        refreshTimer = new Timer(3000, e -> refreshTasksPanel());
        refreshTimer.start();

    }

    private void refreshTasksPanel() {
        if (!Manager.itemAutorefresh) {
            refreshTimer.stop();
            return;
        }
        quantityValue.setText(String.valueOf(item.getQuantityAvailable()));
        quantityValue.setForeground(item.isUnderMin() ? Color.RED : Color.GREEN);
        getParent().revalidate();
        getParent().repaint();

    }
}
