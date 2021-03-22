package library;

import java.util.Arrays;

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

    private double hueNonlin(double x, double amount) {
        double paren1 = ((x - 4.5) * amount) / 20;
        return Math.signum(x - 4.5) * paren1 * paren1 * paren1 * paren1;
    }

    private double hueNonlinRootSinTanh(double x, double amount) {
        if (x < 1 || x > 7) {
            return 0;
        }
        double tanh = 3.5 * Math.tanh((0.35 * x - 1.4) / 2.1);  // 1.4 = 0.35 * 4
        return amount * Math.signum(tanh) * Math.sqrt(Math.sin(tanh * tanh));
    }

    private double edgeReduce(double x, double amount) {
        double offset = -4 * amount + 6;
        double lower = Math.tanh(x + offset - 4.5);
        double upper = Math.tanh(x - offset - 4.5);
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

    private HC sincSine2(double x, double position) {
        double sine = Math.sin(Math.PI * (x - 0.5));
        double sincWindow = 1;
        double mult = 1.5;
        if ((x - position) != 0) {
            double temp = Math.sin(mult * (x - position)) / (mult * (x - position));
            sincWindow = Math.tanh(4 * temp * temp * temp);
        }
        double windowPow4 = sincWindow * sincWindow * sincWindow * sincWindow;
        return new HC(sine * windowPow4, windowPow4);
    }

    private double basicSlope(double x, double position, double slope) {
        return slope * (x - position);
    }

    private double basicSlopeNonlin(double x, double position, double slope) {
        double xdiv = x / 4.5;
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

    private HC hueShift(double x, Hue hue, double blueAmount, double yellowAmount) {
        return hueShift(x, hue.get(), blueAmount, yellowAmount);
    }

    private HC hueShift(double x, double hue, double blueAmount, double yellowAmount) {
        final double yellow = 110;  // for hsv/l use 60
        final double blue = 260;  // for hsv/l use 240
//        double xms = (x - 4.5) * (x - 4.5);
        double xm = (x - 4.5);
        double yVal = -100_000_000;
        double bVal = -100_000_000;
        if (Math.round(hue * 100.0) / 100.0 == yellow) {
            yVal = yellow;  // no change for yellow side
//            bVal = ((20.5 - xms) * hue + xms * blue) / 20.5;  // blue function 1  --  curved
            bVal = ((4.5 - xm) * hue + xm * blue) / 4.5;  // blue function 1  ||||  straight
        } else if (Math.round(hue * 100.0) / 100.0 == blue) {
//            yVal = ((20.5 - xms) * hue + xms * yellow) / 20.5;  // yellow function 1  --  curved
            yVal = ((4.5 - xm) * hue + xm * yellow) / 4.5;  // yellow function 1  ||||  straight
            bVal = blue;  // no change for blue side
        } else if ((hue < blue) && (hue > yellow)) {
            // light goes down to yellow, dark goes up to blue
//            yVal = ((20.5 - xms) * hue + xms * yellow) / 20.5;  // yellow function 1  --  curved
            yVal = ((4.5 - xm) * hue + xm * yellow) / 4.5;  // yellow function 1  ||||  straight
//            bVal = ((20.5 - xms) * hue + xms * blue) / 20.5;  // blue function 1  --  curved
            bVal = ((4.5 - xm) * hue + xm * blue) / 4.5;  // blue function 1  ||||  straight
        } else if (hue < yellow) {
            // light goes up to yellow, dark goes down to blue. low hue values
//            yVal = ((20.5 - xms) * hue + xms * yellow) / 20.5;  // yellow function 1  --  curved
            yVal = ((4.5 - xm) * hue + xm * yellow) / 4.5;  // yellow function 1  ||||  straight
//            bVal = ((20.5 - xms) * hue + xms * (blue - 360)) / 20.5;  // blue function 2  --  curved
            bVal = ((4.5 - xm) * hue + xm * (blue - 360)) / 4.5;  // blue function 2  ||||  straight
        } else if (hue > blue) {
            // light goes up to yellow, dark goes down to blue. high hue values
//            yVal = ((20.5 - xms) * hue + xms * (yellow + 360)) / 20.5;  // yellow function 2  --  curved
            yVal = ((4.5 - xm) * hue + xm * (yellow + 360)) / 4.5;  // yellow function 2  ||||  straight
//            bVal = ((20.5 - xms) * hue + xms * blue) / 20.5;  // blue function 1  --  curved
            bVal = ((4.5 - xm) * hue + xm * blue) / 4.5;  // blue function 1  ||||  straight
        } else {
            System.out.println("error bad hue value");
        }
        double distanceSpread = 5 + (60 - Math.abs(8 * x - 36));
        double outHue;
        double distance;
        double desatDistance;

        if (x < 4) {
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

        } else if (x > 4) {
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

        double desatMult = Math.tanh(Math.abs((x - 4.5) / 3));
        desatDistance = softishClip(desatDistance * 2 - 1.5);

        if (x < 4.5) {
            double hueAverageMask = 0;
            return new HC(outHue * (1 - hueAverageMask) + blue * hueAverageMask, 1 - (desatMult * desatDistance * blueAmount));
        } else if (x > 4.5) {
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
                   double luminanceSpread) {
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
        this.colors = new Color[9];

        for (int i = 1; i < 10; i++) {
            Hue completeHue = new Hue(baseHue.get());

            HC shift = this.hueShift(i-1, baseHue, hueSpreadBlueAmount, hueSpreadYellowAmount);
            completeHue.setHue(shift.getH());

            completeHue.setAdd(this.hueJitter(i-1, hueJitterA, hueJitterB, hueJitterC, hueJitterAmount));
            completeHue.setAdd(this.hueNonlinRootSinTanh(i-1, hueNonlinSpread));

            HC sincSine = this.sincSine2(i, hueSincSinePosition);
            completeHue.setAdd(sincSine.getH() * hueSincSineAmount);

            double completeChroma = baseChroma * Math.max(Math.min(shift.getC(), 1), 0);
            completeChroma += (sincSine.getC() * hueSincSineChromaAmount * baseChroma);

            if (i-1 < 4) {
                completeChroma += this.basicSlope(i-1, 4.5, chromaSpreadDark);
            } else if (i-1 > 4) {
                completeChroma += this.basicSlope(i-1, 4.5, -chromaSpreadLight);
            }

            double completeLuminance = baseLuminance;
            completeLuminance += this.basicSlopeCurvedNegative(i, 4.5, luminanceSpread);

            this.colors[i-1] = new Color(completeHue, softishClip(completeChroma * completeLuminance + 0), completeLuminance);
        }
    }

    public RGB[] getColors() {
        RGB[] out = new RGB[9];
        for (int i = 0; i < 9; i++) {
            out[i] = this.colors[i].OKLABtoRGB();
        }
        return out;
    }

    public RGBPalette getRGBPalette() {
        RGB[] out = new RGB[9];
        for (int i = 0; i < 9; i++) {
            out[i] = this.colors[i].OKLABtoRGB().float01toInt255();
        }
        return new RGBPalette(out);
    }
}


class RGBPalette {
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

    public void set(int index, RGB color) {
        this.colors[index] = color;
    }
}
