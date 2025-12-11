package ui;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import jsonParser.JsonParser;
import utils.Validator;

public class CenterLogin extends JPanel {

    public CenterLogin(JPanel centerPanel, JFrame frame, Factory factory) {
        Color bg = frame.getBackground();
        setLayout(new BorderLayout());
        JPanel boxes = new JPanel(new GridLayout(3, 1, 30, 0));

        JPanel title = new JPanel(new BorderLayout());
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 20));
        signupButton.setForeground(new Color(0xaabbff));
        signupButton.setBorder(BorderFactory.createLineBorder(new Color(0xaabbff), 2));
        signupButton.setBackground(bg);
        signupButton.setFocusable(false);
        signupButton.setContentAreaFilled(false);
        signupButton.addActionListener(e -> UI.switchContent(new CenterSignup(centerPanel, frame, factory)));
        title.add(signupButton, BorderLayout.WEST);
        JLabel titleLable = new JLabel("Login");
        titleLable.setFont(new Font("Arial", Font.BOLD, 30));
        titleLable.setHorizontalAlignment(JLabel.CENTER);
        title.add(titleLable, BorderLayout.CENTER);
        add(title,BorderLayout.NORTH);

        LabelBox emailbox = new LabelBox("Email:");
        LabelBox passwordbox = new LabelBox("Password:", true);

        boxes.add(emailbox);
        boxes.add(passwordbox);
        JButton loginButton = new JButton();
        loginButton.setIcon(new ImageIcon("l.png"));
        loginButton.setFocusable(false);
        loginButton.setBorder(null);
        loginButton.setBackground(bg);
        loginButton.setContentAreaFilled(false);
        boxes.add(loginButton);

        add(boxes, BorderLayout.CENTER);
        loginButton.addActionListener(e -> {
            try {
                String response = utils.Validator.validateEmail(emailbox.getText(), passwordbox.getText(), factory);
                Validator.Response r = JsonParser.fromJson(response, Validator.Response.class);
                if (null==r.getRole()){
                    JOptionPane.showMessageDialog(null, r.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                        centerPanel.removeAll();
                    switch (r.getRole().toLowerCase()) {
                    case "manager":
                        centerPanel.add(new CenterManager(centerPanel, frame, factory));
                        break;
                    case "supervisor":
                        centerPanel.add(new CenterSupervisor(centerPanel, frame, factory));
                        break;
                    case "signup":
                        centerPanel.add(new CenterSignup(centerPanel, frame, factory));
                        break;
                }
                    centerPanel.revalidate();
                    centerPanel.repaint();
                    JOptionPane.showMessageDialog(null, r.getMessage());
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",3);
            }
        });
    }
}
/*

*/
/*
git pull origin main
git push -u origin MKs-branch
*/