package ui.components;
import javax.swing.*;
import java.awt.*;

public class ProductLinePanel extends JPanel {
    public ProductLinePanel(core.ProductLine line) {
        setLayout(new GridLayout(1, 6, 10, 0));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        add(new JLabel("ID: " + line.getId()));
        add(new JLabel(new ImageIcon("pl.png")));
        add(new JLabel(line.getName()));
        
        JLabel completedLabel = new JLabel("Done: " + line.getCompleted().size());
        completedLabel.setForeground(Color.GREEN);
        add(completedLabel);
        
        JLabel inprogressLabel = new JLabel("Progress: " + line.getInprogress().size());
        inprogressLabel.setForeground(Color.BLUE);
        add(inprogressLabel);
        
        JLabel canceledLabel = new JLabel("Canceled: " + line.getCanceled().size());
        canceledLabel.setForeground(Color.GRAY);
        add(canceledLabel);
    }
}
