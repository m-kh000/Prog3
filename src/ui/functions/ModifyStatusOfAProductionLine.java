package ui.functions;
import javax.swing.*;
import java.awt.*;
import core.Factory;

public class ModifyStatusOfAProductionLine extends FunctionPanel {
    public ModifyStatusOfAProductionLine(JFrame frame, core.Factory factory) {
        setLayout(new GridLayout(2, 1, 20, 20));
        add(createBackButton(frame, factory, "manager"));
        add(new JLabel("Modify Status of a Production Line"));
    }
}
