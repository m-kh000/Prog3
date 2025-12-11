package ui.functions;

import javax.swing.*;
import java.awt.*;
import ui.LabelBox;
import core.Factory;
import core.Item;
import java.util.List;

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
            List<Item> itemarr = factory.filterItemsByName((String)itemCombo.getSelectedItem());
            Item item = itemarr.getFirst();
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
        add(boxws);
    }
}
