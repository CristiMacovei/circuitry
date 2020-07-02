package com.circuitry.core.objects;

import com.circuitry.utils.Position;

import java.awt.*;

public class Source extends Conductor {
    final double defaultE = 5.0; // volts
    final double defaultR = .5; // ohms

    public Source (Position pos) {
        super(pos);
        this.R = defaultR;
        this.E = defaultE;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(this.getColor());
        g.drawOval(this.pos.x, this.pos.y, this.size, this.size);
        g.drawLine(this.pos.x+size/4, this.pos.y+size/2, this.pos.x+3*size/4, this.pos.y+size/2);
        g.drawLine(this.pos.x+3*size/4, this.pos.y+size/2, this.pos.x+3*size/4-(int)(size/4*Math.sin(45)), this.pos.y+size/2-(int)(size/4*Math.sin(45)));
        g.drawLine(this.pos.x+3*size/4, this.pos.y+size/2, this.pos.x+3*size/4-(int)(size/4*Math.sin(45)), this.pos.y+size/2+(int)(size/4*Math.sin(45)));
    }
}
