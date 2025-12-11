package ui.functions;

import core.Item;
import java.awt.*;
import javax.swing.*;
import ui.LabelBox;

public class ModifyItem extends FunctionPanel {

    public ModifyItem(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("Modify Items", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        
        JPanel boxws = new JPanel(new GridLayout(8, 1, 10, 10));

        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Item:");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        String[] allitems = factory.getItemsNames();
        JComboBox<String> itemCombo = new JComboBox<>(allitems);
        itemCombo.setFont(new Font("Arial", Font.PLAIN, 20));
        selectPanel.add(selectLabel);
        selectPanel.add(itemCombo);
        boxws.add(selectPanel);

        LabelBox name = new LabelBox("Name:", false);
        LabelBox cat = new LabelBox("Name:", false);
        LabelBox price = new LabelBox("Name:", false);
        LabelBox quan = new LabelBox("Name:", false);
        LabelBox minquan = new LabelBox("Name:", false);
        itemCombo.addActionListener(e -> {
            Item item = factory.filterItemsByName((String)itemCombo.getSelectedItem()).get(0);
            name.setText(item.getName());
            cat.setText(item.getCategory());
            price.setText(""+item.getPrice());
            quan.setText(""+item.getQuantityAvailable());
            minquan.setText(""+item.getMinQuantity());
        });
        boxws.add(name);
        boxws.add(cat);
        boxws.add(price);
        boxws.add(quan);
        boxws.add(minquan);

        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 20));
        boxws.add(updateBtn);
        updateBtn.addActionListener(e -> {
            if (name.getText().isEmpty() || cat.getText().isEmpty() || price.getText().isEmpty() || quan.getText().isEmpty() || minquan.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
                return;
            }
            Item item = factory.filterItemsByName((String)itemCombo.getSelectedItem()).get(0);
            item.setName(name.getText());
            item.setCategory(cat.getText());
            item.setPrice(Double.parseDouble(price.getText()));
            item.setQuantityAvailable(Integer.parseInt(quan.getText()));
            item.setMinQuantity(Integer.parseInt(minquan.getText()));
            name.setText("");
            cat.setText("");
            price.setText("");
            quan.setText("");
            minquan.setText("");
            JOptionPane.showMessageDialog(null, "Item updated successfully");

        });
        add(boxws);
    }
}
