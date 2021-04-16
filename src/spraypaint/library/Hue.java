package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

interface BaseHue {
    double get();
    Hue getAdd(double value);
    Hue getAdd(BaseHue other);
    Hue getAverage(double value);
    Hue getAverage(BaseHue other);
    boolean equals(double value);
    boolean equals(BaseHue other);
    String toString();
    double getRadians();
}


class WeightedHue implements Comparable<WeightedHue> {
    double value;
    double weight;

    public WeightedHue(double value, double weight) {
        this.value = value;
        this.weight = weight;
    }

    public double getValue() {
        return this.value;
    }

    public double getWeight() {
        return this.weight;
    }

    public int compareTo(WeightedHue other) {
        return Double.compare(this.value, other.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightedHue that = (WeightedHue) o;
        return Double.compare(that.value, value) == 0 && Double.compare(that.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, weight);
    }

    @Override
    public String toString() {
        return "WeightedHue{" +
                "value=" + value +
                ", weight=" + weight +
                '}';
    }
}


class HueSet {
    private ArrayList<WeightedHue> hues;

    public HueSet(Hue[] hues) {
        this.hues = new ArrayList<WeightedHue>(hues.length + 5);
        for (int i = 0; i < hues.length; i++) {
            this.hues.add(new WeightedHue(hues[i].get(), 1));
        }
    }

    public HueSet(Hue[] hues, double[] weights) {
        this.hues = new ArrayList<WeightedHue>(hues.length + 5);
        for (int i = 0; i < hues.length; i++) {
            this.hues.add(new WeightedHue(hues[i].get(), weights[i]));
        }
    }

    public HueSet(int capacity) {
        this.hues = new ArrayList<WeightedHue>(capacity);
    }

    public HueSet() {
        this.hues = new ArrayList<WeightedHue>(10);
    }

    public HueSet add(double value, double weight) {
        this.hues.add(new WeightedHue(value, weight));
        return this;
    }

    public HueSet add(double value) {
        this.hues.add(new WeightedHue(value, 1f));
        return this;
    }

    public HueSet add(BaseHue hue, double weight) {
        this.hues.add(new WeightedHue(hue.get(), weight));
        return this;
    }

    public HueSet add(BaseHue hue) {
        this.hues.add(new WeightedHue(hue.get(), 1f));
        return this;
    }

    public HueSet prepend(double value, double weight) {
        this.hues.add(0, new WeightedHue(value, weight));
        return this;
    }

    public HueSet prepend(double value) {
        this.hues.add(0, new WeightedHue(value, 1f));
        return this;
    }

    public HueSet prepend(BaseHue hue, double weight) {
        this.hues.add(0, new WeightedHue(hue.get(), weight));
        return this;
    }

    public HueSet prepend(BaseHue hue) {
        this.hues.add(0, new WeightedHue(hue.get(), 1f));
        return this;
    }

    public HueSet sort() {
        Collections.sort(this.hues);
        return this;
    }

    public int size() {
        return this.hues.size();
    }

    public double[] getValues() {
        double[] out = new double[this.hues.size()];
        int i = 0;
        for (WeightedHue h : this.hues) {
            out[i++] = h.getValue();
        }
        return out;
    }

    public double[] getWeights() {
        double[] out = new double[this.hues.size()];
        int i = 0;
        for (WeightedHue h : this.hues) {
            out[i++] = h.getWeight();
        }
        return out;
    }

    @Override
    public String toString() {
        return "HueSet{" +
                "hues=" + this.hues +
                '}';
    }
}


final class Huetilities {
    static double hueAdd(double a, double b) {
        return (a + b) % 360;
    }

    static double hueAverage(double a, double b) {
        if (a < b) {
            double temp = a;
            a = b;
            b = temp;
        }
        if (Math.abs(a - b) <= Math.abs(a - (b + 360))) {
            return ((a + b) / 2) % 360;
        } else {
            return ((a + (b + 360)) / 2) % 360;
        }
    }

    static double hueAverage(double a, double b, double wb) {
        if (a < b) {
            if (Math.abs(b - a) <= Math.abs(b - (a + 360))) {
                return ((b * 2 * wb + a * 2 * (1 - wb)) / 2) % 360;
            } else {
                return ((b * 2 * wb + (a + 360) * 2 * (1 - wb)) / 2) % 360;
            }
        } else {
            if (Math.abs(a - b) <= Math.abs(a - (b + 360))) {
                return ((a * 2 * (1 - wb) + b * 2 * wb) / 2) % 360;
            } else {
                return ((a * 2 * (1 - wb) + (b + 360) * 2 * wb) / 2) % 360;
            }
        }
    }

    static double hueAverage(HueSet hs) {
        hs.sort();
        double[] hues = new double[hs.size()];
        double[] weights = new double[hs.size()];
        System.arraycopy(hs.getValues(), 0, hues, 0, hs.size());
        System.arraycopy(hs.getWeights(), 0, weights, 0, hs.size());
        double greatestDifference = 0;
        for (int a = 0; a < hues.length - 1; a++) {
            double diff = (hues[a + 1] - hues[a]);
            if (diff > greatestDifference) {
                greatestDifference = diff;
            }
        }
        if (greatestDifference <= 180) {
            double sum = 0;
            double divisor = 0;
            for (int a = 0; a < hues.length; a++) {
                sum += hues[a] * weights[a];
                divisor += weights[a];
            }
            return (sum / divisor) % 360;
        }
        double[] arrCopy = new double[hues.length];
        System.arraycopy(hues, 0, arrCopy, 0, hues.length);
        double upperAccumulator = 0;
        double upperCounter = 0;
        for (int a = 0; a < hues.length; a++) {
            if (hues[a] > 180) {
                upperAccumulator += hues[a] * weights[a];
                upperCounter += weights[a];
            }
        }
        if (upperCounter != 0) {
            upperAccumulator /= upperCounter;
        }
        for (int a = 0; a < hues.length; a++) {
            if (hues[a] <= 180) {
                if (Math.abs(upperAccumulator - hues[a]) <= Math.abs(upperAccumulator - (hues[a] + 360))) {
                    arrCopy[a] = hues[a];
                } else {
                    arrCopy[a] = hues[a] + 360;
                }
            } else {
                arrCopy[a] = hues[a];
            }
        }
        double sum = 0;
        double counter = 0;
        for (int a = 0; a < hues.length; a++) {
            sum += arrCopy[a] * weights[a];
            counter += weights[a];
        }
        return (sum / counter) % 360;
    }

    static double[] getAllHues(BaseHue[] arr) {
        double[] out = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            out[i] = arr[i].get();
        }
        return out;
    }

    static boolean hueEquals(double a, double b) {
        return (Math.abs(a - b) < 0.0000001);
    }
}


class StaticHue implements BaseHue{
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
