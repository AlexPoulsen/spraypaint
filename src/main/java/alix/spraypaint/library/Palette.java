package alix.spraypaint.library;

import alix.spraypaint.library.datatypes.HC;
import alix.spraypaint.library.datatypes.Hue;
import alix.spraypaint.library.datatypes.HueSet;
import alix.spraypaint.library.datatypes.RGB;

import java.util.function.Function;

public class Palette {
    Color[] colors;
    Hue baseHue;
    double hueSpreadBlueAmount;
    double hueSpreadYellowAmount;
    double hueJitterA;
    double hueJitterB;
    double hueJitterC;
    double hueJitterAmount;
    double hueNonlinSpread;
    double hueSincSineAmount;
    double hueSincSineChromaAmount;
    double hueSincSinePosition;

    double baseChroma;
    double chromaSpreadDark;
    double chromaSpreadLight;

    double baseLuminance;
    double luminanceSpread;

    int colorCount;

    public Hue getBaseHue() {
        return this.baseHue;
    }

    public double getHueSpreadBlueAmount() {
        return this.hueSpreadBlueAmount;
    }

    public double getHueSpreadYellowAmount() {
        return this.hueSpreadYellowAmount;
    }

    public double getHueJitterA() {
        return this.hueJitterA;
    }

    public double getHueJitterB() {
        return this.hueJitterB;
    }

    public double getHueJitterC() {
        return this.hueJitterC;
    }

    public double getHueJitterAmount() {
        return this.hueJitterAmount;
    }

    public double getHueNonlinSpread() {
        return this.hueNonlinSpread;
    }

    public double getHueSincSineAmount() {
        return this.hueSincSineAmount;
    }

    public double getHueSincSineChromaAmount() {
        return this.hueSincSineChromaAmount;
    }

    public double getHueSincSinePosition() {
        return this.hueSincSinePosition;
    }

    public double getBaseChroma() {
        return this.baseChroma;
    }

    public double getChromaSpreadDark() {
        return this.chromaSpreadDark;
    }

    public double getChromaSpreadLight() {
        return this.chromaSpreadLight;
    }

    public double getBaseLuminance() {
        return this.baseLuminance;
    }

    public double getLuminanceSpread() {
        return this.luminanceSpread;
    }

    public int getColorCount() {
        return this.colorCount;
    }

    private double hueNonlin(double x, double amount) {
        double paren1 = ((x - 4) * amount) / 20;
        return Math.signum(x - 4) * paren1 * paren1 * paren1 * paren1;
    }

    private double hueNonlinRootSinTanh(double x, double amount, int colorCount) {
        if (x == 0 || x == colorCount - 1) {
            return 0;
        }
        double tanh = Math.tanh(1.75 * ((x / (colorCount - 1)) - 0.5)) * 2.518125;  // number chosen to be zero at the endpoints, zero and colorCount, at even very large sizes
        return amount * Math.signum(tanh) * Math.sqrt(Math.sin(tanh * tanh));
    }

    private double edgeReduce(double x, double amount) {
        double offset = -4 * amount + 6;
        double lower = Math.tanh(x + offset - 4);
        double upper = Math.tanh(x - offset - 4);
        return ((upper * lower) + 1) / 2;
    }

    private double sincSine(double x, double position) {
        double sine = Math.sin(Math.PI * x - 0.5 * Math.PI);
        double sincWindow = 1;
        if ((x - position) != 0) {
            sincWindow = Math.sin(x - position) / (x - position);
        }
        return sine * sincWindow * sincWindow * sincWindow * sincWindow;
    }

    private HC sincSine2(double x, double position, int colorCount) {
        double sine = Math.sin(Math.PI * (x - 0.5));
        double sincWindow = 1;
        double mult = 1.5;
        double posAdj = position * colorCount;
        if ((x - posAdj) != 0) {
            double temp = Math.sin(mult * (x - posAdj)) / (mult * (x - posAdj));
            sincWindow = Math.tanh(4 * temp * temp * temp);
        }
        double windowPow4 = sincWindow * sincWindow * sincWindow * sincWindow;
        return new HC(sine * windowPow4, windowPow4);
    }

    private double basicSlope(double x, double slope, double centerPos) {
        return slope * (x - centerPos);
    }

    private double basicSlopeDynamic(double x, double slope, int colorCountMinus1) {
//        int c = colorCount - 1;
//        (x - (c / 2.0)) * (slope / c);
        return ((x / colorCountMinus1) - 0.5) * slope;
    }

