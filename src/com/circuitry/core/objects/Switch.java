package com.circuitry.core.objects;

import com.circuitry.utils.Position;

import java.awt.*;

public class Switch extends Conductor {
    public boolean open = false;
    public Switch(Position pos) {
        super(pos);
        this.E = 0.0;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(this.getColor());
        if (!open)
            g.drawLine(this.pos.x, this.pos.y+this.size/2, this.pos.x+this.size, this.pos.y+this.size/2);
        else
            g.drawLine(this.pos.x, this.pos.y+this.size/2, this.pos.x+this.size, this.pos.y);
    }
}
