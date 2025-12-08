package ui;
import java.awt.*;
import javax.swing.*;

public class CenterLogin extends JPanel {

    public CenterLogin(JFrame frame) {
        String managerpass = "1234";
        String supervisorpass = "123";
        Color bg = frame.getBackground();

        setLayout(new GridLayout(4, 1, 30, 0));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setHorizontalAlignment(JLabel.CENTER);
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

        JPasswordField passwordbox = new JPasswordField();
        passwordbox.setPreferredSize(new Dimension(10, 10));
        passwordbox.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordbox.setBorder(null);
        passwordbox.setEchoChar('*');
        passwordbox.setBackground(bg);
        JPanel password = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel passLable = new JLabel("Password:");
        passLable.setFont(new Font("Arial", Font.PLAIN, 20));
        password.add(passLable);
        password.add(passwordbox);
        add(password);

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
            if (manager.isSelected() && managerpass.equals(new String(passwordbox.getPassword()))) {
                frame.getContentPane().remove(this);
                frame.add(new CenterManager(frame), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                JOptionPane.showMessageDialog(null, "Welcome Manager!");
            } else if (supervisor.isSelected() && supervisorpass.equals(new String(passwordbox.getPassword()))) {
                frame.getContentPane().remove(this);
                frame.add(new CenterSupervisor(frame), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                JOptionPane.showMessageDialog(null, "Welcome supervisor!");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                passwordbox.setText("");
            }
        });
    }
}
/*
in addproductline add lableboxes as follows(name,status())
in addproduct add lableboxes as follows()
*/