    private double basicSlopeNonlin(double x, double position, double slope) {
        double xdiv = x / 4;
        return (slope * xdiv * xdiv * 2.1) + 0.05 - slope * 3;
    }

    private double basicSlopeCurvedNegative(double x, double position, double slope) {
        double x2_4 = 2.4 * (x - position);
        double curve = (0.7 * (x - position)) + (0.01 * x2_4 * x2_4) + (0.00012 * x2_4 * x2_4 * x2_4);
        if ((x - position) < 0) {
            return curve * slope;
        } else {
            return (x - position) * slope;
        }
    }

    public double softishClip(double x) {
        double a = (2 * x) - 1;
        double curve = (Math.pow(Math.abs(Math.tanh(a * a * a * a * a)), 0.2) + 1) / 2;
        double sign = Math.signum(-x + 0.5);
        return (sign / 2) + (curve * -sign) + 0.5;
    }

    private double basicSlopeNonlinTanh(double x, double position, double slope) {
        return slope * Math.tanh((x - position) / 3) * 5;
    }

    private HC hueShift(double x, Hue hue, double blueAmount, double yellowAmount, double centerPos) {
        return hueShift(x, hue.get(), blueAmount, yellowAmount, centerPos);
    }

    private HC hueShift(double x, double hue, double blueAmount, double yellowAmount, double centerPos) {
        final double yellow = 110;  // for hsv/l use 60
        final double blue = 260;  // for hsv/l use 240
//        double xms = (x - 4) * (x - 4);
        double xm = (x - centerPos);
        double yVal = -100_000_000;
        double bVal = -100_000_000;
        if (Math.round(hue * 100.0) / 100.0 == yellow) {
            yVal = yellow;  // no change for yellow side
//            bVal = ((20.5 - xms) * hue + xms * blue) / 20.5;  // blue function 1  --  curved
            bVal = ((centerPos - xm) * hue + xm * blue) / centerPos;  // blue function 1  ||||  straight
        } else if (Math.round(hue * 100.0) / 100.0 == blue) {
//            yVal = ((20.5 - xms) * hue + xms * yellow) / 20.5;  // yellow function 1  --  curved
            yVal = ((centerPos - xm) * hue + xm * yellow) / centerPos;  // yellow function 1  ||||  straight
            bVal = blue;  // no change for blue side
        } else if ((hue < blue) && (hue > yellow)) {
            // light goes down to yellow, dark goes up to blue
//            yVal = ((20.5 - xms) * hue + xms * yellow) / 20.5;  // yellow function 1  --  curved
            yVal = ((centerPos - xm) * hue + xm * yellow) / centerPos;  // yellow function 1  ||||  straight
//            bVal = ((20.5 - xms) * hue + xms * blue) / 20.5;  // blue function 1  --  curved
            bVal = ((centerPos - xm) * hue + xm * blue) / centerPos;  // blue function 1  ||||  straight
        } else if (hue < yellow) {
            // light goes up to yellow, dark goes down to blue. low hue values
//            yVal = ((20.5 - xms) * hue + xms * yellow) / 20.5;  // yellow function 1  --  curved
            yVal = ((centerPos - xm) * hue + xm * yellow) / centerPos;  // yellow function 1  ||||  straight
//            bVal = ((20.5 - xms) * hue + xms * (blue - 360)) / 20.5;  // blue function 2  --  curved
            bVal = ((centerPos - xm) * hue + xm * (blue - 360)) / centerPos;  // blue function 2  ||||  straight
        } else if (hue > blue) {
            // light goes up to yellow, dark goes down to blue. high hue values
//            yVal = ((20.5 - xms) * hue + xms * (yellow + 360)) / 20.5;  // yellow function 2  --  curved
            yVal = ((centerPos - xm) * hue + xm * (yellow + 360)) / centerPos;  // yellow function 2  ||||  straight
//            bVal = ((20.5 - xms) * hue + xms * blue) / 20.5;  // blue function 1  --  curved
            bVal = ((centerPos - xm) * hue + xm * blue) / centerPos;  // blue function 1  ||||  straight
        } else {
            System.out.println("error bad hue value");
        }
        double distanceSpread = 5 + (60 - Math.abs(8 * x - 36));
        double outHue;
        double distance;
        double desatDistance;

        if (x < centerPos) {
            distance = Math.tanh((distanceSpread / Math.abs(hue - yellow)) + (distanceSpread / Math.abs(hue - (yellow + 360))));
            desatDistance = (distance * distance / 2) + distance;
            distance = distance * distance * distance * distance * distance * distance;
            distance = 1 - distance;

            double blueAmountDist = blueAmount * distance;
            double intermediate = ((-bVal * blueAmountDist) + hue * (1 + blueAmountDist));  // straight, invert signs 1 and 3 for curved

            if (intermediate < 0) {
                intermediate += 360;
            } else if (intermediate > 360) {
                intermediate -= 360;
            }
            outHue = intermediate % 360;

        } else if (x > centerPos) {
            distanceSpread = distanceSpread * 1.4;
            distance = Math.tanh((distanceSpread / Math.abs(hue - blue)) + (distanceSpread / Math.abs(hue - (blue - 360))));
            desatDistance = (distance * distance / 2) + distance;
            distance = distance * distance * distance * distance * distance * distance;
            distance = 1 - distance;

            double yellowAmountDist = yellowAmount * distance;
            double intermediate = ((yVal * yellowAmountDist) + hue * (1 - yellowAmountDist));

            if (intermediate < 0) {
                intermediate += 360;
            } else if (intermediate > 360) {
                intermediate -= 360;
            }
            outHue = intermediate % 360;

        } else {
            outHue = hue;
            desatDistance = 0;
        }

        double desatMult = Math.tanh(Math.abs((x - centerPos) / 3));
        desatDistance = softishClip(desatDistance * 2 - 1.5);

        if (x < centerPos) {
            double hueAverageMask = 0;
            return new HC(outHue * (1 - hueAverageMask) + blue * hueAverageMask, 1 - (desatMult * desatDistance * blueAmount));
        } else if (x > centerPos) {
            double hueAverageMask = 0;
            return new HC(outHue * (1 - hueAverageMask) + yellow * hueAverageMask, 1 - (desatMult * desatDistance * yellowAmount));
        } else {
            return new HC(outHue, 1);
        }
    }

