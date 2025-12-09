package ui;

import java.awt.*;
import javax.swing.*;

public class UI {
/*
git checkout MKs-Brach
git fetch origin 
*/
//
    public UI() {
        int bigp = 450, smallp = 80;

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Add padding panels
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

        // Add login panel to center
        frame.add(new CenterLogin(frame), BorderLayout.CENTER);
        frame.setVisible(true);
    }

}

