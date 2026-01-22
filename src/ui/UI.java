package ui;
import java.awt.*;
import javax.swing.*;

public class UI extends JFrame {
    private static JPanel centerPanel;
    
    public UI() {
        int bigp = 300, smallp = 80;
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        JPanel paddings1 = new JPanel();
        JPanel paddings2 = new JPanel();
        JPanel paddings3 = new JPanel();
        JPanel paddings4 = new JPanel();
        paddings1.setPreferredSize(new Dimension(smallp, smallp));
        paddings2.setPreferredSize(new Dimension(smallp, smallp));
        paddings3.setPreferredSize(new Dimension(bigp, bigp));
        paddings4.setPreferredSize(new Dimension(bigp, bigp));
        add(paddings1, BorderLayout.NORTH);
        add(paddings2, BorderLayout.SOUTH);
        add(paddings3, BorderLayout.EAST);
        add(paddings4, BorderLayout.WEST);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new CenterLogin(centerPanel, this));
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    public static void switchContent(JPanel newPanel) {
        centerPanel.removeAll();
        centerPanel.add(newPanel);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    
}
//  TODO: use ThreadManager.StartInitialization() to read the data when someone logs in
