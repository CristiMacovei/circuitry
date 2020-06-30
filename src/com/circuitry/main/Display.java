package com.circuitry.main;

import com.circuitry.core.Circuitry;
import com.circuitry.core.objects.Conductor;
import com.circuitry.core.objects.Resistor;
import com.circuitry.core.objects.Source;
import com.circuitry.utils.Cursor;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static com.circuitry.utils.DrawUtils.clscr;
import static com.circuitry.utils.DrawUtils.drawMenu;
import static com.circuitry.utils.DrawUtils.menuWidth;

public class Display extends JPanel {
    private Dimension size;
    int menuStage = 0;
    final Color bg = Color.darkGray;
    public static final Color menuColor = new Color(50,50,50);
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    JButton left = new JButton (">");
    JButton left2 = new JButton ("<");
    boolean menu = false;
    // circuitry buttons
    JButton source, resistor, wire;
    JButton calculate = new JButton("calculate");
    JButton clear = new JButton("clear");
    public static Circuitry core = new Circuitry();
    Cursor cursor = new Cursor();

    void buttonStyle (JButton button) {
        button.setBorder(null);
        button.setForeground(menuColor);
        button.setBackground(Color.lightGray);
    }

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
        addMouseListener(cursor);
        addMouseMotionListener(cursor);
        gbl.columnWidths = new int [120];
        Arrays.fill(gbl.columnWidths, 1920/120);
        gbl.rowHeights = new int [120];
        Arrays.fill(gbl.rowHeights, 1080/120);
        gbc.fill = GridBagConstraints.BOTH;

        left.setBackground(bg);
        left.setForeground(Color.lightGray);
        left.setOpaque(false);
        left.setBorder(null);
        left.setFont(new Font("Serif", Font.BOLD,  24));
        left.setFocusable(false);
        left.addActionListener(onclick -> menu = !menu);

        left2.setBackground(bg);
        left2.setForeground(Color.lightGray);
        left2.setOpaque(false);
        left2.setBorder(null);
        left2.setFont(new Font("Serif", Font.BOLD,  24));
        left2.setFocusable(false);
        left2.addActionListener(onclick -> menu = !menu);

        source = new JButton("source");
        buttonStyle(source);
        source.addActionListener(onclick -> {
            cursor.carrying = new Source(cursor.getMousePosition());
            menu = false;
        });

        buttonStyle(calculate);
        calculate.addActionListener(onclick -> {
            core.calculated = false;
            core.calculateCircuit();
        });

        buttonStyle(clear);
        clear.addActionListener(onclick -> core.reset());

        resistor = new JButton("resistor");
        buttonStyle(resistor);
        resistor.addActionListener(onclick -> {
            cursor.carrying = new Resistor(cursor.getMousePosition());
            menu = false;
        });

        wire = new JButton("wire");
        buttonStyle(wire);
        wire.addActionListener(onclick -> {
            if (core.conductors.size() < 2) {
                System.err.println("cannot wire if there are not 2 components at least");
                return;
            }
            cursor.searching = 2;
            menu = false;
        });
    }

    void start () {
        while (true) {
            repaint();
        }
    }

    public Display (Dimension size) throws InterruptedException {
        this.size = size;
        this.setLayout(gbl);

        init();
        add (left, 0, 60, 3, 3);
        add(left2, 10, 60, 3, 3);
        add(source, 4, 10, 5, 3);
        add(resistor, 4, 15, 5, 3);
        add(wire, 4, 20, 5, 3);
        add(calculate, 4, 100, 5, 3);
        add(clear, 4, 105, 5, 3);
    }

    public void paintComponent (Graphics gobj) {
        super.paintComponent(gobj);
        Graphics2D g = (Graphics2D) gobj;

        for (Conductor conductor: core.conductors)
            conductor.calculated = core.calculated;

        clscr(g, bg, size);
        if (cursor.carrying != null) {
            cursor.carrying.draw(g);
        }

        if (menu) {
            drawMenu (g, menuColor, menuStage/2);
            left.setVisible(false);
            if (menuStage == menuWidth*2) {
                left2.setVisible(true);
                source.setVisible(true);
                resistor.setVisible(true);
                wire.setVisible(true);
                calculate.setVisible(true);
                clear.setVisible(true);
            } else {
                if (menuStage < 2*menuWidth)
                    menuStage ++;
            }
        } else {
            left2.setVisible(false);
            source.setVisible(false);
            resistor.setVisible(false);
            wire.setVisible(false);
            calculate.setVisible(false);
            clear.setVisible(false);
            if (menuStage > 0) {
                -- menuStage;
                drawMenu(g, menuColor, menuStage/2);
            } else
                left.setVisible(true);
        }
        core.draw(g);
    }
}
