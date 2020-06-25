package com.circuitry.utils;

import java.awt.*;

public class DrawUtils {
    public static final int menuWidth = 200;
    public static final int menuHeight = 1080;

    public static void clscr (Graphics2D g, Color c, Dimension size) {
        g.setColor(c);
        g.fillRect(0, 0, size.width, size.height);
    }

    public static void drawMenu (Graphics2D g, Color c, int stage) {
        g.setColor(c);
        g.fillRect(0,0, stage, menuHeight);
    }
}
