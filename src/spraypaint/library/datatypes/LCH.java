package library.datatypes;

import library.util.LABoratory;

public class LCH {
    private final double l;
    private final double c;
    private final double h;

    public LCH(double l, double c, double h) {
        this.l = l;
        this.c = c;
        this.h = h;
    }

    @Override
    public String toString() {
        return "LCH{" +
                "l=" + l +
                ", c=" + c +
                ", h=" + h +
                '}';
    }

    public double getL() {
        return l;
    }

    public double getC() {
        return c;
    }

    public double getH() {
        return h;
    }

    public LAB toLAB() {
        return new LAB(this.l, LABoratory.a(this), LABoratory.b(this));
    }
}
