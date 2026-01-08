package ui.functions;
import core.Factory;
import core.Task;
import exceptions.EmptyFieldException;
import java.awt.*;
import javax.swing.*;
import ui.LabelBox;
import ui.Manager;
import utils.FileUtils;

// AddTask panel for creating new production tasks
public class AddTask extends FunctionPanel {
    
    public AddTask(JPanel centerPanel, JFrame frame) {
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        // Components
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 10, 20));
        
        JPanel selectPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectLabel = new JLabel("Select Product:");
        selectLabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> product = new JComboBox<String>(Factory.getProductNames());
        product.setFont(Manager.defaultFont(true, false));
        product.setSelectedItem(null);
        
        JPanel selectPLPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel selectPLLabel = new JLabel("Select Product Line:");
        selectPLLabel.setFont(Manager.defaultFont(true, false));
        JComboBox<String> PL = new JComboBox<String>(Factory.getProductLineNames());
        PL.setFont(Manager.defaultFont(true, false));
        PL.setSelectedItem(null);
        
        LabelBox quantity = new LabelBox("Required Quantity:", false);
        LabelBox customer = new LabelBox("Customer Name:", false);
        LabelBox start = new LabelBox("Start Date:", false, true);
        LabelBox delivery = new LabelBox("Delivery Date:", false, true);
        
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(Manager.defaultFont(true, false));
        
        // Listeners
        // Enter key functionality
        delivery.getTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    submitBtn.doClick();
                }
            }
        });

        // Submit button listener
        submitBtn.addActionListener(e -> {
            try {
                if (product.getSelectedItem() == null || quantity.getText().isEmpty() || customer.getText().isEmpty() || start.getText().isEmpty() || delivery.getText().isEmpty()) {
                    throw new EmptyFieldException();
                }
                
                String productName = (String) product.getSelectedItem();
                String PLtext = (String) PL.getSelectedItem();
                String quantityText = quantity.getText();
                String customerText = customer.getText();
                String startText = start.getText();
                String deliveryText = delivery.getText();
                
                Factory.add(new Task(Factory.findProductByName(productName), Integer.parseInt(quantityText), customerText, utils.Validator.validateDate(startText), utils.Validator.validateDate(deliveryText),"inline"),PLtext);
                Manager.isEdited = true;

                product.setSelectedItem(null);
                PL.setSelectedItem(null);
                quantity.reset();
                customer.reset();
                start.reset();
                delivery.reset();

                JOptionPane.showMessageDialog(frame, "Task added successfully");
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
                FileUtils.log(ex);
            }
        });
        
        // Layout setup
        mainPanel.add(createTopPanel("Add Task", centerPanel, frame, "supervisor"));
        
        selectPanel.add(selectLabel);
        selectPanel.add(product);
        mainPanel.add(selectPanel);
        
        selectPLPanel.add(selectPLLabel);
        selectPLPanel.add(PL);
        mainPanel.add(selectPLPanel);
        
        mainPanel.add(quantity);
        mainPanel.add(customer);
        mainPanel.add(start);
        mainPanel.add(delivery);
        mainPanel.add(submitBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
}