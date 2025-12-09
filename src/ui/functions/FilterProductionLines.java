package ui.functions;
import javax.swing.*;
import java.awt.*;
import core.Factory;

public class FilterProductionLines extends FunctionPanel {
    public FilterProductionLines(JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(2, 1, 20, 20));
        add(createBackButton(frame, factory, "supervisor"));
        add(new JLabel("Filter Production Lines"));
    }
}
