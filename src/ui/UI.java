package ui;

import java.awt.*;
import javax.swing.*;
import core.Factory;

public class UI {
    private static JPanel centerPanel;
    
    public UI(Factory factory) {
        int bigp = 450, smallp = 80;

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel paddings1 = new JPanel();
        JPanel paddings2 = new JPanel();
        JPanel paddings3 = new JPanel();
        JPanel paddings4 = new JPanel();
        paddings1.setPreferredSize(new Dimension(smallp, smallp));
        paddings2.setPreferredSize(new Dimension(smallp, smallp));
        paddings3.setPreferredSize(new Dimension(bigp, bigp));
        paddings4.setPreferredSize(new Dimension(bigp, bigp));
        frame.add(paddings1, BorderLayout.NORTH);
        frame.add(paddings2, BorderLayout.SOUTH);
        frame.add(paddings3, BorderLayout.EAST);
        frame.add(paddings4, BorderLayout.WEST);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new CenterLogin(centerPanel, frame, factory));
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    public static void switchContent(JPanel newPanel) {
        centerPanel.removeAll();
        centerPanel.add(newPanel);
        centerPanel.revalidate();
        centerPanel.repaint();
    }
}

