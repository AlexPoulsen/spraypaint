package alix.spraypaint.library.datatypes;

import alix.spraypaint.library.util.Huetilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class Hue implements BaseHue {
    public static final StaticHue RED = new StaticHue(0);
    public static final StaticHue ORANGE = new StaticHue(30);
    public static final StaticHue YELLOW = new StaticHue(60);
    public static final StaticHue GREEN = new StaticHue(120);
    public static final StaticHue CYAN = new StaticHue(180);
    public static final StaticHue BLUE = new StaticHue(240);
    public static final StaticHue PURPLE = new StaticHue(270);
    public static final StaticHue MAGENTA = new StaticHue(300);
    public static final StaticHue PINK = new StaticHue(330);
    private double hue;

    public Hue(double hue) {
        this.hue = hue;
    }

    public double get() {
        return this.hue;
    }

    public void setAdd(double value) {
        this.hue = Huetilities.hueAdd(this.hue, value);
    }

    public void setAdd(BaseHue other) {
        this.hue = Huetilities.hueAdd(this.hue, other.get());
    }

    public Hue getAdd(double value) {
        return new Hue(Huetilities.hueAdd(this.hue, value));
    }

    public Hue getAdd(BaseHue other) {
        return new Hue(Huetilities.hueAdd(this.hue, other.get()));
    }

    public void setAverage(double value) {
        this.hue = Huetilities.hueAverage(this.hue, value);
    }

    public void setAverage(BaseHue other) {
        this.hue = Huetilities.hueAverage(this.hue, other.get());
    }

    public void setAverage(double value, double weightOther) {
        this.hue = Huetilities.hueAverage(this.hue, value, weightOther);
    }

    public void setAverage(BaseHue other, double weightOther) {
        this.hue = Huetilities.hueAverage(this.hue, other.get(), weightOther);
    }

    public void setAverage(HueSet values) {
        this.hue = Huetilities.hueAverage(values.add(this.hue));
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
        return new Hue(Huetilities.hueAverage(values.prepend(this.hue)));
    }

    public Hue getAverage(HueSet values, double weightThis) {
        return new Hue(Huetilities.hueAverage(values.prepend(this.hue, weightThis)));
    }

    public static Hue average(HueSet values) {
        return new Hue(Huetilities.hueAverage(values));
    }

    public boolean equals(double value) {
        return Huetilities.hueEquals(this.hue, value);
    }

    public boolean equals(BaseHue other) {
        return Huetilities.hueEquals(this.hue, other.get());
    }

    @Override
    public String toString() {
        return "Hue{" +
                "hue=" + hue +
                '}';
    }

    public double getRadians() {
        return Math.toRadians(this.hue);
    }

    public Hue setFromRadians(double hue) {
        this.hue = Math.toDegrees(hue);
        return this;
    }

    public Hue setHue(double hue) {
        this.hue = hue;
        return this;
    }

    public Hue setHue(Hue hue) {
        this.hue = hue.get();
        return this;
    }
}
