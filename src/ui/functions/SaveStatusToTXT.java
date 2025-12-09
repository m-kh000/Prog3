package ui.functions;

import javax.swing.*;
import java.awt.*;
import core.Factory;

public class SaveStatusToTXT extends FunctionPanel {

    public SaveStatusToTXT(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(2, 1, 20, 20));
        add(BackBtn(centerPanel, frame, factory, "supervisor"));
        add(new JLabel("Save Status to TXT"));
    }
}
