package ui;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.functions.*;

public class CenterManager extends JPanel {
    public CenterManager(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 0, 20));
        JLabel title = new JLabel("Manager");
        title.setFont(Manager.defaultFont(true, true));
        title.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(title);
        Color buttonColor = Color.decode("#5294ff");

        JButton addLine = createStyledButton("Add Production Line", buttonColor);
        addLine.addActionListener(e -> UI.switchContent(new AddProductionLine(centerPanel, frame, factory)));

        JButton modifyStatus = createStyledButton("Modify Status of a Production Line", buttonColor);
        modifyStatus.addActionListener(e -> UI.switchContent(new ModifyStatusOfAProductionLine(centerPanel, frame, factory)));

        JButton viewPerformance = createStyledButton("View Performance", buttonColor);
        viewPerformance.addActionListener(e -> UI.switchContent(new ViewPerformance(centerPanel, frame, factory)));

        JButton deliverTask = createStyledButton("Deliver Task", buttonColor);
        deliverTask.addActionListener(e -> UI.switchContent(new DeliverTask(centerPanel, frame, factory)));

        mainPanel.add(addLine);
        mainPanel.add(modifyStatus);
        mainPanel.add(viewPerformance);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color bgColor) {
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