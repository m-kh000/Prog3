package ui.functions;
import core.Factory;
import java.awt.*;
import javax.swing.*;
import ui.Manager;
import ui.components.ProductLinePanel;

public class FilterProductLines extends FunctionPanel {
    private JPanel ProductLinesPanel;
    private JComboBox<String> filterField;

    public FilterProductLines(JPanel centerPanel, JFrame frame, Factory factory) {
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new GridLayout(1, 4));
        JLabel filterLabel = new JLabel("Filter by: ");
        JLabel filterLabel2 = new JLabel("a specific product");
        filterLabel.setFont(Manager.defaultFont(false, false));
        filterField = new JComboBox<>(factory.getProductNames());
        filterField.setFont(Manager.defaultFont(false, false));
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(Manager.defaultFont(true, false));

        filterBtn.addActionListener(e -> {
            String filterValue = (String) filterField.getSelectedItem();
            updateProductLinesPanel(factory, filterValue);
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
        filterPanel.add(filterLabel2);
        filterPanel.add(filterField);
        filterPanel.add(filterBtn);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createTopPanel("Filter ProductLines", centerPanel, frame, factory, "supervisor"), BorderLayout.NORTH);
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        ProductLinesPanel = createProductLinesPanel();
        add(new JScrollPane(ProductLinesPanel), BorderLayout.CENTER);
    }

    private void updateProductLinesPanel(Factory factory, String filterValue) {
        ProductLinesPanel.removeAll();

        java.util.List<core.ProductLine> ProductLines = factory.filterProductLinesByProduct(filterValue);
        if (ProductLines != null && !ProductLines.isEmpty()) {
            for (core.ProductLine ProductLine : ProductLines) {
                ProductLinesPanel.add(new ProductLinePanel(ProductLine));
                ProductLinesPanel.add(Box.createVerticalStrut(5));
            }
        }
        else {
            ProductLinesPanel.add(new JLabel("No ProductLines found."));
        }

        ProductLinesPanel.revalidate();
        ProductLinesPanel.repaint();
    }

    private JPanel createProductLinesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
}
