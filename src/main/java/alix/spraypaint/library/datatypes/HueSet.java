package alix.spraypaint.library.datatypes;

import java.util.ArrayList;
import java.util.Collections;

public class HueSet {
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
