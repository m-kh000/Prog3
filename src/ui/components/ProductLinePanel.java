package ui.components;
import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class ProductLinePanel extends JPanel {
    public ProductLinePanel(core.ProductLine line) {
        setLayout(new GridLayout(1, 5, 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        ImageIcon icon = new ImageIcon("pl.png");
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
        iconPanel.add(new JLabel(line.getId()+"  "));
        iconPanel.add(new JLabel(new ImageIcon(img)));
        iconPanel.add(new JLabel("   "+line.getName()));
        add(iconPanel);

        JLabel completionrate = new JLabel(String.format("%.1f", line.getCompletionRate() * 100) + "%");
        completionrate.setForeground(Color.DARK_GRAY);
        completionrate.setFont(Manager.defaultFont(false, false));
        JPanel completionpPanel = new JPanel();
        completionpPanel.setLayout(new BoxLayout(completionpPanel, BoxLayout.X_AXIS));
        JLabel completionHint = new JLabel("Completion");
        completionHint.setForeground(Color.GRAY);
        completionHint.setFont(Manager.hintFont());
        completionpPanel.add(completionrate);
        completionpPanel.add(completionHint);
        add(completionpPanel);
        
        // Completed tasks
        JLabel completedValue = new JLabel(String.valueOf(line.getCompleted().size()));
        completedValue.setForeground(Color.GREEN);
        completedValue.setFont(Manager.defaultFont(false, false));
        JPanel completedPanel = new JPanel();
        completedPanel.setLayout(new BoxLayout(completedPanel, BoxLayout.X_AXIS));
        JLabel completedHint = new JLabel("Done");
        completedHint.setForeground(Color.GRAY);
        completedHint.setFont(Manager.hintFont());
        completedPanel.add(completedValue);
        completedPanel.add(completedHint);
        add(completedPanel);
        
        // In progress tasks
        JLabel inprogressValue = new JLabel(String.valueOf(line.getInprogress().size()));
        inprogressValue.setForeground(Color.BLUE);
        inprogressValue.setFont(Manager.defaultFont(false, false));
        JPanel inprogressPanel = new JPanel();
        inprogressPanel.setLayout(new BoxLayout(inprogressPanel, BoxLayout.X_AXIS));
        JLabel inprogressHint = new JLabel("Progress");
        inprogressHint.setForeground(Color.GRAY);
        inprogressHint.setFont(Manager.hintFont());
        inprogressPanel.add(inprogressValue);
        inprogressPanel.add(inprogressHint);
        add(inprogressPanel);
        
        // Canceled tasks
        JLabel canceledValue = new JLabel(String.valueOf(line.getCanceled().size()));
        canceledValue.setForeground(Color.GRAY);
        canceledValue.setFont(Manager.defaultFont(false, false));
        JPanel canceledPanel = new JPanel();
        canceledPanel.setLayout(new BoxLayout(canceledPanel, BoxLayout.X_AXIS));
        JLabel canceledHint = new JLabel("Canceled");
        canceledHint.setForeground(Color.GRAY);
        canceledHint.setFont(Manager.hintFont());
        canceledPanel.add(canceledValue);
        canceledPanel.add(canceledHint);
        add(canceledPanel);
    }
}
