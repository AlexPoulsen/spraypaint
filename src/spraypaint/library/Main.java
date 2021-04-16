package library;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

import static java.awt.image.BufferedImage.*;

public class Main {

    public static void main(String[] args) {
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
//        PaletteApplicator pa = new PaletteApplicator(green.getRGBPalette(), tex);
//        BufferedImage output = pa.colorifyAll();
//        try {
//            File outputfile = new File("/Users/alix/Documents/art and design/endless metallurgy/dust.png");
//            ImageIO.write(output, "png", outputfile);
//        } catch (IOException e) {
//            System.out.println("image write failed");
//        }
//    }
//        long start = System.nanoTime();
//        for (int i = 0; i < 100; i++) {
//        Palette orange_block = new Palette(new Hue(80), 0.5, 1,
//                2, 3, 4, 5,
//                0, 10, 0.35, 3.125,
//                0.35, 0.00625, 0.075,
//                0.575, 0.075);
        Palette copper2_block = new Palette(new Hue(55), 0.35, 1,
                2, 3, 4, 5,
                0, 10, 0.35, 3.125,
                0.225, 0.01, 0.05,
                0.5, 0.075);
        Palette orange_item = new Palette(new Hue(80), 0.6, 1,
                2, 3, 4, 5,
                0, 10, 0.35, 3.125,
                0.4, 0.0125, 0.085,
                0.575, 0.12);
        Palette orange_block_rust1 = new Palette(new Hue(65), 0.5, 1,
                3, 4, 2, -10,
                -20, -10, 0.25, 3.125,
                0.15, 0.065, 0.025,
                0.4125, 0.07);
        Palette orange_block_rust2 = new Palette(new Hue(115), 0.75, 1,
                3, 4, 2, -10,
                -20, -10, 0.25, 3.125,
                0.15, 0.065, 0.025,
                0.4, 0.07);
        Palette orange_block_rust3 = new Palette(new Hue(170), 0.75, 1,
                3, 4, 2, -10,
                -20, -10, 0.25, 3.125,
                0.15, 0.065, 0.025,
                0.4125, 0.07);
        Palette iron_block = new Palette(new Hue(150), 0.5, 1,
                2, 3, 4, 5,
                0, 40, 0.1, 3.125,
                0.00125, 0.05, 0.075,
                0.575, 0.075);
        Palette iron_item = new Palette(new Hue(80), 0.6, 1,
                2, 3, 4, 5,
                0, 10, 0.35, 3.125,
                0.4, 0.0125, 0.085,
                0.575, 0.12);
        Palette iron_block_rust1 = new Palette(new Hue(70), 0.5, 1,
                3, 4, 2, -10,
                -20, -15, 0.25, 3.125,
                0.005, 0.065, 0.025,
                0.425, 0.075);
        Palette iron_block_rust2 = new Palette(new Hue(60), 0.75, 0.75,
                3, 4, 2, -10,
                -20, -10, 0.25, 3.125,
                0.175, 0.065, 0.025,
                0.375, 0.065);
        Palette iron_block_rust3 = new Palette(new Hue(45), 0.75, 0.5,
                3, 4, 2, -10,
                -20, 20, 0.25, 3.125,
                0.225, 0.035, 0.025,
                0.3125, 0.05);
//        Palette blue = new Palette(new Hue(270), 0.6, 1,
//                2, 3, 4, 10,
//                0, 10, 0.25, 3.5,
//                0.15, 0.025, 0.05,
//                0.45, 0.15);
//        Palette green = new Palette(new Hue(170), 0.7, 1,
//                2, 3, 4, 10,
//                0, -20, 0.25, 3.5,
//                0.15, 0.025, 0.025,
//                0.45, 0.15);
//        PaletteApplicator pa = new PaletteApplicator(iron_item.getRGBPalette());
//        PaletteApplicator pa = new PaletteApplicator(orange_item.getRGBPalette());
//        try {
//            BufferedImage output = pa.colorizeBaseOnly("/Users/alix/Documents/art and design/endless metallurgy/items_base.png");
//            File outputfile = new File("/Users/alix/Documents/art and design/endless metallurgy/items_recolor.png");
//            ImageIO.write(output, "png", outputfile);
//        } catch (IOException e) {
//            System.out.println("image write failed");
//        }

//        try {
//            pa.swapPalette(iron_block.getRGBPalette(), iron_block_rust1.getRGBPalette(), iron_block_rust2.getRGBPalette(), iron_block_rust3.getRGBPalette());
////            pa.swapPalette(copper2_block.getRGBPalette(), orange_block_rust1.getRGBPalette(), orange_block_rust2.getRGBPalette(), orange_block_rust3.getRGBPalette());
//            BufferedImage output = pa.colorizeBaseOnly4Color("/Users/alix/Documents/art and design/endless metallurgy/blocks_base_4color.png");
//            File outputfile = new File("/Users/alix/Documents/art and design/endless metallurgy/blocks_recolor_4color_iron_with_palette.png");
//            ImageIO.write(output, "png", outputfile);
//        } catch (IOException e) {
//            System.out.println("image write failed");
//        }

//        pa.colorizeFolder("/Users/alix/Documents/art and design/endless metallurgy/metals_base", "/Users/alix/Documents/art and design/endless metallurgy/recolor/green");
//        pa.swapPalette(orange.getRGBPalette());
//        pa.colorizeFolder("/Users/alix/Documents/art and design/endless metallurgy/metals_base", "/Users/alix/Documents/art and design/endless metallurgy/recolor/orange");
//        pa.swapPalette(blue.getRGBPalette());
//        pa.colorizeFolder("/Users/alix/Documents/art and design/endless metallurgy/metals_base", "/Users/alix/Documents/art and design/endless metallurgy/recolor/blue");
//        }
//        long end = System.nanoTime();
//        System.out.println("Location: " + (end - start));

//        BufferedImage table = new BufferedImage(180, 361, TYPE_INT_RGB);
//        Graphics2D g2d = table.createGraphics();
////        Palette[] rusts = new Palette[]{iron_block_rust1, iron_block_rust2, iron_block_rust3};
//        Palette[] palettes = new Palette[]{iron_block, iron_block_rust1, iron_block_rust2, iron_block_rust3};
//        for (int i = 0; i <= 360; i++) {
////            Palette one;
////            Palette two;
////            if (i < 120) {
////                one = iron_block;
////                two = iron_block_rust1;
////            } else if (i < 240) {
////                one = iron_block_rust1;
////                two = iron_block_rust2;
////            } else if (i < 360) {
////                one = iron_block_rust2;
////                two = iron_block_rust3;
////            } else {
////                one = iron_block_rust3;
////                two = iron_block_rust3;
////            }
////            RGB[] rgb = one.average(two, (i % 120.0) / 120.0).getColors();
//            double[] weights = new double[]{0.0, 0.0, 0.0, 0.0};
//            if (i < 120) {
//                weights[0] = 1 - ((i % 120.0) / 120.0);
//                weights[1] = (i % 120.0) / 120.0;
//            } else if (i < 240) {
//                weights[0] = 0;
//                weights[1] = 1 - ((i % 120.0) / 120.0);
//                weights[2] = (i % 120.0) / 120.0;
//            } else if (i < 360) {
//                weights[0] = 0;
//                weights[1] = 0;
//                weights[2] = 1 - ((i % 120.0) / 120.0);
//                weights[3] = (i % 120.0) / 120.0;
//            } else {
//                weights[0] = 0;
//                weights[1] = 0;
//                weights[2] = 0;
//                weights[3] = 1;
//            }
//            Palette avg = Palette.average(palettes, weights);
////            System.out.println(Arrays.toString(weights));
////            System.out.println(avg.getBaseHue());
//            RGB[] rgb = avg.getColors();
//            for (int j = 0; j < 9; j++) {
////                System.out.println(rgb);
//                g2d.setColor(new java.awt.Color(LABoratory.clipQuant(rgb[j].getR()), LABoratory.clipQuant(rgb[j].getG()), LABoratory.clipQuant(rgb[j].getB())));
//                g2d.drawLine(j * 20, i, j * 20 + 16, i);
//            }
////            System.out.println();
//        }
//        try {
//            File outputfile = new File("/Users/alix/Documents/art and design/palette_average_iron_ncolor_2.png");
//            ImageIO.write(table, "png", outputfile);
//        } catch (IOException e) {
//            System.out.println("image write failed");
//        }
//        g2d.dispose();

        BufferedImage table = new BufferedImage(304, 768, TYPE_INT_RGB);
        Graphics2D g2d = table.createGraphics();
//        Palette[] palettes = new Palette[]{iron_block, iron_block_rust1, iron_block_rust2, iron_block_rust3};
        Palette[] palettes = new Palette[]{copper2_block, orange_block_rust1, orange_block_rust2, orange_block_rust3};
        PaletteApplicator pa = new PaletteApplicator(iron_block.getRGBPalette());
        for (int i = 0; i < 16; i++) {
            double[] weights; // = new double[]{0.0, 0.0, 0.0, 0.0};
            weights = Palette.spreadSineBlend(i, 1.2);
//            if (i < 5) {
//                weights[0] = 1 - ((i % 5) / 5.0);
//                weights[1] = (i % 5) / 5.0;
//            } else if (i < 10) {
//                weights[0] = 0;
//                weights[1] = 1 - ((i % 5) / 5.0);
//                weights[2] = (i % 5) / 5.0;
//            } else if (i < 15) {
//                weights[0] = 0;
//                weights[1] = 0;
//                weights[2] = 1 - ((i % 5) / 5.0);
//                weights[3] = (i % 5) / 5.0;
//            } else {
//                weights[0] = 0;
//                weights[1] = 0;
//                weights[2] = 0;
//                weights[3] = 1;
//            }
//            System.out.println(Arrays.toString(weights));
            Palette avg = Palette.average(palettes, weights);
//            RGB[] rgb = avg.getColors();
            pa.swapPalette(avg.getRGBPalette());
            int rownum;
            if (i < 2) {
                rownum = 1;
            } else if (i < 4) {
                rownum = 2;
            } else if (i < 6) {
                rownum = 3;
            } else if (i < 8) {
                rownum = 4;
            } else if (i < 11) {
                rownum = 5;
            } else if (i < 14) {
                rownum = 6;
            } else {
                rownum = 7;
            }
            try {
                g2d.drawImage(
                        pa.colorizeBaseOnly("/Users/alix/Documents/art and design/endless metallurgy/blocks_base_2/row_" + rownum + ".png")
                        , 0, i * 48, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            File outputfile = new File("/Users/alix/Documents/art and design/endless metallurgy/palette_average_copper_blocks_sine_1_2_alt2.png");
            ImageIO.write(table, "png", outputfile);
        } catch (IOException e) {
            System.out.println("image write failed");
        }
        g2d.dispose();
    }
}