    public double hueJitter(double x, double a, double b, double c, double amount) {
        double sine1 = Math.sin((a / 10 + b / 100) * (x + b));
        double sine2 = Math.sin((b / 10 + a / 100) * (x + a));
        return amount * Math.sin(c * (sine1 + sine2));
    }

    public Palette(Hue baseHue,
                   double hueSpreadBlueAmount,
                   double hueSpreadYellowAmount,
                   double hueJitterA,
                   double hueJitterB,
                   double hueJitterC,
                   double hueJitterAmount,
                   double hueNonlinSpread,
                   double hueSincSineAmount,
                   double hueSincSineChromaAmount,
                   double hueSincSinePosition,
                   double baseChroma,
                   double chromaSpreadDark,
                   double chromaSpreadLight,
                   double baseLuminance,
                   double luminanceSpread,
                   int colorCount) {
        this.baseHue = baseHue;
        this.hueSpreadBlueAmount = hueSpreadBlueAmount;
        this.hueSpreadYellowAmount = hueSpreadYellowAmount;
        this.hueJitterA = hueJitterA;
        this.hueJitterB = hueJitterB;
        this.hueJitterC = hueJitterC;
        this.hueJitterAmount = hueJitterAmount;
        this.hueNonlinSpread = hueNonlinSpread;
        this.hueSincSineAmount = hueSincSineAmount;
        this.hueSincSineChromaAmount = hueSincSineChromaAmount;
        this.hueSincSinePosition = hueSincSinePosition;

        this.baseChroma = baseChroma;
        this.chromaSpreadDark = chromaSpreadDark;
        this.chromaSpreadLight = chromaSpreadLight;

        this.baseLuminance = baseLuminance;
        this.luminanceSpread = luminanceSpread;

        this.colorCount = colorCount;
        this.colors = new Color[colorCount];

        double centerPos = (colorCount - 1) / 2.0;
        int colorCountMinus1 = colorCount - 1;

        for (int i = 0; i < colorCount; i++) {
            Hue completeHue = new Hue(baseHue.get());
            HC shift = this.hueShift(i, baseHue, hueSpreadBlueAmount, hueSpreadYellowAmount, centerPos);
            completeHue.setHue(shift.getH());

            completeHue.setAdd(this.hueJitter(i, hueJitterA, hueJitterB, hueJitterC, hueJitterAmount));
            completeHue.setAdd(this.hueNonlinRootSinTanh(i, hueNonlinSpread, colorCount));

            HC sincSine = this.sincSine2(i, hueSincSinePosition, colorCount);
            completeHue.setAdd(sincSine.getH() * hueSincSineAmount);

            double chromaAccumulator = baseChroma * Math.max(Math.min(shift.getC(), 1), 0);
            chromaAccumulator += (sincSine.getC() * hueSincSineChromaAmount * baseChroma);

            double res;
            if (i < centerPos) {
                res = this.basicSlopeDynamic(i, -chromaSpreadDark, colorCountMinus1);
//                chromaAccumulator *= (1 - (res * res));
            } else {
                res = this.basicSlopeDynamic(i, chromaSpreadLight, colorCountMinus1);
            }
            chromaAccumulator *= (1 - (res * res * 3));

            double completeLuminance = baseLuminance;
//            completeLuminance += this.basicSlopeCurvedNegative(i, 4, luminanceSpread, centerPos);
//            completeLuminance += this.basicSlope(i, luminanceSpread, centerPos);
            completeLuminance += this.basicSlopeDynamic(i, luminanceSpread, colorCountMinus1);

            double completeChroma = softishClip(chromaAccumulator * completeLuminance) / 10;

//            System.out.println(softishClip(chromaAccumulator * completeLuminance, centerPos));

            if (Double.isNaN(completeHue.get())) {
                System.out.println("hue NaN");
            }
            if (Double.isNaN(completeChroma)) {
                System.out.println("chroma NaN");
            }
            if (Double.isNaN(completeLuminance)) {
                System.out.println("luma NaN");
            }
            this.colors[i] = new Color(completeHue, completeChroma, completeLuminance);
        }
//        System.out.println();
    }

