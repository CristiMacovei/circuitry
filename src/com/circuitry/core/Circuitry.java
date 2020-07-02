package com.circuitry.core;

import com.circuitry.core.objects.Conductor;
import com.circuitry.core.objects.Switch;
import com.circuitry.utils.Position;

import java.awt.*;
import java.util.Vector;

import static com.circuitry.main.Display.core;

public class Circuitry {
    public Vector<Conductor> conductors =  new Vector<>();
    public boolean calculated = false;

    public void add_component (Conductor conductor) {
        conductors.add(conductor);
    }

    /**
     * method to draw the conductors and the connections between them
     * @param g - graphics Object to draw
     */
    public void draw (Graphics2D g) {
        for (Conductor c: conductors) {
            c.draw(g);
        }
        for (Conductor c: conductors) {
            Conductor left = c.connectionLeft;
            Conductor right = c.connectionRight;
            if (left != null) {
                if (left.connectionLeft == c) {
//                    g.drawLine(c.pos.x, c.pos.y+c.size/2, left.pos.x, left.pos.y+left.size/2);
                    int x = Math.min(c.pos.x, left.pos.x) - 20;
                    g.drawLine(c.pos.x, c.pos.y+c.size/2, x, c.pos.y + c.size/2);
                    g.drawLine(x, c.pos.y+c.size/2, x, left.pos.y+left.size/2);
                    g.drawLine(x, left.pos.y+left.size/2, left.pos.x, left.pos.y+left.size/2);
                } else {
                    if (c.pos.x < left.pos.x) {
                        g.drawLine(c.pos.x,c.pos.y+c.size/2, left.pos.x+left.size+20,c.pos.y+c.size/2);
                        g.drawLine(left.pos.x+left.size+20,c.pos.y+c.size/2, left.pos.x+left.size+20, left.pos.y+left.size/2);
                        g.drawLine(left.pos.x+left.size+20, left.pos.y+left.size/2, left.pos.x+left.size, left.pos.y+left.size/2);
                    } else {
                        g.drawLine(c.pos.x, c.pos.y + c.size / 2, c.pos.x - 20, c.pos.y + c.size / 2);
                        g.drawLine(c.pos.x - 20, c.pos.y + c.size / 2, c.pos.x - 20, left.pos.y + left.size / 2);
                        g.drawLine(c.pos.x - 20, left.pos.y + left.size / 2, left.pos.x + left.size, left.pos.y + left.size / 2);
                    }
                }

            }
            if (right != null) {
                if (right.connectionRight == c) {
                    int x = Math.max(c.pos.x+c.size, right.pos.x+right.size) + 20;
                    g.drawLine(c.pos.x+c.size, c.pos.y+c.size/2, x, c.pos.y + c.size/2);
                    g.drawLine(x, c.pos.y+c.size/2, x, right.pos.y+right.size/2);
                    g.drawLine(x, right.pos.y+right.size/2, right.pos.x+right.size, right.pos.y+right.size/2);
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

    /**
     * method to remove one singular conductor from the circuit
     * @param conductor = Conductor object to be removed
     */
    public void clear (Conductor conductor) {
        System.out.println("clicked clear");
        try {
            Conductor left = conductor.connectionLeft;
            Conductor right = conductor.connectionRight;
            if (left.connectionLeft == conductor)
                left.connectionLeft = null;
            if (left.connectionRight == conductor)
                left.connectionRight = null;
            if (right.connectionLeft == conductor)
                right.connectionLeft = null;
            if (right.connectionRight == conductor)
                right.connectionRight = null;

        } catch (NullPointerException ignored) {}
        System.err.printf("Conductor %s will be deleted, list of conductors is: \n", conductor.toString());
        core.conductors.remove(conductor);
        for (Conductor c: core.conductors) {
            System.err.println(c);
        }
        conductor.isEditMenuDrawn = false;
        conductor.isEditMenuActive = false;
    }

    /**
     * method to clear the entire circuit
     */
    public void reset () {
        conductors = new Vector<>();
    }

    /**
     * method to calculate the current through the circuit
     */
    public void calculateCircuit () {
        // fixme
        if (conductors.isEmpty()) {
            System.err.println("[Circuit compute failed] circuit is empty -- process cancelled");
            return;
        }
        for (Conductor c: conductors) {
            if (c.connectionLeft == null || c.connectionRight == null) {
                System.err.println("[Circuit compute failed] one conductor has empty connection -- process cancelled");
                return;
            }
            if (c.getClass().getName().equals("com.circuitry.core.objects.Switch") && ((Switch) c).open) {
                System.err.println("[Circuit compute failed] found open switch -- process cancelled");
                return;
            }
        }
        this.calculated = true;
        double ESum = 0, RSum = 0;
        for (Conductor c: conductors) {
            ESum += c.E;
            RSum += c.R;
        }
        System.err.printf("ESum is %f, RSum is %f\n", ESum, RSum);
        double current = ESum / RSum;
        for (Conductor c: conductors) {
            c.I = current;
            c.calculated = true;
        }
    }
}
