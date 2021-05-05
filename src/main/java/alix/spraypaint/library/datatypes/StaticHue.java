package alix.spraypaint.library.datatypes;

import alix.spraypaint.library.util.Huetilities;

public class StaticHue implements BaseHue {
    private final double hue;

    public StaticHue(double hue) {
        this.hue = hue;
    }

    public double get() {
        return this.hue;
    }

    public Hue getAdd(double value) {
        return new Hue(Huetilities.hueAdd(this.hue, value));
    }

    public Hue getAdd(BaseHue other) {
        return new Hue(Huetilities.hueAdd(this.hue, other.get()));
    }

    public Hue getAverage(double value) {
        return new Hue(Huetilities.hueAverage(this.hue, value));
    }

    public Hue getAverage(BaseHue other) {
        return new Hue(Huetilities.hueAverage(this.hue, other.get()));
    }

    public Hue getAverage(double value, double weightOther) {
        return new Hue(Huetilities.hueAverage(this.hue, value, weightOther));
    }

    public Hue getAverage(BaseHue other, double weightOther) {
        return new Hue(Huetilities.hueAverage(this.hue, other.get(), weightOther));
    }

    public Hue getAverage(HueSet values) {
        return new Hue(Huetilities.hueAverage(values.add(this.hue)));
    }

    public boolean equals(double value) {
        return Huetilities.hueEquals(this.hue, value);
    }

    public boolean equals(BaseHue other) {
        return Huetilities.hueEquals(this.hue, other.get());
    }

    @Override
    public String toString() {
        return "StaticHue{" +
                "hue=" + hue +
                '}';
    }

    public double getRadians() {
        return Math.toRadians(this.hue);
    }
}
