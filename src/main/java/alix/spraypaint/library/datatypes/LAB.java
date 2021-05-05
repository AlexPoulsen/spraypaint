package alix.spraypaint.library.datatypes;

import alix.spraypaint.library.util.LABoratory;
import alix.spraypaint.library.datatypes.LCH;

public class LAB {
    private final double l;
    private final double a;
    private final double b;

    public LAB(double l, double a, double b) {
        this.l = l;
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "LAB{" +
                "l=" + l +
                ", a=" + a +
                ", b=" + b +
                '}';
    }

    public double getL() {
        return l;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public LCH toLCH() {
        return new LCH(this.l, LABoratory.chroma(this), LABoratory.hue(this));
    }
}
