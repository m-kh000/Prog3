package ui.functions;
import javax.swing.*;
import java.awt.*;
import core.Factory;

public class DeleteItem extends FunctionPanel {
    public DeleteItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(4, 1, 20, 20));
        
        add(createBackButton(centerPanel, frame, factory, "supervisor"));
        
        JLabel title = new JLabel("Delete Item");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Item:");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        JComboBox<String> itemCombo = new JComboBox<>(new String[]{"Item 1", "Item 2", "Item 3"});
        itemCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        selectPanel.add(selectLabel);
        selectPanel.add(itemCombo);
        add(selectPanel);
        
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 20));
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);
        add(deleteBtn);
    }
}
