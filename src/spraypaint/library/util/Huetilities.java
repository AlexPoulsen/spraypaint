package library.util;

import library.datatypes.BaseHue;
import library.datatypes.HueSet;

public final class Huetilities {
    public static double hueAdd(double a, double b) {
        return (a + b) % 360;
    }

    public static double hueAverage(double a, double b) {
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

    public static double hueAverage(double a, double b, double wb) {
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

    public static double hueAverage(HueSet hs) {
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

    public static double[] getAllHues(BaseHue[] arr) {
        double[] out = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            out[i] = arr[i].get();
        }
        return out;
    }

    public static boolean hueEquals(double a, double b) {
        return (Math.abs(a - b) < 0.0000001);
    }
}
