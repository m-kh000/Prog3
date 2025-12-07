package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class Login {

    public Login() {

        //fetch
        String managerpass = "1234";//passwords
        String supervisorpass = "123";

        Dimension p = new Dimension(200,200);

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel paddings1 = new JPanel();//paddings
        JPanel paddings2 = new JPanel();
        JPanel paddings3 = new JPanel();
        JPanel paddings4 = new JPanel();
        paddings1.setPreferredSize(p);
        paddings2.setPreferredSize(p);
        paddings3.setPreferredSize(p);
        paddings4.setPreferredSize(p);

        frame.add(paddings1, BorderLayout.NORTH);
        frame.add(paddings2, BorderLayout.SOUTH);
        frame.add(paddings3, BorderLayout.EAST);
        frame.add(paddings4, BorderLayout.WEST);

        JPanel center = new JPanel(new GridLayout(5, 2, 10, 10));
        JComboBox<String> role = new JComboBox<>(new String[]{"Manager", "Supervisor"});//Jcombo is a dropmenu
        JRadioButton manager = new JRadioButton("Manager");//radioButtons re check boxes
        JRadioButton supervisor = new JRadioButton("Supervisor");
        ButtonGroup m_sGroup = new ButtonGroup();
        m_sGroup.add(manager);
        m_sGroup.add(supervisor);
        JPanel m_spanel = new JPanel(new FlowLayout());
        m_spanel.add(manager);
        m_spanel.add(supervisor);
        m_spanel.setSize(1000,1000);
        JPasswordField password = new JPasswordField();
        JButton loginButton = new JButton("Login");

        center.add(new JLabel("Select Role:"));
        center.add(m_spanel);
        center.add(new JLabel("Select Role:"));
        center.add(role);
        center.add(new JLabel("Password:"));
        center.add(password);
        center.add(loginButton);
        frame.add(center, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            if ("Manager".equals(role.getSelectedItem()) && managerpass.equals(new String(password.getPassword()))) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Welcome Manager!");
                // Replace line above with: new ManagerPage();
            } else if ("Supervisor".equals(role.getSelectedItem()) && supervisorpass.equals(new String(password.getPassword()))) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Welcome Manager!");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                password.setText("");
            }
        });

        frame.setVisible(true);
    }
}
