package ui;

import java.awt.*;
import javax.swing.*;
import core.Factory;

public class CenterSignup extends JPanel {
    
    public CenterSignup(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new GridLayout(3, 1, 30, 0));
        
        JLabel title = new JLabel("Sign Up");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        
        
        LabelBox emailBox = new LabelBox("Email");
        LabelBox passwordBox = new LabelBox("Password", true);
    }
}