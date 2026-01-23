package ui;

import java.awt.*;
import javax.swing.*;

public abstract class FunctionPanel extends JPanel {
    
    private static ImageIcon cachedBackIcon = null;
    
    private static ImageIcon getBackIcon() {
        if (cachedBackIcon == null) {
            ImageIcon backIcon = new ImageIcon("icons/back.png");
            Image img = backIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            cachedBackIcon = new ImageIcon(img);
        }
        return cachedBackIcon;
    }

    protected JButton BackBtn(JPanel centerPanel, JFrame frame, String role) {
        JButton backBtn = new JButton();
        backBtn.setIcon(getBackIcon());
        backBtn.setFont(Manager.defaultFont(true, false));
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.addActionListener(e -> navigateBack(centerPanel, frame, role));
        return backBtn;
    }

    protected JPanel createTopPanel(String titleText, JPanel centerPanel, JFrame frame, String role) {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel(titleText);
        title.setFont(Manager.defaultFont(true, true));
        title.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(BackBtn(centerPanel, frame, role), BorderLayout.WEST);
        setName(titleText);
        return topPanel;
    }

    protected void navigateBack(JPanel centerPanel, JFrame frame, String role) {
        Manager.taskAutorefresh = false;
        Manager.plAutorefresh = false;
        Manager.itemAutorefresh = false;
        if (role.equals("manager")) {
            UI.switchContent(new CenterManager(centerPanel, frame));
        } else {
            UI.switchContent(new CenterSupervisor(centerPanel, frame));
        }
    }

    protected JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(Manager.defaultFont(true, false));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

}
