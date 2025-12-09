package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.*;

public abstract class FunctionPanel extends JPanel {
    
    protected JButton createBackButton(JPanel centerPanel, JFrame frame, Factory factory, String role) {
        JButton backBtn = new JButton("← Go Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.addActionListener(e -> navigateBack(centerPanel, frame, factory, role));
        return backBtn;
    }
    
    protected JPanel createTopPanel(String titleText, JPanel centerPanel, JFrame frame, Factory factory, String role) {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(createBackButton(centerPanel, frame, factory, role), BorderLayout.WEST);
        return topPanel;
    }
    
    protected void navigateBack(JPanel centerPanel, JFrame frame, Factory factory, String role) {
        if (role.equals("manager")) {
            UI.switchContent(new CenterManager(centerPanel, frame, factory));
        } else {
            UI.switchContent(new CenterSupervisor(centerPanel, frame, factory));
        }
    }
    

}
