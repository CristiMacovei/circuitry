package com.circuitry.core.objects;

import com.circuitry.utils.Position;

public class Switch extends Conductor {
    public final double defaultR = 0.0, defaultE = 0.0;
    public Switch(double r, double e, Position pos) {
        super(pos);
        this.R = defaultR;
        this.E = defaultE;
    }
}
