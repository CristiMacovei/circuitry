package com.circuitry.utils;

import com.circuitry.core.objects.Conductor;
import com.circuitry.main.Display;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

public class Cursor implements MouseListener, MouseMotionListener {
    public Conductor carrying = null;
    int cursorX = 0, cursorY = 0;
    public int searching = 0;
    public Vector<Conductor> searchedConductors = new Vector<>();

    public Position getMousePosition () {
        return new Position(cursorX, cursorY);
    }

    public int getX () {
        return cursorX;
    }

    public int getY () {
        return cursorY;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.printf("mouse clicked at coords x = %d, y = %d\n", mouseEvent.getX(), mouseEvent.getY());
        if (Display.core.calculated = true)
            Display.core.calculated = false;
        if (this.searching != 0) {
            Conductor cond = Display.core.getComponentAt(getMousePosition());
            if (cond != null && (this.searching != 1 || this.searchedConductors.firstElement() != cond)) {
                -- this.searching;
                searchedConductors.add(cond);
                System.err.printf("searching conductor added : %s\n", cond.toString());
                if (this.searching == 0) {
                    Conductor c1 = searchedConductors.firstElement();
                    Conductor c2 = searchedConductors.lastElement();
                    if (c1.connectionLeft == null) {
                        if (c2.connectionLeft == null) {
                            c1.connectionLeft = c2;
                            c2.connectionLeft = c1;
                        } else if (c2.connectionRight == null) {
                            c1.connectionLeft = c2;
                            c2.connectionRight = c1;
                        } else  {
                            System.err.println ("conductor " + c2 + " already has 2 connections inside it");
                            return;
                        }
                    } else if (c1.connectionRight == null) {
                        if (c2.connectionLeft == null) {
                            c1.connectionRight = c2;
                            c2.connectionLeft = c1;
                        } else if (c2.connectionRight == null) {
                            c1.connectionRight = c2;
                            c2.connectionRight = c1;
                        } else  {
                            System.err.println ("conductor " + c2 + " already has 2 connections inside it");
                            return;
                        }
                    } else  {
                        System.err.println ("conductor " + c1 + " already has 2 connections inside it");
                        return;
                    }
                    System.err.println("conductor " + c1 + " left: " + c1.connectionLeft + " right: " + c1.connectionRight);
                    System.err.println("conductor " + c2 + " left: " + c2.connectionLeft + " right: " + c2.connectionRight);
                    searchedConductors.clear();
                }
            }
        }
        if (this.carrying != null) {
            System.out.println("trying to add object, other objects are: ");
            Position mousePosition = getMousePosition();
            if (Display.core.canAdd(mousePosition)) {
                Display.core.add_component(this.carrying);
                this.carrying = null;
            } else {
                System.err.println ("could not add component at specified location -- space already occupied");
            }
        } else {
            // mouse carrying nothing clicks, check if inside bounding box
            if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                Conductor conductor = Display.core.getComponentAt(getMousePosition());
                if (conductor != null) {
                    System.err.println("trying to edit features of component " + conductor);
                    conductor.isEditMenuActive = true;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        cursorX = mouseEvent.getX();
        cursorY = mouseEvent.getY();
        if (this.carrying != null) {
            this.carrying.pos.x = cursorX;
            this.carrying.pos.y = cursorY;
        }
//        System.out.printf("mouse position: x=%d, y=%d\n", mouseEvent.getX(), mouseEvent.getY());
    }
}