    public RGB[] getColors() {
        RGB[] out = new RGB[this.colorCount];
        for (int i = 0; i < this.colorCount; i++) {
            out[i] = this.colors[i].OKLABtoRGB();
        }
        return out;
    }

    public RGBPalette getRGBPalette() {
        RGB[] out = new RGB[this.colorCount];
        for (int i = 0; i < this.colorCount; i++) {
            out[i] = this.colors[i].OKLABtoRGB().float01toInt255();
        }
        return new RGBPalette(out);
    }

    public Palette average(Palette other) {
        return new Palette(this.baseHue.getAverage(other.baseHue),
                (this.hueSpreadBlueAmount + other.hueSpreadBlueAmount) / 2,
                (this.hueSpreadYellowAmount + other.hueSpreadYellowAmount) / 2,
                (this.hueJitterA + other.hueJitterA) / 2,
                (this.hueJitterB + other.hueJitterB) / 2,
                (this.hueJitterC + other.hueJitterC) / 2,
                (this.hueJitterAmount + other.hueJitterAmount) / 2,
                (this.hueNonlinSpread + other.hueNonlinSpread) / 2,
                (this.hueSincSineAmount + other.hueSincSineAmount) / 2,
                (this.hueSincSineChromaAmount + other.hueSincSineChromaAmount) / 2,
                (this.hueSincSinePosition + other.hueSincSinePosition) / 2,
                (this.baseChroma + other.baseChroma) / 2,
                (this.chromaSpreadDark + other.chromaSpreadDark) / 2,
                (this.chromaSpreadLight + other.chromaSpreadLight) / 2,
                (this.baseLuminance + other.baseLuminance) / 2,
                (this.luminanceSpread + other.luminanceSpread) / 2,
                Math.max(this.colorCount, other.getColorCount()));
    }

    public Palette average(Palette other, double otherWeight) {
        return new Palette(this.baseHue.getAverage(other.baseHue, otherWeight),
                (1-otherWeight) * this.hueSpreadBlueAmount + otherWeight * other.hueSpreadBlueAmount,
                (1-otherWeight) * this.hueSpreadYellowAmount + otherWeight * other.hueSpreadYellowAmount,
                (1-otherWeight) * this.hueJitterA + otherWeight * other.hueJitterA,
                (1-otherWeight) * this.hueJitterB + otherWeight * other.hueJitterB,
                (1-otherWeight) * this.hueJitterC + otherWeight * other.hueJitterC,
                (1-otherWeight) * this.hueJitterAmount + otherWeight * other.hueJitterAmount,
                (1-otherWeight) * this.hueNonlinSpread + otherWeight * other.hueNonlinSpread,
                (1-otherWeight) * this.hueSincSineAmount + otherWeight * other.hueSincSineAmount,
                (1-otherWeight) * this.hueSincSineChromaAmount + otherWeight * other.hueSincSineChromaAmount,
                (1-otherWeight) * this.hueSincSinePosition + otherWeight * other.hueSincSinePosition,
                (1-otherWeight) * this.baseChroma + otherWeight * other.baseChroma,
                (1-otherWeight) * this.chromaSpreadDark + otherWeight * other.chromaSpreadDark,
                (1-otherWeight) * this.chromaSpreadLight + otherWeight * other.chromaSpreadLight,
                (1-otherWeight) * this.baseLuminance + otherWeight * other.baseLuminance,
                (1-otherWeight) * this.luminanceSpread + otherWeight * other.luminanceSpread,
                Math.max(this.colorCount, other.getColorCount()));
    }

