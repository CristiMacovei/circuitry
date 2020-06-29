package com.circuitry.core.objects;

import com.circuitry.main.EditMenu;
import com.circuitry.utils.Position;

import javax.swing.*;
import java.awt.*;

public class Conductor {
    public boolean calculated = false;
    public double R = 0.0, E = 0.0, I = 0.0;
    public Position pos;
    public int size = 32;
    public boolean isEditMenuActive = false, isEditMenuDrawn = false;
    public Conductor connectionLeft = null, connectionRight = null;

    public String toString () {
        return String.format("object %s: position = {x = %d, y = %d}",this.getClass().getName(), pos.x, pos.y);
    }

    public boolean doBoundingBoxesCollide (Position p) {
        return p.x <= this.pos.x + size && p.x >= this.pos.x - size && p.y <= this.pos.y + size && p.y >= this.pos.y - size;
    }

    public boolean isInsideBoundingBox (Position p) {
        return p.x <= this.pos.x + size && p.x >= this.pos.x && p.y <= this.pos.y + size && p.y >= this.pos.y;
    }

    /**
     * method to give the color of a conductor based on the current that flows through it
     * @return yellow is current is greater than 0, else white
     */
    public Color getColor () {
        if (I == 0.0 || !calculated)
            return Color.white;
        return Color.yellow;
    }

    public Conductor (Position pos) {
        this.pos = pos;
    }

    public void draw (Graphics2D g) { /* just for override */ }

    public void drawEditMenu () {
        if (!isEditMenuDrawn) {
            EditMenu editMenu = new EditMenu (this);
            editMenu.setVisible(true);
            editMenu.setSize(EditMenu.width, EditMenu.height);
            isEditMenuDrawn = true;
        }
    }
}
