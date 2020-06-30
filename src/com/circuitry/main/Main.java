package com.circuitry.main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame jf = new JFrame ("circuitry");
        Display disp = new Display (Toolkit.getDefaultToolkit().getScreenSize());
        jf.add(disp);
        jf.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

        Thread.sleep(100);
        disp.start ();
    }
}
