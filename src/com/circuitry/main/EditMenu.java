package com.circuitry.main;

import com.circuitry.core.objects.Conductor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

public class EditMenu extends JFrame {
    public Conductor conductor;
    public GridBagLayout gbl = new GridBagLayout();
    public GridBagConstraints gbc =  new GridBagConstraints();
    public static final int width = 200;
    public static final int height = 300;
    JTextField RInput = new JTextField ();
    JTextField EInput = new JTextField ();
    JButton enter = new JButton("enter");

    void add (JComponent component, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(component, gbc);
    }

    void add (JComponent component, int x, int y, int dx, int dy) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = dx;
        gbc.gridheight = dy;
        this.add(component, gbc);
    }

    void init () {
        gbl.columnWidths = new int [10];
        Arrays.fill(gbl.columnWidths, 30);
        gbl.rowHeights = new int [10];
        Arrays.fill(gbl.rowHeights, 20);
        gbc.fill = GridBagConstraints.BOTH;
        EInput.setText(Double.toString(conductor.E));
        if (conductor.getClass().getName() != "com.circuitry.core.objects.Source")
            EInput.setEditable(false);
        RInput.setText(Double.toString(conductor.R));
        enter.addActionListener(click -> {
            double newR = conductor.R, newE = conductor.E;
            try {
                newR = Double.parseDouble(RInput.getText());
                newE = Double.parseDouble(EInput.getText());
            } catch (NumberFormatException exc) {
                // user entered some string that is not a number
                System.err.println ("edit non-number entered");
            }
            conductor.E = newE;
            conductor.R = newR;
        });
    }

    public EditMenu (Conductor conductor) {
        super("if u see this u fucked it up somewhere");
        this.conductor = conductor;
        this.setLayout(gbl);
        this.init();
        setTitle(String.format ("edit conductor -- %s", conductor.toString()));
        setLocation(new Point(conductor.pos.x, conductor.pos.y));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                conductor.isEditMenuDrawn = false;
                conductor.isEditMenuActive = false;
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
        setSize(new Dimension(300,200));

        add(EInput,1, 2, 8, 1);
        add(RInput,1, 4, 8, 1);
        add(enter, 4, 6, 3, 1);
    }


}
