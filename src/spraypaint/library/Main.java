package library;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static java.awt.image.BufferedImage.*;

public class Main {

    public static void main(String[] args) {
//        Integer[] temp = Swap.rotateArr(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8}, -3);
//	    Swap.printArr(temp);
//        double[] ratios = TriData.makeRatios(0, 5, 15);
//        double[] coords = TriData.getTriCoords(TriData.makeRatios(5, 5, 5), 1024);
//        System.out.println(coords[0] + " " + coords[1]);
//        BufferedImage feature = null;
//        try {
//            feature = ImageIO.read(new File("/Users/alix/Documents/art and design/feature1.png"));
//        } catch (IOException e) {
//            System.out.println("image read failed");
//        }
////        Graphics.drawImage(img, 0, 0, null);
//        BufferedImage table = new BufferedImage(1024, TriData.fastEquilateralHeight(1024), TYPE_USHORT_GRAY);
//        PropertyTableUtils.overlayFeature(table, feature, 1, 1, 4, 5);
//        PropertyTableUtils.overlayFeature(table, feature, 1, 3, 1, 1);
//        PropertyTableUtils.overlayFeature(table, feature, 1, 1, 1, 1);
////        PropertyTableUtils.overlayFeature(table, feature, 1, 50, TriData.fastEquilateralCenterHeight(2048));
//        Graphics2D g2d = table.createGraphics();
//        g2d.drawLine(0, 0, 512, TriData.fastEquilateralHeight(1024));
//        g2d.drawLine(512, TriData.fastEquilateralHeight(1024), 1024, 0);
//        g2d.drawLine(1024, 0, 0, 0);
//        g2d.dispose();
//        try {
//            File outputfile = new File("/Users/alix/Documents/art and design/saved.png");
//            ImageIO.write(table, "png", outputfile);
//        } catch (IOException e) {
//            System.out.println("image write failed");
//        }
//        System.out.println(Hue.MAGENTA);
//        System.out.println(Hue.YELLOW);
//        System.out.println(Hue.YELLOW.getAverage(Hue.MAGENTA));
//        System.out.println(Hue.RED);
//        System.out.println(Hue.YELLOW.getAverage(Hue.MAGENTA).equals(Hue.RED));
//        System.out.println(Hue.BLUE);
//        System.out.println(Hue.RED);
//        System.out.println(Hue.BLUE.getAverage(Hue.RED));
//        System.out.println(Hue.MAGENTA);
//        System.out.println(Hue.BLUE.getAverage(Hue.RED).equals(Hue.MAGENTA));
//        System.out.println(Hue.BLUE);
//        System.out.println(Hue.GREEN);
//        System.out.println(Hue.BLUE.getAverage(Hue.GREEN));
//        System.out.println(Hue.CYAN);
//        System.out.println(Hue.BLUE.getAverage(Hue.GREEN).equals(Hue.CYAN));
//        System.out.println(Hue.RED);
//        System.out.println(Hue.YELLOW);
//        System.out.println(Hue.RED.getAverage(Hue.YELLOW));
//        System.out.println(Hue.ORANGE);
//        System.out.println(Hue.RED.getAverage(Hue.YELLOW).equals(Hue.ORANGE));
//        System.out.println(Hue.BLUE);
//        System.out.println(Hue.MAGENTA);
//        System.out.println(Hue.BLUE.getAverage(Hue.MAGENTA));
//        System.out.println(Hue.PURPLE);
//        System.out.println(Hue.BLUE.getAverage(Hue.MAGENTA).equals(Hue.PURPLE));
//        System.out.println(Hue.RED);
//        System.out.println(Hue.MAGENTA);
//        System.out.println(Hue.RED.getAverage(Hue.MAGENTA));
//        System.out.println(Hue.PINK);
//        System.out.println(Hue.RED.getAverage(Hue.MAGENTA).equals(Hue.PINK));
//        System.out.println(Hue.YELLOW);
//        System.out.println(Hue.CYAN);
//        System.out.println(Hue.MAGENTA);
//        System.out.println(Huetilities.hueAverage(new float[]{Hue.YELLOW.get(), Hue.CYAN.get(), Hue.MAGENTA.get()}));
//        System.out.println(Hue.RED);
//        System.out.println(Hue.GREEN);
//        System.out.println(Hue.BLUE);
//        System.out.println(Huetilities.hueAverage(new float[]{Hue.RED.get(), Hue.GREEN.get(), Hue.BLUE.get()}));
//        System.out.println(Huetilities.hueAverage(new float[]{0, 50f, 100f, 150f, 200f}));
//        System.out.println(Huetilities.hueAverage(new float[]{0, 200f}));
//        System.out.println(Huetilities.hueAverage(new float[]{0, 179.99f}));
//        System.out.println(Huetilities.hueAverage(new float[]{0, 180.01f}));
//        System.out.println(Hue.YELLOW);
//        System.out.println(Hue.CYAN);
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0.1f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0.2f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0.3f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0.4f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0.5f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0.6f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0.7f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0.8f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 0.9f));
//        System.out.println(Hue.YELLOW.getAverage(Hue.CYAN, 1f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0.1f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0.2f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0.3f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0.4f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0.5f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0.6f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0.7f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0.8f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 0.9f));
//        System.out.println(Hue.CYAN.getAverage(Hue.YELLOW, 1f));
//        HueSet one = new HueSet(10);
//        one.add(Hue.RED, 10).add(Hue.GREEN, 1).add(Hue.BLUE, 1);
//        System.out.println(Hue.RED);
//        System.out.println(Hue.GREEN);
//        System.out.println(Hue.BLUE);
//        System.out.println(Huetilities.hueAverage(one));
//        HueSet two = new HueSet(10);
//        two.add(Hue.RED, 1).add(Hue.BLUE, 10);
//        System.out.println(Hue.RED);
//        System.out.println(Hue.BLUE);
//        System.out.println(Huetilities.hueAverage(two));
//        Color blue = new Color(Hue.BLUE, 100f, 50f);
//        Color yellow = new Color(Hue.YELLOW, 1f, 1f);
//        System.out.println(blue.OKLABtoRGB());
//        for (double i = 0; i < 10; i++) {
//            double r = i / 10;
//            for (double j = 0; j < 10; j++) {
//                double g = j / 10;
//                for (double k = 0; k < 10; k++) {
//                    double b = k / 10;
//                    System.out.print(r);
//                    System.out.print(" ");
//                    System.out.print(g);
//                    System.out.print(" ");
//                    System.out.print(b);
//                    System.out.print(" ");
//                    fromRGB.RGBtoOKLAB(new RGB(r, g, b));
//                    fromRGB.OKLABtoRGB();
////                    System.out.println(fromRGB.OKLABtoRGB());
//                    System.out.println();
//                }
//            }
//        }

