package ui.functions;
import core.Factory;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import ui.FunctionPanel;
import ui.components.ItemPanel;

public class FilterItems extends FunctionPanel {
    private JPanel itemsPanel;
    
    public FilterItems(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());
        
        // Components creation
        JPanel filterPanel = new JPanel(new GridLayout(1,4));
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"Name", "Category", "Available","Under min","Out"});
        filterCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        filterCombo.setSelectedItem(null);
        JTextField filterField = new JTextField(20);
        filterField.setFont(new Font("Arial", Font.PLAIN, 16));
        filterField.setEnabled(false);
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Listeners
        // Filter combo selection changes
        filterCombo.addActionListener(e->{
            if(((String)filterCombo.getSelectedItem()).equals("Available")||((String)filterCombo.getSelectedItem()).equals("Under min")||((String)filterCombo.getSelectedItem()).equals("Out")){
                filterField.setEnabled(false);
            }
            else{
                filterField.setEnabled(true);
            }
        });
        
        // Filter button click
        filterBtn.addActionListener(e -> {
            String filterType = (String) filterCombo.getSelectedItem();
            String filterValue = filterField.getText();
            updatePanel(filterType, filterValue);
        });
        
        // Enter key binding
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "filter");
        getActionMap().put("filter", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                filterBtn.doClick();
            }
        });
        
        // Layout setup
        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);
        
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter Items", centerPanel, frame, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);
        
        itemsPanel = createItemsPanel();
        // Load all items initially
        for(core.Item item : Factory.previewItems()) {
            itemsPanel.add(new ItemPanel(item));
            itemsPanel.add(Box.createVerticalStrut(5));
        }
        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
    }
    
    // Update items panel with filtered results
    private void updatePanel(String filterType, String filterValue) {
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
        }else{
            itemsPanel.add(new JLabel("No items found"));
        }
        
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
    
    // Create items panel with vertical layout
    private JPanel createItemsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
}