    private static double[] extractDouble(Palette[] paletteArray, Function<Palette, Double> func) {
        double[] out = new double[paletteArray.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = func.apply(paletteArray[i]);
        }
        return out;
    }

    private static int[] extractInt(Palette[] paletteArray, Function<Palette, Integer> func) {
        int[] out = new int[paletteArray.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = func.apply(paletteArray[i]);
        }
        return out;
    }

    private static Hue[] extractHue(Palette[] paletteArray, Function<Palette, Hue> func) {
        Hue[] out = new Hue[paletteArray.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = func.apply(paletteArray[i]);
        }
        return out;
    }

    private static double average(double[] arr) {
        int counter = 0;
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            counter++;
        }
        return sum / counter;
    }

    private static double average(double[] arr, double[] weights) {
        double counter = 0;
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i] * weights[i];
            counter += weights[i];
        }
        return sum / counter;
    }

    private static int max(int[] arr) {
        int highest = 0;
        for (int i : arr) {
            if (i > highest) {
                highest = i;
            }
        }
        return highest;
    }

    public static double[] tilt(double index, double blend, double tilt) {
        double[] out = new double[]{0, 0, 0, 0};
        double full;
        double line = (1 - Math.abs((2 * blend) - 1)) * 3/8;
        if (index > 0.5) {
            full = index * line;
        } else {
            full = ((index - 8) * line) + 3;
        }
        double tilted = (tilt * (full)) + ((1 - tilt) * blend * 3);
        double temp1 = Math.max(Math.min(tilted, 1), 0) % 1;
        double temp2 = (Math.max(Math.min(tilted, 2), 1) - 1) % 1;
        double temp3 = Math.max(Math.min(tilted, 3), 2) - 2;
        out[0] = (1 - temp1) % 1;
        out[1] = temp1 + (1 - temp2) % 1;
        out[2] = temp2 + (1 - temp3) % 1;
        out[3] = temp3;
        return out;
    }

    private static double spreadSine(double x, double spread) {
        return (0.9 * Math.cos((x * Math.PI)/(5 * spread))) + (0.05 * Math.cos((3 * x * Math.PI)/(5 * spread) - Math.PI));
    }

    private static double adjustedSine(double in) {
        return Math.max(0.55 * in + 0.465, 0) / 0.95;
    }

    private static double sineMask(double x, double spread) {
        double temp = ((5 * Math.PI * spread) / 3.15);
        return (Math.signum(-x + temp)*Math.signum(x + temp) / 2) + 0.5;
    }

    private static double spreadMask(double x) {
        return Math.max(Math.tanh(4 * (1 - Math.abs((x - 7.5) / 6.65)) + 0.5), 0);
    }

    public static double[] spreadSineBlend(double x, double spread) {
        double[] out = new double[4];
        out[0] = adjustedSine(spreadSine(x, spread)) * sineMask(x, spread);
        out[1] = adjustedSine(spreadSine(x-5,  spread)) * sineMask(x-5,  spread) * spreadMask(x);
        out[2] = adjustedSine(spreadSine(x-10, spread)) * sineMask(x-10, spread) * spreadMask(x);
        out[3] = adjustedSine(spreadSine(x-15, spread)) * sineMask(x-15, spread);
        return out;
    }

    public Palette average(Palette[] palettes) {
        double[] hueWeights = new double[palettes.length];
        double[] chromaValues = extractDouble(palettes, Palette::getBaseChroma);
        for (int i = 0; i < palettes.length; i++) {
            hueWeights[i] = chromaValues[i] * 4;  // * 4 because chroma values, like the others, are in a weird range
        }
        return new Palette(Hue.average(new HueSet(extractHue(palettes, Palette::getBaseHue), hueWeights)),
                average(extractDouble(palettes, Palette::getHueSpreadBlueAmount)),
                average(extractDouble(palettes, Palette::getHueSpreadYellowAmount)),
                average(extractDouble(palettes, Palette::getHueJitterA)),
                average(extractDouble(palettes, Palette::getHueJitterB)),
                average(extractDouble(palettes, Palette::getHueJitterC)),
                average(extractDouble(palettes, Palette::getHueJitterAmount)),
                average(extractDouble(palettes, Palette::getHueNonlinSpread)),
                average(extractDouble(palettes, Palette::getHueSincSineAmount)),
                average(extractDouble(palettes, Palette::getHueSincSineChromaAmount)),
                average(extractDouble(palettes, Palette::getHueSincSinePosition)),
                average(chromaValues),
                average(extractDouble(palettes, Palette::getChromaSpreadDark)),
                average(extractDouble(palettes, Palette::getChromaSpreadLight)),
                average(extractDouble(palettes, Palette::getBaseLuminance)),
                average(extractDouble(palettes, Palette::getLuminanceSpread)),
                max(extractInt(palettes, Palette::getColorCount)));
    }

    public static Palette average(Palette[] palettes, double[] weights) {
        double[] hueWeights = new double[weights.length];
        double[] chromaValues = extractDouble(palettes, Palette::getBaseChroma);
        for (int i = 0; i < weights.length; i++) {
            hueWeights[i] = weights[i] * chromaValues[i] * 4;  // * 4 because chroma values, like the others, are in a weird range
        }
        return new Palette(Hue.average(new HueSet(extractHue(palettes, Palette::getBaseHue), hueWeights)),
                average(extractDouble(palettes, Palette::getHueSpreadBlueAmount), weights),
                average(extractDouble(palettes, Palette::getHueSpreadYellowAmount), weights),
                average(extractDouble(palettes, Palette::getHueJitterA), weights),
                average(extractDouble(palettes, Palette::getHueJitterB), weights),
                average(extractDouble(palettes, Palette::getHueJitterC), weights),
                average(extractDouble(palettes, Palette::getHueJitterAmount), weights),
                average(extractDouble(palettes, Palette::getHueNonlinSpread), weights),
                average(extractDouble(palettes, Palette::getHueSincSineAmount), weights),
                average(extractDouble(palettes, Palette::getHueSincSineChromaAmount), weights),
                average(extractDouble(palettes, Palette::getHueSincSinePosition), weights),
                average(chromaValues, weights),
                average(extractDouble(palettes, Palette::getChromaSpreadDark), weights),
                average(extractDouble(palettes, Palette::getChromaSpreadLight), weights),
                average(extractDouble(palettes, Palette::getBaseLuminance), weights),
                average(extractDouble(palettes, Palette::getLuminanceSpread), weights),
                max(extractInt(palettes, Palette::getColorCount)));
    }

