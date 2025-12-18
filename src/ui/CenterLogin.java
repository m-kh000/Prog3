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
        JPanel boxes = new JPanel(new GridLayout(3, 1, 30, 0));

        JPanel title = new JPanel(new BorderLayout());
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(Manager.defaultFont(false, true));
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
        add(title,BorderLayout.NORTH);

        LabelBox emailbox = new LabelBox("Email:");
        LabelBox passwordbox = new LabelBox("Password:", true);
        if(!autoEmail.equals(""))emailbox.setText(autoEmail);
        passwordbox.setText(autoPassword);

        boxes.add(emailbox);
        boxes.add(passwordbox);
        JButton loginButton = new JButton();
        loginButton.setIcon(new ImageIcon("l.png"));
        loginButton.setFocusable(false);
        loginButton.setBorder(null);
        loginButton.setBackground(bg);
        loginButton.setContentAreaFilled(false);
        boxes.add(loginButton);

        add(boxes, BorderLayout.CENTER);
        loginButton.addActionListener(e -> {
            try {
                String email = emailbox.getText();
                String passord = passwordbox.getText();
                String response = utils.Validator.validateEmail(email, passord);
                Validator.Response r = JsonParser.fromJson(response, Validator.Response.class);
                    centerPanel.removeAll();

                    switch (r.getState().toLowerCase()) {
                    case "manager":
                        Factory factory = new Factory(FileUtils.readProductLines(),new Warehouse(FileUtils.readItems(),FileUtils.readProducts()));
                        makeSaveOnClose(frame, factory);
                        centerPanel.add(new CenterManager(centerPanel, frame ,factory));
                        break;
                    case "supervisor":
                        Factory factory2 = new Factory(FileUtils.readProductLines(),new Warehouse(FileUtils.readItems(),FileUtils.readProducts()));
                        makeSaveOnClose(frame, factory2);
                        centerPanel.add(new CenterSupervisor(centerPanel, frame ,factory2));
                        break;
                    case "signup":
                        centerPanel.add(new CenterSignup(centerPanel, frame ,email, passord));
                        break;
                }
                
                    centerPanel.revalidate();
                    centerPanel.repaint();
                    JOptionPane.showMessageDialog(null, r.getMessage());
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",3);
            }
        });
    }
    public void makeSaveOnClose (JFrame frame, Factory factory) {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try{
                int response = JOptionPane.showConfirmDialog(frame, "Do you want to save before exiting?", "Confirm Exit",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    FileUtils.saveItems(factory);
                    FileUtils.saveProductLines(factory);
                    FileUtils.saveProducts(factory);
                    System.exit(0);
                } else if (response == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(),"Error",3);                
            }
            }
        });
    }
}
/*

*/
/*
git pull origin main
git push -u origin MKs-branch
*/