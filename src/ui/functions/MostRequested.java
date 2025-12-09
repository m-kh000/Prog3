package ui.functions;
import javax.swing.*;
import java.awt.*;
import core.Factory;

public class MostRequested extends FunctionPanel {
    public MostRequested(JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(2, 1, 20, 20));
        add(createBackButton(frame, factory, "supervisor"));
        add(new JLabel("Most Requested"));
    }
}
