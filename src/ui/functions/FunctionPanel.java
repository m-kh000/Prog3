package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.*;

public abstract class FunctionPanel extends JPanel {
    protected JButton createBackButton(JFrame frame, Factory factory, String role) {
        JButton backBtn = new JButton("← Go Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.addActionListener(e -> {
    frame.getContentPane().remove(this);
            if (role.equals("manager")) {
                frame.add(new CenterManager(frame, factory), BorderLayout.CENTER);
            } else {
                frame.add(new CenterSupervisor(frame, factory), BorderLayout.CENTER);
            }
            frame.revalidate();
            frame.repaint();
        });
        return backBtn;
    }
}
