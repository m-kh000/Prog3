package ui;
import java.awt.*;
import javax.swing.*;

public class LabelBox extends JPanel {
    private JTextField textField;

    public LabelBox(String labelText) {
        setLayout(new GridLayout(1, 2, 0, 0));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        add(label);
        
        textField = new JPasswordField();
        textField.setPreferredSize(new Dimension(10, 10));
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setBorder(null);
        ((JPasswordField) textField).setEchoChar('*');
        textField.setBackground(UIManager.getColor("Panel.background"));
        add(textField);
    }

    public String getText() {
        return textField instanceof JPasswordField ? 
            new String(((JPasswordField) textField).getPassword()) : textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }
}
