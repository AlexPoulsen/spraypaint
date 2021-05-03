package library.datatypes;

public interface BaseHue {
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
