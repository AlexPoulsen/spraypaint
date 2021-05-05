package alix.spraypaint.library;

import alix.spraypaint.library.datatypes.RGB;

import java.util.Arrays;

public class RGBPalette {
    RGB[] colors;

    public RGBPalette(RGB[] colors) {
        this.colors = colors;
    }

    public RGBPalette(int length) {
        this.colors = new RGB[length];
    }

    public RGBPalette(String colors) {
        colors = colors.replace(", ", ",");
        String[] colorArr = colors.split(",");
        this.colors = new RGB[colorArr.length];
        for (int i = 0; i < colorArr.length; i++) {
            this.colors[i] = new RGB(colorArr[i]);
        }
    }

    @Override
    public String toString() {
        String out = "RGBPalette{" +
                "colors=" + Arrays.toString(colors) +
                '}';
        return out.replace("RGB", "\nRGB");
    }

    public RGB get(int index) {
        return this.colors[index];
    }

    public int length() {
        return this.colors.length;
    }

    public void set(int index, RGB color) {
        this.colors[index] = color;
    }
}
