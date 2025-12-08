package ui;

import java.awt.*;
import javax.swing.*;

public class Login {

    public Login() {

        //fetch
        String managerpass = "1234";//passwords
        String supervisorpass = "123";

        Dimension p = new Dimension(100, 100);

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
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

        JPanel center = new JPanel(new GridLayout(3, 1, 30, 30));

        JRadioButton manager = new JRadioButton("Manager");//radioButtons are check boxes
        JRadioButton supervisor = new JRadioButton("Supervisor");
        ButtonGroup m_sGroup = new ButtonGroup();
        manager.setBorder(null);
        manager.setFocusable(false);
        supervisor.setBorder(null);
        supervisor.setFocusable(false);
        m_sGroup.add(manager);
        m_sGroup.add(supervisor);
        JPanel m_spanel = new JPanel(new GridLayout(2, 1));
        m_spanel.add(manager);
        m_spanel.add(supervisor);
        JPasswordField passwordbox = new JPasswordField();
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(10, 10));
        loginButton.setFocusable(false);
        loginButton.setIcon(new ImageIcon("1.png"));

        JPanel role = new JPanel(new FlowLayout());

        role.add(new JLabel("Select Role:"));
        role.add(m_spanel);
        center.add(role);

        JPanel password = new JPanel(new FlowLayout());
        password.add(new JLabel("Password:"));
        password.add(passwordbox);
        center.add(password);

        center.add(loginButton);
        frame.add(center, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            //if the manager is selected and password is correct
            if (manager.isSelected() && managerpass.equals(new String(passwordbox.getPassword()))) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Welcome Manager!");
                // Replace line above with: new ManagerPage();
            } else if (supervisor.isSelected() && supervisorpass.equals(new String(passwordbox.getPassword()))) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Welcome supervisor!");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                passwordbox.setText("");
            }
        });

        frame.setVisible(true);
    }
}
