package com.circuitry.main;

import com.circuitry.core.objects.Conductor;
import com.circuitry.core.objects.Source;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.Arrays;

public class EditMenu extends JFrame {
    public static final Color bgColor = Color.gray;
    public static final Color labelColor = Color.lightGray;
    public Conductor conductor;
    public GridBagLayout gbl = new GridBagLayout();
    public GridBagConstraints gbc =  new GridBagConstraints();
    public static final int width = 320;
    public static final int height = 300;
    JTextField RInput = new JTextField ();
    JTextField EInput = new JTextField ();
    JTextField leftInput = new JTextField ();
    JTextField rightInput = new JTextField ();
    JTextField IValue = new JTextField();
    JTextField ILabel = new JTextField(" I : ");
    JTextField RLabel = new JTextField (" R :  ");
    JTextField ELabel = new JTextField (" E :  ");
    JTextField leftLabel = new JTextField (" left :  ");
    JTextField rightLabel = new JTextField (" right :  ");
    JButton enter = new JButton("enter");
    JButton clear = new JButton("clear");
    // todo implement clear
    DecimalFormat format3digits = new DecimalFormat("#.000");

    void add (JComponent component, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(component, gbc);
    }

    void jTextFieldStyle (JTextField jtf, boolean editable) {
        jtf.setEditable(editable);
        if (editable) {
            jtf.setBackground(labelColor);
            jtf.setForeground(Color.black);
            jtf.setBorder(BorderFactory.createLineBorder(labelColor, 1, true));
        } else {
            jtf.setBackground(bgColor);
            jtf.setForeground(Color.black);
            jtf.setBorder(null);
            jtf.setOpaque(false);
        }
    }

    void jButtonStyle (JButton jb) {
        jb.setBackground(labelColor);
        jb.setForeground(Color.black);
        jb.setBorder(BorderFactory.createLineBorder(labelColor, 1, true));
    }

    void add (JComponent component, int x, int y, int dx, int dy) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = dx;
        gbc.gridheight = dy;
        this.add(component, gbc);
    }

    void init () {
        // fixme removing connection doesn't work nobody will ever know why
        gbl.columnWidths = new int [16];
        Arrays.fill(gbl.columnWidths, 20);
        gbl.rowHeights = new int [15];
        Arrays.fill(gbl.rowHeights, 20);
        gbc.fill = GridBagConstraints.BOTH;
        jTextFieldStyle(EInput, true);
        jTextFieldStyle(RInput, true);
        jTextFieldStyle(leftInput, true);
        jTextFieldStyle(rightInput, true);
        jTextFieldStyle(IValue, true);
        IValue.setEditable(false);
        jTextFieldStyle(ELabel, false);
        jTextFieldStyle(RLabel, false);
        jTextFieldStyle(leftLabel, false);
        jTextFieldStyle(rightLabel, false);
        jTextFieldStyle(ILabel, false);
        jButtonStyle(enter);
        EInput.setText(format3digits.format(conductor.E));
        if (conductor.getClass().getName() != "com.circuitry.core.objects.Source")
            EInput.setEditable(false);
        RInput.setText(format3digits.format(conductor.R));
        IValue.setText(format3digits.format(conductor.I));
        leftInput.setText(Boolean.toString(conductor.connectionLeft != null));
        rightInput.setText(Boolean.toString(conductor.connectionRight != null));
        enter.addActionListener(click -> {
            double newR = conductor.R, newE = conductor.E;
            try {
                newR = Double.parseDouble(RInput.getText());
                newE = Double.parseDouble(EInput.getText());
                if (leftInput.getText() == "false") {
                    if (conductor.connectionLeft.connectionLeft == conductor)
                        conductor.connectionLeft.connectionLeft = null;
                    else
                        conductor.connectionLeft.connectionRight = null;
                    conductor.connectionLeft = null;
                }
                if (rightInput.getText() == "false") {
                    if (conductor.connectionRight.connectionLeft == conductor)
                        conductor.connectionRight.connectionLeft = null;
                    else
                        conductor.connectionRight.connectionRight = null;
                    conductor.connectionRight = null;
                }
            } catch (NumberFormatException exc) {
                // user entered some string that is not a number
                System.err.println ("edit non-number entered");
            }
            conductor.E = newE;
            conductor.R = newR;
//            this.dispose();
        });
    }

    public EditMenu (Conductor conductor) {
        super("if u see this u fucked it up somewhere");
        setSize(width, height);
        this.getContentPane().setBackground(bgColor);
        this.conductor = conductor;
        this.setLayout(gbl);
        this.init();
        setTitle(String.format ("edit conductor -- %s", conductor.toString()));
        setLocation(new Point(conductor.pos.x, conductor.pos.y));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) { }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                conductor.isEditMenuDrawn = false;
                conductor.isEditMenuActive = false;
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) { }

            @Override
            public void windowIconified(WindowEvent windowEvent) { }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) { }

            @Override
            public void windowActivated(WindowEvent windowEvent) { }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) { }
        });

        add(EInput,4, 2, 8, 1);
        add(RInput,4, 4, 8, 1);
        add(IValue, 4, 6, 8, 1);
        add(leftInput, 4, 8, 8, 1);
        add(rightInput, 4, 10, 8, 1);
        add(enter, 6, 12, 4, 1);
        add(ELabel,1, 2, 2, 1);
        add(RLabel,1, 4, 2, 1);
        add(ILabel, 1, 6, 2, 1);
        add(leftLabel,1, 8, 2, 1);
        add(rightLabel,1, 10, 2, 1);
    }
}
