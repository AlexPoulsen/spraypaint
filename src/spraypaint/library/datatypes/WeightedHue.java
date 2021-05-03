package library.datatypes;

import java.util.Objects;

public class WeightedHue implements Comparable<WeightedHue> {
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
