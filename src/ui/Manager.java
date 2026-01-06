package ui;

import java.awt.Color;
import java.awt.Font;

public class Manager {
    private static final String FONT_NAME = "Arial";
    private static final int NORMAL_SIZE = 16;
    private static final int SUPER_BIG_SIZE = 32;
    private static final int BIG_SIZE = 28;
    private static final Color BG_COLOR = new Color(240, 240, 240);
    public static boolean isEdited = false;
    public static boolean autorefresh = false;
    
    public static Font defaultFont(boolean isBold, boolean isBig) {
        int style = isBold ? Font.BOLD : Font.PLAIN;
        int size = isBig ? SUPER_BIG_SIZE : NORMAL_SIZE;
        return new Font(FONT_NAME, style, size);
    }
    public static Font defaultFont(boolean isBold, boolean isBig, String minbig) {
        int style = isBold ? Font.BOLD : Font.PLAIN;
        int size = isBig ? BIG_SIZE : NORMAL_SIZE;
        return new Font(FONT_NAME, style, size);
    }
    
    public static Font hintFont() {
        return new Font(FONT_NAME, Font.PLAIN, 10);
    }
    
    public static Color defaultBGColor() {
        return BG_COLOR;
    }
}