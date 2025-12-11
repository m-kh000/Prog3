package ui;

import java.awt.Font;
import java.awt.Color;

public class Manager {
    private static final String FONT_NAME = "Arial";
    private static final int NORMAL_SIZE = 16;
    private static final int BIG_SIZE = 30;
    private static final Color BG_COLOR = new Color(240, 240, 240);
    
    public static Font defaultFont(boolean isBold, boolean isBig) {
        int style = isBold ? Font.BOLD : Font.PLAIN;
        int size = isBig ? BIG_SIZE : NORMAL_SIZE;
        return new Font(FONT_NAME, style, size);
    }
    
    public static Color defaultBGColor() {
        return BG_COLOR;
    }
}