package ui.functions;

import javax.swing.*;
import java.awt.*;
import core.Factory;

public class ViewPerformance extends FunctionPanel {

    public ViewPerformance(JPanel centerPanel, JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(2, 1, 20, 20));
        add(BackBtn(centerPanel, frame, factory, "manager"));
        add(new JLabel("View Performance"));
    }
}
