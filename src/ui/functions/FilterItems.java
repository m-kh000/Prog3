package ui.functions;
import core.Factory;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import ui.components.ItemPanel;

public class FilterItems extends FunctionPanel {
    private JPanel itemsPanel;
    
    public FilterItems(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new BorderLayout());
        add(createTopPanel("Filter Items", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        
        JPanel filterPanel = new JPanel(new GridLayout(1,4));
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"Name", "Category", "Available","Under min","Out"});
        filterCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField filterField = new JTextField(20);
        filterField.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(new Font("Arial", Font.BOLD, 16));
        filterCombo.addActionListener(e->{
            if(((String)filterCombo.getSelectedItem()).equals("Available")||((String)filterCombo.getSelectedItem()).equals("Under min")||((String)filterCombo.getSelectedItem()).equals("Out")){
                filterField.setEnabled(false);
            }
            else{
                filterField.setEnabled(true);
            }
        });
        filterBtn.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            String filterValue = filterField.getText();
            updatePanel(factory, filterType, filterValue);
        });
        //enter key
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    filterBtn.doClick();
                }
            }
        });
        
        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);
        
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter Items", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);
        
        itemsPanel = createItemsPanel();
        // Load initial items
        for(core.Item item : Factory.previewItems()) {
            itemsPanel.add(new ItemPanel(item));
        }
        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
    }
    
    private void updatePanel(Factory factory, String filterType, String filterValue) {
        itemsPanel.removeAll();
        
        List<core.Item> items = null;
        switch (filterType) {
            case "Name" -> items = Factory.filterItemsByName(filterValue);
            case "Category" -> items = Factory.filterItemsByCategory(filterValue);
            case "Available" -> items = Factory.filterItemsByAvailable();
            case "Under min" -> items = Factory.filterItemsByUnderMin();
            case "Out" -> items = Factory.filterItemsByOut();
        }
        
        if(items != null && !items.isEmpty()) {
            for(core.Item item : items) {
                itemsPanel.add(new ItemPanel(item));
                itemsPanel.add(Box.createVerticalStrut(5));
            }
        }
        
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
    
    private JPanel createItemsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }}
