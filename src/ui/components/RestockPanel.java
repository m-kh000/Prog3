package ui.components;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class RestockPanel extends JPanel {
    private JTextField quantityField;
    
    public RestockPanel(core.Item item, Factory factory) {
        setLayout(new GridLayout(1, 6, 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
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
        JLabel quantityValue = new JLabel(String.valueOf(item.getQuantityAvailable()));
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

        // Min quantity with hint
        JLabel minValue = new JLabel(String.valueOf(item.getMinQuantity()));
        minValue.setForeground(Color.GRAY);
        minValue.setFont(Manager.defaultFont(false, false));
        JPanel minPanel = new JPanel();
        minPanel.setLayout(new BoxLayout(minPanel, BoxLayout.X_AXIS));
        JLabel minHint = new JLabel("Min");
        minHint.setForeground(Color.GRAY);
        minHint.setFont(Manager.hintFont());
        minPanel.add(minValue);
        minPanel.add(minHint);
        add(minPanel);
        
        // Add quantity input
        quantityField = new JTextField("0");
        quantityField.setFont(Manager.defaultFont(false, false));
        add(quantityField);
        
        // Submit button
        JButton submitBtn = new CustomBtn("Restock");

        //exicute on clicl or on enter
        submitBtn.addActionListener(e -> {
            try {
                int addQuantity = Integer.parseInt(quantityField.getText());
                item.restock(addQuantity);
                Manager.isEdited = true;
                quantityValue.setText(String.valueOf(item.getQuantityAvailable()));
                quantityValue.setForeground(item.isUnderMin() ? Color.RED : Color.GREEN);
                quantityField.setText("0");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        quantityField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    submitBtn.doClick();
                }
            }
        });
        add(submitBtn);
    }

    private class CustomBtn extends JButton {
        public CustomBtn(String text) {
            super(text);
            setFont(Manager.defaultFont(false,false));
            setForeground(Color.decode("#5294ff"));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
}