//        Color fromRGB = new Color();
//        BufferedImage table = new BufferedImage(200, 360, TYPE_INT_RGB);
//        Graphics2D g2d = table.createGraphics();
//        for (int i = 0; i < 360; i++) {
//            System.out.print(i);
//            System.out.print(" ");
////            RGB rgb = new Color(new Hue(i), 1, 1).HSVtoRGB();
////            fromRGB.RGBtoOKLAB(rgb);
//            fromRGB.setHue(new Hue(i));
////            fromRGB.setChroma(0.33);
//////            fromRGB.setChroma((fromRGB.getChroma() + 0.3) / 2);
////            System.out.print(fromRGB.getChroma());
////            System.out.print(" ");
////            Color blue = new Color();
////            blue.setHue(new Hue(110 + (i / 9.0) - 20));  // 260 is probably the bluest blue, 110 is probably the yellowest yellow
////            blue.setChroma(0.5);
////            blue.setLuma(0.8);
////            RGB oklabRGB = blue.OKLABtoRGB();
////            RGB oklabRGB = LABoratory.gamutClipPreserveChroma(fromRGB);
////            System.out.println(oklabRGB);
////            System.out.println(fromRGB);
////            g2d.setColor(new java.awt.Color(LABoratory.clipQuant(rgb.getR()), LABoratory.clipQuant(rgb.getG()), LABoratory.clipQuant(rgb.getB())));
//            for (int j = 0; j < 200; j++) {
//                double l_c = j/200.0;
//                fromRGB.setLuma(l_c);
//                fromRGB.setChroma(0.33333*l_c+0.05);
//                RGB oklabRGB = fromRGB.OKLABtoRGB();
//                g2d.setColor(new java.awt.Color(LABoratory.clipQuant(oklabRGB.getR()), LABoratory.clipQuant(oklabRGB.getG()), LABoratory.clipQuant(oklabRGB.getB())));
//                g2d.drawLine(j, i, j+1, i);
//            }
//            System.out.println();
//        }
//        try {
//            File outputfile = new File("/Users/alix/Documents/art and design/rainbow.png");
//            ImageIO.write(table, "png", outputfile);
//        } catch (IOException e) {
//            System.out.println("image write failed");
//        }
//        g2d.dispose();

