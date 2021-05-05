package alix.spraypaint.library.datatypes;

public class AB {
    public double A;
    public double B;

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
