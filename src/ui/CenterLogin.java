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


            //     int x = utils.Validator.validateEmail(emailbox.getText(),passwordbox.getText(), factory);
            //     if (x == -1) {
            //         JOptionPane.showMessageDialog(null, "User not found.\nyou need to sign up", "Error", JOptionPane.ERROR_MESSAGE);
            //         centerPanel.removeAll();
            //         centerPanel.add(new CenterSignup(centerPanel, frame, factory));
            //     } else if (x == 0) {
            //         JOptionPane.showMessageDialog(null, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
            //         passwordbox.setText("");
            //     } else {
            //         if (manager.isSelected()) {
            //                 centerPanel.removeAll();
            //                 centerPanel.add(new ManagerPanel(centerPanel, frame, factory));
            //                 centerPanel.revalidate();
            //                 centerPanel.repaint();
            //         } else if (supervisor.isSelected()) {
            //             if (passwordbox.getText().equals(supervisorpass)) {
            //                 centerPanel.removeAll();
            //                 centerPanel.add(new SupervisorPanel(centerPanel, frame, factory));
            //                 centerPanel.revalidate();
            //                 centerPanel.repaint();
            //             } else {
            //                 JOptionPane.showMessageDialog(null, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
            //             }
            //         } else {
            //             JOptionPane.showMessageDialog(null, "Please select a role", "Error", JOptionPane.ERROR_MESSAGE);
            //         }
            //     }
            // } catch (InvalidEmailException e1) {
            //     // TODO Auto-generated catch block
            //     e1.printStackTrace();
            // }
            

                String response = utils.Validator.validateEmail(emailbox.getText(), passwordbox.getText(), factory);
                Validator.Response r = JsonParser.fromJson(response, Validator.Response.class);
                if ()
                if (r.getRole()=="manager"){
                    centerPanel.removeAll();
                    centerPanel.add(new CenterManager(centerPanel, frame, factory));
                    centerPanel.revalidate();
                    centerPanel.repaint();
                
                }else{
                    centerPanel.removeAll();
                    centerPanel.add(new CenterSupervisor(centerPanel, frame, factory));
                    centerPanel.revalidate();
                    centerPanel.repaint();
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