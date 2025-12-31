package ui;
import core.Factory;
import core.Warehouse;
import java.awt.*;
import javax.swing.*;
import jsonParser.JsonParser;
import utils.FileUtils;
import utils.Validator;

public class CenterLogin extends JPanel {

    public CenterLogin(JPanel centerPanel, JFrame frame) {
        this(centerPanel, frame ,"","");
    }

    public CenterLogin(JPanel centerPanel, JFrame frame, String autoEmail, String autoPassword) {
        Color bg = frame.getBackground();
        setLayout(new BorderLayout());
        
        // Side panels
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel boxes = new JPanel(new GridLayout(3, 1, 30, 0));

        JPanel title = new JPanel(new BorderLayout());
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(Manager.defaultFont(false, true,""));
        signupButton.setForeground(new Color(0xaabbff));
        signupButton.setBorder(BorderFactory.createLineBorder(new Color(0xaabbff), 2));
        signupButton.setBackground(bg);
        signupButton.setFocusable(false);
        signupButton.setContentAreaFilled(false);
        signupButton.addActionListener(e -> UI.switchContent(new CenterSignup(centerPanel, frame )));
        title.add(signupButton, BorderLayout.WEST);
        JLabel titleLable = new JLabel("Login");
        titleLable.setFont(Manager.defaultFont(true, true));
        titleLable.setHorizontalAlignment(JLabel.CENTER);
        title.add(titleLable, BorderLayout.CENTER);
        mainPanel.add(title,BorderLayout.NORTH);

        LabelBox emailbox = new LabelBox("Email:");
        LabelBox passwordbox = new LabelBox("Password:", true);
        if(!autoEmail.equals(""))emailbox.setText(autoEmail);
        passwordbox.setText(autoPassword);

        boxes.add(emailbox);
        boxes.add(passwordbox);
        JButton loginButton = new JButton();
        loginButton.setIcon(new ImageIcon("icons/l.png"));
        loginButton.setFocusable(false);
        loginButton.setBorder(null);
        loginButton.setBackground(bg);
        loginButton.setContentAreaFilled(false);
        boxes.add(loginButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.add(boxes, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        //triggered be pressing btn or ENTER
        loginButton.addActionListener(e -> {
        try {
            String email = emailbox.getText();
            String passord = passwordbox.getText();
            String response = utils.Validator.validateEmail(email, passord);
            Validator.Response r = JsonParser.fromJson(response, Validator.Response.class);
            Factory factory = null;
            boolean successful = false;

            switch (r.getState().toLowerCase()) {
                case "manager":
                    centerPanel.removeAll();
                    factory = new Factory(FileUtils.readProductLines(),new Warehouse(FileUtils.readItems(),FileUtils.readProducts()));
                    makeSaveOnClose(frame, factory);
                    centerPanel.add(new CenterManager(centerPanel, frame ,factory));
                    successful = true;
                    break;
                case "supervisor":
                    centerPanel.removeAll();
                    factory = new Factory(FileUtils.readProductLines(),new Warehouse(FileUtils.readItems(),FileUtils.readProducts()));
                    makeSaveOnClose(frame, factory);
                    centerPanel.add(new CenterSupervisor(centerPanel, frame ,factory));
                    successful = true;
                    break;
                case "signup":
                    centerPanel.removeAll();
                    centerPanel.add(new CenterSignup(centerPanel, frame ,email, passord));
                    break;
            
            }
                    centerPanel.revalidate();
                    centerPanel.repaint();
                    if(successful) {
                        ImageIcon icon = new ImageIcon("icons/smile5.png");
                        Image img = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                        JOptionPane.showMessageDialog(null, r.getMessage(), "HI!!", JOptionPane.PLAIN_MESSAGE, new ImageIcon(img));
                    } else {
                        JOptionPane.showMessageDialog(null, r.getMessage(), "Message", JOptionPane.INFORMATION_MESSAGE);
                    }
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            FileUtils.log(ex);
        }
        });
        passwordbox.getTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });
        
    }
    public void makeSaveOnClose (JFrame frame, Factory factory) {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try{
                if (Manager.isEdited) {
                    int response = JOptionPane.showConfirmDialog(frame, "Do you want to save before exiting?", "Confirm Exit",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        Factory.saveToTXT();
                        System.exit(0);
                    } else if (response == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                } 
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);                
                FileUtils.log(e);
            }
            System.exit(0);
            
    }});
    }
}
/*
git pull origin main
git push -u origin MKs-branch
*/