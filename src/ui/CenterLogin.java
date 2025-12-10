package ui;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import jsonParser.JsonParser;
import utils.Validator;

public class CenterLogin extends JPanel {

    public CenterLogin(JPanel centerPanel, JFrame frame, Factory factory) {
        Color bg = frame.getBackground();

        setLayout(new GridLayout(4, 1, 30, 0));

        JPanel title = new JPanel();
        title.setFont(new Font("Arial", Font.BOLD, 40));
    
        add(title);

        JRadioButton manager = new JRadioButton("Manager");
        JRadioButton supervisor = new JRadioButton("Supervisor");
        ButtonGroup m_sGroup = new ButtonGroup();
        manager.setFont(new Font("Arial", Font.BOLD, 20));
        manager.setBorder(null);
        manager.setFocusable(false);
        supervisor.setFont(new Font("Arial", Font.BOLD, 20));
        supervisor.setBorder(null);
        supervisor.setFocusable(false);
        m_sGroup.add(manager);
        m_sGroup.add(supervisor);
        JPanel m_spanel = new JPanel(new GridLayout(2, 1, 0, -20));
        m_spanel.add(manager);
        m_spanel.add(supervisor);
        JPanel role = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel roleLable = new JLabel("Role:");
        roleLable.setFont(new Font("Arial", Font.PLAIN, 20));
        role.add(roleLable);
        role.add(m_spanel);
        add(role);

        LabelBox emailbox = new LabelBox("Email:");
        LabelBox passwordbox = new LabelBox("Password:", true);

        JButton loginButton = new JButton();
        loginButton.setIcon(new ImageIcon("l3.png"));
        loginButton.setBounds(0, 0, 10, 10);
        loginButton.setFocusable(false);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.setBorder(null);
        loginButton.setBackground(bg);
        loginButton.setContentAreaFilled(false);
        add(loginButton);

        loginButton.addActionListener(e -> {
            try {
                String response = utils.Validator.validateEmail(emailbox.getText(), passwordbox.getText(), factory);
                Validator.Response r = JsonParser.fromJson(response, Validator.Response.class);
                if (null==r.getRole()){
                    JOptionPane.showMessageDialog(null, r.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                        centerPanel.removeAll();
                    switch (r.getRole()) {
                    case "manager":
                        centerPanel.add(new CenterManager(centerPanel, frame, factory));
                        break;
                    case "Supervisor":
                        centerPanel.add(new CenterSupervisor(centerPanel, frame, factory));
                        break;
                    case "signun":
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