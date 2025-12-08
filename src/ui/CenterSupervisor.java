package ui;
import java.awt.*;
import javax.swing.*;

public class CenterSupervisor extends JPanel {

    public CenterSupervisor(JFrame frame) {
        setLayout(new GridLayout(7, 1, 0, 20));

        Color buttonColor = Color.decode("#5294ff");

        JLabel title = new JLabel("Supervisor");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);

        JPanel row1 = new JPanel(new GridLayout(1, 2, 10, 0));
        row1.add(createStyledButton("Items", buttonColor));
        row1.add(createStyledButton("Filter Items", buttonColor));
        add(row1);

        JPanel row2 = new JPanel(new GridLayout(1, 2, 10, 0));
        row2.add(createStyledButton("Tasks", buttonColor));
        row2.add(createStyledButton("Filter Tasks", buttonColor));
        add(row2);

        JPanel row3 = new JPanel(new GridLayout(1, 2, 10, 0));
        row3.add(createStyledButton("Products", buttonColor));
        row3.add(createStyledButton("Filter Products", buttonColor));
        add(row3);

        add(createStyledButton("Filter Production Lines", buttonColor));

        add(createStyledButton("Most Requested", buttonColor));
        add(createStyledButton("Save Status to TXT", buttonColor));
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }
}
