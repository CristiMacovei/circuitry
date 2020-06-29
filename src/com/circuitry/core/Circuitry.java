package com.circuitry.core;

import com.circuitry.core.objects.Conductor;
import com.circuitry.utils.Position;

import java.awt.*;
import java.util.Vector;

public class Circuitry {
    public Vector<Conductor> conductors =  new Vector<>();

    public void add_component (Conductor conductor) {
        conductors.add(conductor);
    }

    public void dbg () {
        for (Conductor conductor: conductors)
            System.err.println(conductor);
    }

    public void draw (Graphics2D g) {
        for (Conductor c: conductors) {
            c.draw(g);
        }
        for (Conductor c: conductors) {
            Conductor left = c.connectionLeft;
            Conductor right = c.connectionRight;
            if (left != null) {
                if (left.connectionLeft == c) {
                    g.drawLine(c.pos.x, c.pos.y+c.size/2, left.pos.x, left.pos.y+left.size/2);
                } else {
                    g.drawLine(c.pos.x, c.pos.y+c.size/2, left.pos.x+left.size, left.pos.y+left.size/2);
                }

            }
            if (right != null) {
                if (right.connectionRight == c) {
                    g.drawLine(c.pos.x + c.size, c.pos.y + c.size / 2, right.pos.x + right.size, right.pos.y + right.size / 2);
                } else {
                    g.drawLine(c.pos.x + c.size, c.pos.y + c.size / 2, right.pos.x, right.pos.y + right.size / 2);
                }
            }
        }
        for (Conductor c: conductors) {
            if (c.isEditMenuActive)
                c.drawEditMenu();
        }
    }

    public boolean canAdd (Position pos) {
        for (Conductor conductor: conductors) {
            System.out.println(conductor + ", is inside = " + conductor.doBoundingBoxesCollide(pos));
            if (conductor.doBoundingBoxesCollide(pos))
                return false;
        }
        return true;
    }

    public Conductor getComponentAt (Position pos) {
        for (Conductor conductor: conductors) {
            if (conductor.isInsideBoundingBox(pos))
                return conductor;
        }
        return null;
    }

    public void calculateCircuit () {
        // todo make this thing work lmao
        int ESum = 0, RSum = 0;
        for (Conductor c: conductors) {
            ESum += c.E;
            RSum += c.R;
        }
        double current = (double)ESum / (double)RSum;
        for (Conductor c: conductors) {
            c.I = current;
        }
    }
}
