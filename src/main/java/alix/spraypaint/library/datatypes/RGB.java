package alix.spraypaint.library.datatypes;

import alix.spraypaint.library.util.LABoratory;

public class RGB {
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
        return "RGB{" + red + "," + green + "," + blue + '}';
    }

    public RGB float01toInt255() {
        this.red = LABoratory.clipQuant(this.red);
        this.green = LABoratory.clipQuant(this.green);
        this.blue = LABoratory.clipQuant(this.blue);
        return this;
    }
}
