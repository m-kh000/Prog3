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
        setLayout(new GridLayout(1,  6 , 10, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        Manager.plAutorefresh = true;

        //id, icon and name
        JLabel idLable = new JLabel(String.format("#%04d ", line.getId())+" ");
        idLable.setFont(Manager.defaultFont(false, false));
        ImageIcon icon = new ImageIcon("icons/pl.png");
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel nameLable = new JLabel(" " + line.getName() + " ");
        nameLable.setFont(Manager.defaultFont(false, false));
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
        iconPanel.add(idLable);
        iconPanel.add(new JLabel(new ImageIcon(img)));
        add(iconPanel);
        add(nameLable);

        //completion rate with hint
        double rate = line.getCompletionRate();
        String s = Double.isNaN(rate) ? "0" : String.format("%.1f", rate * 100) + "%";
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

        JPanel note_start = new JPanel(new GridLayout(1, role.startsWith("m") ? 2 : 1)); 
        JButton start = new JButton();
        start.setBorderPainted(false);
        start.setFocusPainted(false);
        start.setContentAreaFilled(false);
        Image startimg = new ImageIcon("icons/start.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        start.setIcon(new ImageIcon(startimg));
        start.addActionListener(e -> line.startWorking());
        note_start.add(start);

        if (role.startsWith("m")) {
            JButton addNoteButton = new JButton();
            addNoteButton.setBorderPainted(false);
            addNoteButton.setFocusPainted(false);
            addNoteButton.setContentAreaFilled(false);
            Image noteimg = new ImageIcon("icons/note.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            addNoteButton.setIcon(new ImageIcon(noteimg));
            addNoteButton.addActionListener(e -> addnote());
            note_start.add(addNoteButton);
        }

        add(note_start);
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
        String rateText = Double.isNaN(rate) ? "0" : String.format("%.1f", rate * 100) + "%";
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
        String note = line.getNote();
        JTextArea tx = new JTextArea(note);
        frame.add(tx);

        //save back to json
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            line.setNote(tx.getText());
            Manager.isEdited=true;
            frame.dispose();
        }});

        frame.setVisible(true);
    }
}
