package ui;

import core.User;
import exceptions.EmptyFieldException;
import java.awt.*;
import javax.swing.*;
import jsonParser.JsonParser;
import utils.FileUtils;
import utils.Validator;

public class CenterSignup extends JPanel {
    
    public CenterSignup(JPanel centerPanel, JFrame frame) {
        this(centerPanel, frame,"","");
    }
    
    public CenterSignup(JPanel centerPanel, JFrame frame,String autoEmail, String autoPassword) {
        Color bg = frame.getBackground();
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        // Components creation
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel title = new JPanel(new BorderLayout());
        JButton loginButton = new JButton("Login");
        loginButton.setFont(Manager.defaultFont(false, true));
        loginButton.setForeground(new Color(0xaabbff));
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(0xaabbff), 2));
        loginButton.setBackground(bg);
        loginButton.setFocusable(false);
        loginButton.setContentAreaFilled(false);
        
        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(Manager.defaultFont(true, true));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel boxes = new JPanel(new GridLayout(4, 1, 30, 0));
        
        // Role selection
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
        
        LabelBox emailbox = new LabelBox("Email:");
        LabelBox passwordbox = new LabelBox("Password:",true);
        if(!autoEmail.equals(""))emailbox.setText(autoEmail);
        passwordbox.setText(autoPassword);

        JButton signupButton = new JButton();
        signupButton.setIcon(new ImageIcon("icons/s.png"));
        signupButton.setFocusable(false);
        signupButton.setBorder(null);
        signupButton.setBackground(bg);
        signupButton.setContentAreaFilled(false);

        // Listeners
        // Login button click
        loginButton.addActionListener(e -> UI.switchContent(new CenterLogin(centerPanel, frame)));
        
        // Signup button click
        signupButton.addActionListener(e -> {
            String email = emailbox.getText();
            String password = passwordbox.getText();
            try {
                if (!manager.isSelected() && !supervisor.isSelected()) {
                    throw new EmptyFieldException();
                }

                String response =utils.Validator.validateSignupEmail(email);
                if (JsonParser.fromJson(response, Validator.Response.class).getState().toLowerCase().equals("unavailable")) {
                    JOptionPane.showMessageDialog(null, "Email is already in use");
                UI.switchContent(new CenterLogin(centerPanel, frame, email,password));
                return;
                }

                response = utils.Validator.validateSignupPassword(password);
                if(JsonParser.fromJson(response, Validator.Response.class).getState().toLowerCase().equals("invalid")) {
                    JOptionPane.showMessageDialog(null, "Password is too weak");
                    return;
                }
                User newUser = new User(email, password, manager.isSelected());
                utils.FileUtils.saveUsers(newUser);
                UI.switchContent(new CenterLogin(centerPanel, frame));
                JOptionPane.showMessageDialog(null, "Signup successful");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                FileUtils.log(ex);
            }
        });

        // Layout setup
        title.add(loginButton, BorderLayout.WEST);
        title.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(title, BorderLayout.NORTH);
        
        boxes.add(role);
        boxes.add(emailbox);
        boxes.add(passwordbox);
        boxes.add(signupButton);
        
        mainPanel.add(boxes, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }
}