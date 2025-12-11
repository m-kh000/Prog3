package ui;

import java.awt.*;
import javax.swing.*;
import core.Factory;
import core.User;
import exceptions.EmptyFieldException;
import exceptions.InvalidEmailException;

import javax.swing.JOptionPane;

public class CenterSignup extends JPanel {
    
    public CenterSignup(JPanel centerPanel, JFrame frame, Factory factory) {
        Color bg = frame.getBackground();
        setLayout(new BorderLayout());
        
        JPanel title = new JPanel(new BorderLayout());
        JButton loginButton = new JButton("Login");
        loginButton.setFont(Manager.defaultFont(false, true));
        loginButton.setForeground(new Color(0xaabbff));
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(0xaabbff), 2));
        loginButton.setBackground(bg);
        loginButton.setFocusable(false);
        loginButton.setContentAreaFilled(false);
        loginButton.addActionListener(e -> UI.switchContent(new CenterLogin(centerPanel, frame, factory)));
        title.add(loginButton, BorderLayout.WEST);
        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(Manager.defaultFont(true, true));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        title.add(titleLabel, BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);
        
        JPanel boxes = new JPanel(new GridLayout(4, 1, 30, 0));
        //role
        JRadioButton manager = new JRadioButton("Manager");
        JRadioButton supervisor = new JRadioButton("Supervisor");
        ButtonGroup m_sGroup = new ButtonGroup();
        manager.setFont(Manager.defaultFont(true, true));
        manager.setBorder(null);
        manager.setFocusable(false);
        supervisor.setFont(Manager.defaultFont(true, true));
        supervisor.setBorder(null);
        supervisor.setFocusable(false);
        m_sGroup.add(manager);
        m_sGroup.add(supervisor);
        JPanel m_spanel = new JPanel(new GridLayout(2, 1, 0, -20));
        m_spanel.add(manager);
        m_spanel.add(supervisor);
        JPanel role = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel roleLable = new JLabel("Role:");
        roleLable.setFont(Manager.defaultFont(false, true));
        role.add(roleLable);
        role.add(m_spanel);
        boxes.add(role);
        
        LabelBox emailbox = new LabelBox("Email:");
        LabelBox passwordbox = new LabelBox("Password:",true);
        boxes.add(emailbox);
        boxes.add(passwordbox);
        
        JButton signupButton = new JButton();
        signupButton.setIcon(new ImageIcon("s.png"));
        signupButton.setFocusable(false);
        signupButton.setBorder(null);
        signupButton.setBackground(bg);
        signupButton.setContentAreaFilled(false);
        boxes.add(signupButton);
        
        add(boxes, BorderLayout.CENTER);
        signupButton.addActionListener(e -> {
            String email = emailbox.getText();
            String password = passwordbox.getText();
            try {
                if (!manager.isSelected() && !supervisor.isSelected()) {
                    throw new EmptyFieldException();
                }
                boolean isManager = manager.isSelected();
                utils.Validator.validateEmail(email, password, factory);
            
                factory.addUser(new User(email, password, isManager));
                UI.switchContent(new CenterLogin(centerPanel, frame, factory));
                JOptionPane.showMessageDialog(null, "Signup successful");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}