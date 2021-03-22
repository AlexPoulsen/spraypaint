package library;

class HC {
    double H;
    double C;

    public HC(double h, double c) {
        this.H = h;
        this.C = c;
    }

    public double getH() {
        return this.H;
    }

    public double getC() {
        return this.C;
    }
}

class AB {
    double A;
    double B;

    public AB(double a, double b) {
        this.A = a;
        this.B = b;
    }

    public double getA() {
        return this.A;
    }

    public double getB() {
        return this.B;
    }

    public AB normalize() {
        double divisor = Math.sqrt(this.A * this.A + this.B * this.B);
        this.A = this.A / divisor;
        this.B = this.B / divisor;
        return this;
    }
}

class LAB {
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

class RGB {
    private double red;
    private double green;
    private double blue;

    public RGB(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public RGB(String hex) {
        hex = hex.replace("#", "");
        hex = hex.replace("0x", "");
        this.red = Integer.parseInt(hex.substring(0, 2), 16);
        this.green = Integer.parseInt(hex.substring(2, 4), 16);
        this.blue = Integer.parseInt(hex.substring(4, 6), 16);
    }

    public double getR() {
        return red;
    }

    public double getG() {
        return green;
    }

    public double getB() {
        return blue;
    }

    @Override
    public String toString() {
        return "RGB{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }

    public RGB float01toInt255() {
        this.red = LABoratory.clipQuant(this.red);
        this.green = LABoratory.clipQuant(this.green);
        this.blue = LABoratory.clipQuant(this.blue);
        return this;
    }
}


class LC {
    double L;
    double C;

    public LC(double l, double c) {
        this.L = l;
        this.C = c;
    }

    public double getL() {
        return this.L;
    }

    public double getC() {
        return this.C;
    }
}


class LCH {
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

public class Datatypes {
}