//        BufferedImage table = new BufferedImage(180, 360, TYPE_INT_RGB);
//        Graphics2D g2d = table.createGraphics();
//        for (int i = 0; i < 360; i++) {
//            Palette palette = new Palette(new Hue(i), 1, 1,
//                    i / 18.0, Math.sin(i / 10.0), 4, 5,
//                    0, 30, 0.35, Math.sin(i / 36.0),
//                    0.45, 0.0125, 0.075,
//                    0.5, 0.15);
////            Palette palette = new Palette(new Hue(i), 1, 1,
////                    0, 0, 0, 0,
////                    0, 0, 0, 0,
////                    0.5, 0, 0,
////                    0.55, 0);
//            RGB[] rgb = palette.getColors();
//            for (int j = 0; j < 9; j++) {
////                System.out.println(rgb);
//                g2d.setColor(new java.awt.Color(LABoratory.clipQuant(rgb[j].getR()), LABoratory.clipQuant(rgb[j].getG()), LABoratory.clipQuant(rgb[j].getB())));
//                g2d.drawLine(j * 20, i, j * 20 + 16, i);
//            }
////            System.out.println();
//        }
//        try {
//            File outputfile = new File("/Users/alix/Documents/art and design/palettes.png");
//            ImageIO.write(table, "png", outputfile);
//        } catch (IOException e) {
//            System.out.println("image write failed");
//        }
//        g2d.dispose();

//        BufferedImage tex = null;
//        try {
//            tex = ImageIO.read(new File("/Users/alix/Documents/art and design/endless metallurgy/metals_base/dust.png"));
//        } catch (IOException e) {
//            System.out.println("image read failed");
//        }
//        BufferedImage texNoColor = null;
//        try {
//            texNoColor = ImageIO.read(new File("/Users/alix/Documents/art and design/endless metallurgy/metals_base/wire_coil_wood.png"));
//        } catch (IOException e) {
//            System.out.println("image read failed");
//        }
//        BufferedImage texOverlay = null;
//        try {
//            texOverlay = ImageIO.read(new File("/Users/alix/Documents/art and design/endless metallurgy/metals_base/sparkle3.png"));
//        } catch (IOException e) {
//            System.out.println("image read failed");
//        }
//        BufferedImage texNothing = new BufferedImage(tex.getWidth(), tex.getHeight(), TYPE_INT_ARGB);
//        RGBPalette premadePurpleBlue = new RGBPalette( "#191919,#272730,#3E3B4C,#59505B,#786E77,#968A8D,#A89D9D,#C4BBBB,#D9D9D9");
//        Palette orange = new Palette(new Hue(80), 0.6, 1,
//                2, 3, 4, 5,
//                0, 10, 0.35, 3.125,
//                0.475, 0.05, 0.1,
//                0.5, 0.15);
//        Palette blue = new Palette(new Hue(270), 0.6, 1,
//                2, 3, 4, 10,
//                0, 10, 0.25, 3.5,
//                0.15, 0.025, 0.05,
//                0.45, 0.15);
        Palette green = new Palette(new Hue(170), 0.7, 1,
                2, 3, 4, 10,
                0, -20, 0.25, 3.5,
                0.15, 0.025, 0.025,
                0.45, 0.15);
//        PaletteApplicator pa = new PaletteApplicator(green.getRGBPalette(), tex);
//        BufferedImage output = pa.colorifyAll();
//        try {
//            File outputfile = new File("/Users/alix/Documents/art and design/endless metallurgy/dust.png");
//            ImageIO.write(output, "png", outputfile);
//        } catch (IOException e) {
//            System.out.println("image write failed");
//        }
//    }
        PaletteApplicator pa = new PaletteApplicator(green.getRGBPalette());
        pa.colorizeFolder("/Users/alix/Documents/art and design/endless metallurgy/metals_base", "/Users/alix/Documents/art and design/endless metallurgy/recolor");
    }
}
