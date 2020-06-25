package com.circuitry.core.objects;

import com.circuitry.main.EditMenu;
import com.circuitry.utils.Position;

import javax.swing.*;
import java.awt.*;

public class Conductor {
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

    public Color getColor () {
        // todo shade of yellow based on strength of current
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
            isEditMenuDrawn = true;
        }
    }
}
