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
//  TODO: do the todo in ui.functions.DeliverTask
//  TODO: use ThreadManager.StartInitialization() to read the data when someone logs in
//  TODO: when use new Product(...) pass HashMap<String, Integer> instead of HashMap<Item, Integer>
//  TODO: when use new Task(...) pass the product name instead of the product itself
//  TODO: two LocalDates on top sales and deliver task to do and load screen and make sure of directly sorting file reading
//  TODO: it is not important to make sure of sorting the file reading because you now call just ThreadManager.startInitialization() and it will handle all the work for you
//  TODO: do all the todos

//  TODO: I think the next todo is already done
//  TODO: cancel and deliver task