//    public static Palette average(Palette[] palettes, double index, double tilt) {
//        return new Palette(Hue.average(new HueSet(extractHue(palettes, Palette::getBaseHue), weights)),
//                average(extractDouble(palettes, Palette::getHueSpreadBlueAmount), weights),
//                average(extractDouble(palettes, Palette::getHueSpreadYellowAmount), weights),
//                average(extractDouble(palettes, Palette::getHueJitterA), weights),
//                average(extractDouble(palettes, Palette::getHueJitterB), weights),
//                average(extractDouble(palettes, Palette::getHueJitterC), weights),
//                average(extractDouble(palettes, Palette::getHueJitterAmount), weights),
//                average(extractDouble(palettes, Palette::getHueNonlinSpread), weights),
//                average(extractDouble(palettes, Palette::getHueSincSineAmount), weights),
//                average(extractDouble(palettes, Palette::getHueSincSineChromaAmount), weights),
//                average(extractDouble(palettes, Palette::getHueSincSinePosition), weights),
//                average(extractDouble(palettes, Palette::getBaseChroma), weights),
//                average(extractDouble(palettes, Palette::getChromaSpreadDark), weights),
//                average(extractDouble(palettes, Palette::getChromaSpreadLight), weights),
//                average(extractDouble(palettes, Palette::getBaseLuminance), weights),
//                average(extractDouble(palettes, Palette::getLuminanceSpread), weights));
//    }
}


