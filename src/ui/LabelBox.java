package ui;
import java.awt.*;
import javax.swing.*;

public class LabelBox extends JPanel {
    private JTextField textField;
    String placeholder;
    boolean isDate, isPassword;
    String labelText;


    public LabelBox(String labelText) {
        this(labelText, false);
    }

    public LabelBox(String labelText, boolean isPassword) {
        this(labelText, isPassword,false);
    }
    
    public LabelBox(String labelText, boolean isPassword,boolean isDate) {
        this.isDate = isDate;
        this.isPassword = isPassword;
        this.labelText = labelText;
        setLayout(new GridLayout(1, 2, 0, 0));
        
        JLabel label = new JLabel(labelText);
        label.setFont(Manager.defaultFont(true, false));
        add(label);
        
        if (isPassword) {
            textField = new JPasswordField();
            ((JPasswordField) textField).setEchoChar('*');
        } else {
            textField = new JTextField();
        }
        textField.setPreferredSize(new Dimension(10, 10));
        textField.setFont(Manager.defaultFont(true, false));
        textField.setBorder(null);
        textField.setBackground(UIManager.getColor("Panel.background"));
        reset();
        
        add(textField);
    }

    public JTextField getTextField() {
        return textField;
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

    public void reset(){
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
    }
}
