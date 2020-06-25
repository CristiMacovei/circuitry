package com.circuitry.core.objects;

import com.circuitry.utils.Position;

import java.awt.*;

public class Resistor extends Conductor {
    public final double defaultE = 0.0, defaultR = 1.0;

    public Resistor (Position pos) {
        super(pos);
        this.R = defaultR;
        this.E = defaultE;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(this.getColor());
        g.drawRect(this.pos.x, this.pos.y, this.size, this.size/2);
    }
}
