package ui.components;

import java.awt.*;
import javax.swing.*;
import ui.Manager;

public class ProductLinePanel extends JPanel {

    Timer refreshTimer;
    JLabel completionrate;
    JLabel completedValue;
    JLabel inprogressValue;
    core.ProductLine line;

    public ProductLinePanel(core.ProductLine line, String role) {
        this.line = line;
        setLayout(new GridLayout(1, role.startsWith("m") ? 5 : 4, 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        Manager.plAutorefresh = true;

        //id, icon and name
        ImageIcon icon = new ImageIcon("icons/pl.png");
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
        iconPanel.add(new JLabel(line.getId() + "  "));
        iconPanel.add(new JLabel(new ImageIcon(img)));
        iconPanel.add(new JLabel("   " + line.getName()));
        add(iconPanel);

        //completion rate with hint
        double rate = line.getCompletionRate();
        String s = Double.isNaN(rate) ? "No Tasks RN" : String.format("%.1f", rate * 100) + "%";
        completionrate = new JLabel(s);
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
        completedValue = new JLabel(String.valueOf(line.getCompleted().size()));
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
        inprogressValue = new JLabel(String.valueOf(line.getBothInPInL().size()));
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

        if (role.startsWith("m")) {
            JButton addNoteButton = new JButton();
            addNoteButton.setBorderPainted(false);
            addNoteButton.setFocusPainted(false);
            addNoteButton.setContentAreaFilled(false);
            Image noteimg = new ImageIcon("icons/note.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            addNoteButton.setIcon(new ImageIcon(noteimg));
            addNoteButton.addActionListener(e -> addnote());
            add(addNoteButton);
        }

        // Auto-refresh timer (every 1.2 seconds)
        refreshTimer = new Timer(1200, e -> refreshTasksPanel());
        refreshTimer.start();

    }

    private void refreshTasksPanel() {
        if (!Manager.plAutorefresh) {
            refreshTimer.stop();
            return;
        }

        double rate = line.getCompletionRate();
        String rateText = Double.isNaN(rate) ? "No Tasks RN" : String.format("%.1f", rate * 100) + "%";
        completionrate.setText(rateText);
        completedValue.setText(String.valueOf(line.getCompleted().size()));
        inprogressValue.setText(String.valueOf(line.getBothInPInL().size()));
        getParent().revalidate();
        getParent().repaint();
    }

    private void addnote() {
        JFrame frame = new JFrame("Notes");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        // String note = FileUtils.readNote(line.getId());
        String note = "old note";
        frame.add(new JTextArea(note));

        //save back to json
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // FileUtils.writeNote(line.getId(), ((JTextArea)frame.getContentPane().getComponent(0)).getText());
            frame.dispose();
        }});

        frame.setVisible(true);
    }
}
