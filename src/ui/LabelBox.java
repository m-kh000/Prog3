package ui;
import java.awt.*;
import javax.swing.*;

public class LabelBox extends JPanel {
    private JTextField textField;
    String placeholder;

    public LabelBox(String labelText) {
        this(labelText, false);
    }

    public LabelBox(String labelText, boolean isPassword) {
        this(labelText, isPassword,false);
    }
    
    public LabelBox(String labelText, boolean isPassword,boolean isDate) {
        setLayout(new GridLayout(1, 2, 0, 0));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        add(label);
        
        if (isPassword) {
            textField = new JPasswordField();
            ((JPasswordField) textField).setEchoChar('*');
        } else {
            textField = new JTextField();
        }
        textField.setPreferredSize(new Dimension(10, 10));
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setBorder(null);
        textField.setBackground(UIManager.getColor("Panel.background"));

        textField.setForeground(Color.GRAY);
        placeholder = isDate ? "Enter DD-MM-YYYY" : isPassword? "" : "Enter " + labelText.toLowerCase().replace(":", "");
        textField.setText(placeholder);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
        });
        add(textField);
    }

    public String getText() {
        return textField instanceof JPasswordField ? 
            new String(((JPasswordField) textField).getPassword()) : textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public boolean isEmpty() {
        String text = getText();
        return text.isEmpty() || text.startsWith("Enter ");
    }

    public void setPlacehoder() {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
    }
}
