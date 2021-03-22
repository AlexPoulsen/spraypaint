package library;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class PaletteApplicator {
    // contains magenta on areas outside of defined palette.
    // if magenta sections are visible on a texture, you likely have used incorrect lookup colors on the texture.
    RGBPalette fullColors;
    BufferedImage imageBase;
    BufferedImage imageUncolored;
    BufferedImage imageOverlay;

    public PaletteApplicator(RGBPalette colors) {
        RGBPalette expandedColors = new RGBPalette(16);
        for (int i = 0; i < 16; i++) {
            if (i < 4) {
                expandedColors.set(i, new RGB(255, 0, 255));
            } else if (i > 12) {
                expandedColors.set(i, new RGB(255, 0, 255));
            } else {
                expandedColors.set(i, colors.get(i - 4));
            }
        }
        this.fullColors = expandedColors;
        this.imageBase = null;
        this.imageUncolored = null;
        this.imageOverlay = null;
    }

    public PaletteApplicator(RGBPalette colors, BufferedImage image) {
        RGBPalette expandedColors = new RGBPalette(16);
        for (int i = 0; i < 16; i++) {
            if (i < 4) {
                expandedColors.set(i, new RGB(255, 0, 255));
            } else if (i > 12) {
                expandedColors.set(i, new RGB(255, 0, 255));
            } else {
                expandedColors.set(i, colors.get(i - 4));
            }
        }
        this.fullColors = expandedColors;
        this.imageBase = image;
        this.imageUncolored = null;
        this.imageOverlay = null;
    }

    public PaletteApplicator(RGBPalette colors, BufferedImage imageBase, BufferedImage imageUncolored, BufferedImage imageOverlay) {
        RGBPalette expandedColors = new RGBPalette(16);
        for (int i = 0; i < 16; i++) {
            if (i < 4) {
                expandedColors.set(i, new RGB(255, 0, 255));
            } else if (i > 12) {
                expandedColors.set(i, new RGB(255, 0, 255));
            } else {
                expandedColors.set(i, colors.get(i - 4));
            }
        }
        this.fullColors = expandedColors;
        this.imageBase = imageBase;
        this.imageUncolored = imageUncolored;
        this.imageOverlay = imageOverlay;
    }

    public void swapPalette(RGBPalette colors) {
        RGBPalette expandedColors = new RGBPalette(16);
        for (int i = 0; i < 16; i++) {
            if (i < 4) {
                expandedColors.set(i, new RGB(255, 0, 255));
            } else if (i > 12) {
                expandedColors.set(i, new RGB(255, 0, 255));
            } else {
                expandedColors.set(i, colors.get(i - 4));
            }
        }
        this.fullColors = expandedColors;
    }

    public void swapImageBase(BufferedImage image) {
        this.imageBase = image;
    }

    public void imageUncolored(BufferedImage image) {
        this.imageUncolored = image;
    }

    public void imageOverlay(BufferedImage image) {
        this.imageOverlay = image;
    }

    public void swapImages(BufferedImage imageBase, BufferedImage imageUncolored, BufferedImage imageOverlay) {
        this.imageBase = imageBase;
        this.imageUncolored = imageUncolored;
        this.imageOverlay = imageOverlay;
    }

    public BufferedImage colorizeBaseOnly() {
        return this.colorizeBaseOnly(this.imageBase);
    }

    public BufferedImage colorizeBaseOnly(String filename) throws IOException {
        BufferedImage tex;
        try {
            tex = ImageIO.read(new File(filename));
            return this.colorizeBaseOnly(tex);
        } catch (IOException e) {
            System.out.println("image read failed");
            throw e;
        }
    }

    public BufferedImage colorizeBaseOnly(BufferedImage image) {
        BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), TYPE_INT_ARGB);
        for (int y = 0; y     < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth();  x++) {

                int value = image.getRGB(x, y);

                int a = (value >> 24) & 0xff;
                int r = (value >> 16) & 0xfc;
                int g = (value >> 8)  & 0xfc;
                int b = value         & 0xfc;

                int newPixel;

                if (a == 0) {
                    newPixel = 0;
                } else if ((r != g) || (g != b)) {
                    newPixel = value;
                } else {
                    RGB newColor = this.fullColors.get(r >> 4);
                    int newR = (int) newColor.getR();
                    int newG = (int) newColor.getG();
                    int newB = (int) newColor.getB();
                    newPixel = (0xff000000) | (newR<<16) | (newG<<8) | newB;
                }
                out.setRGB(x, y, newPixel);
            }
        }
        return out;
    }

    public BufferedImage colorizeAll() {
        return this.colorizeAll(this.imageBase, this.imageUncolored, this.imageOverlay);
    }

    public BufferedImage colorizeAll(String sharedPath, String imageBase, String imageUncolored, String imageOverlay) throws IOException{
        String pathBase;
        String pathUncolored;
        String pathOverlay;

        if (sharedPath.endsWith("/")) {
            pathBase = sharedPath + imageBase;
            pathUncolored = sharedPath + imageUncolored;
            pathOverlay = sharedPath + imageOverlay;
        } else {
            pathBase = sharedPath + "/" + imageBase;
            pathUncolored = sharedPath + "/" + imageUncolored;
            pathOverlay = sharedPath + "/" + imageOverlay;
        }
        return this.colorizeAll(pathBase, pathUncolored, pathOverlay);
    }

    public BufferedImage colorizeAll(String imageBase, String imageUncolored, String imageOverlay) throws IOException {
        BufferedImage texBase;
        try {
            texBase = ImageIO.read(new File(imageBase));
        } catch (IOException e) {
            System.out.println("base image read failed");
            throw e;
        }

        BufferedImage texUncolored;
        if (imageUncolored.equals("_null_")) {
            texUncolored = new BufferedImage(texBase.getWidth(), texBase.getHeight(), TYPE_INT_ARGB);
        } else {
            try {
                texUncolored = ImageIO.read(new File(imageUncolored));
            } catch (IOException e) {
                System.out.println("uncolored image read failed");
                throw e;
            }
        }

        BufferedImage texOverlay;
        if (imageOverlay.equals("_null_")) {
            texOverlay = new BufferedImage(texBase.getWidth(), texBase.getHeight(), TYPE_INT_ARGB);
        } else {
            try {
                texOverlay = ImageIO.read(new File(imageOverlay));
            } catch (IOException e) {
                System.out.println("overlay image read failed");
                throw e;
            }
        }

        return this.colorizeAll(texBase, texUncolored, texOverlay);
    }

    public BufferedImage colorizeAll(BufferedImage imageBase, BufferedImage imageUncolored, BufferedImage imageOverlay) {
        BufferedImage out = new BufferedImage(imageBase.getWidth(), imageBase.getHeight(), TYPE_INT_ARGB);
        for (int y = 0; y     < imageBase.getHeight(); y++) {
            for (int x = 0; x < imageBase.getWidth();  x++) {

                int valueBase      =      imageBase.getRGB(x, y);
                int valueUncolored = imageUncolored.getRGB(x, y);
                int valueOverlay   =   imageOverlay.getRGB(x, y);

                int a =          (valueBase      >> 24) & 0xff;
                int r =          (valueBase      >> 16) & 0xfc;
                int g =          (valueBase      >> 8)  & 0xfc;
                int b =           valueBase             & 0xfc;

                int aUncolored = (valueUncolored >> 24) & 0xff;

                int aOverlay =   (valueOverlay   >> 24) & 0xff;
                int rOverlay =   (valueOverlay   >> 16) & 0xfc;
                int gOverlay =   (valueOverlay   >> 8)  & 0xfc;
                int bOverlay =    valueOverlay          & 0xfc;

                int newPixel;

                if ((a != 0) && (aUncolored == 0) && (aOverlay == 0)) {
                    if ((r != g) || (g != b)) {
                        newPixel = valueBase;
                    } else {
                        RGB newColor = this.fullColors.get(r >> 4);
                        int newR = (int) newColor.getR();
                        int newG = (int) newColor.getG();
                        int newB = (int) newColor.getB();
                        newPixel = (0xff000000) | (newR<<16) | (newG<<8) | newB;
                    }
                } else {
                    if ((aUncolored == 0) && (aOverlay == 0)) {
                        newPixel = 0;
                    } else if ((aUncolored != 0) && (aOverlay == 0)) {
                        newPixel = valueUncolored;
                    } else {
                        if ((rOverlay != gOverlay) || (gOverlay != bOverlay)) {
                            newPixel = valueOverlay;
                        } else {
                            RGB newColor = this.fullColors.get(rOverlay >> 4);
                            int newR = (int) newColor.getR();
                            int newG = (int) newColor.getG();
                            int newB = (int) newColor.getB();
                            newPixel = (0xff000000) | (newR<<16) | (newG<<8) | newB;
                        }
                    }
                }
                out.setRGB(x, y, newPixel);
            }
        }
        return out;
    }

    private void colorizeFolderSubFile(String in, Path out) {
        System.out.print("Colorizing `");
        System.out.print(in);
        System.out.print("` to `");
        System.out.print(out.toString());
        System.out.println("`");
        BufferedImage tex;
        try {
            System.out.println(in);
            tex = ImageIO.read(new File(in));
            BufferedImage output = this.colorizeBaseOnly(tex);
            try {
                String outputPath = out.toString();
                if (!(Files.exists(out.getParent()))) {
                    Files.createDirectories(out.getParent());
                }
                System.out.println(outputPath);
                File outputfile = new File(outputPath);
                ImageIO.write(output, "png", outputfile);
            } catch (IOException e3) {
                System.out.println("!! image write failed");
//                e.printStackTrace();
            }
        } catch (IOException e1) {
            System.out.println("!! image read failed");
//            e.printStackTrace();
        }
    }

    private void colorizeFolderSubFile3(String in1, String in2, String in3, Path out) {
//        String in1Str = in1.toString();
//        String in2Str = in2.toString();
//        String in3Str = in3.toString();
        System.out.print("Colorizing with three layers `");
        System.out.print(in1);
        System.out.print("`, `");
        System.out.print(in2);
        System.out.print("`, `");
        System.out.print(in3);
        System.out.print("` to `");
        System.out.print(out);
        System.out.println("`");
        try {
            BufferedImage output = this.colorizeAll(in1, in2, in3);
            try {
                String outputPath = out.toString();
                if (!(Files.exists(out.getParent()))) {
                    Files.createDirectories(out.getParent());
                }
                System.out.println(outputPath);
                File outputfile = new File(outputPath);
                ImageIO.write(output, "png", outputfile);
            } catch (IOException e2) {
                System.out.println("!! image write failed");
                e2.printStackTrace();
            }
        } catch (IOException e1) {
            System.out.println("!! image read failed");
            e1.printStackTrace();
        }
    }

    private void colorizeFolderSub(Path in, Path out) throws IOException {
//                System.out.println(pathIn);
        Path txtPath = in.resolve("_spraypaint.txt");
//                System.out.println(txtPath);
        InputStream inputStream = Files.newInputStream(txtPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        System.out.print(">> entering folder `");
        System.out.print(in);
        System.out.println("`");
        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//            colorizeFolderSub(line, pathIn, pathOut);
            if (line.startsWith("/")) {
                String newFolder = line.substring(2);
                colorizeFolderSub(in.resolve(newFolder), out.resolve(newFolder));
//            } else if (line.length() <= 1) {
//                continue;
            } else {
                if (line.contains("=")) {
                    String[] lineSplit1 = line.replace(" = ", "=").replace("= ", "=").replace(" =", "=").split("=");
                    String[] args = lineSplit1[1].trim().replace(", ", ",").split(",");
                    String dest = lineSplit1[0];
                    String arg2;
                    String arg3;
                    switch (args.length) {
                        case 0:
                            System.out.print("line `");
                            System.out.print(line);
                            System.out.println("` is badly formatted. if an = is present, there must be at least one following argument");
                            break;
                        case 1:
                            colorizeFolderSubFile(in.resolve(args[0]).toString(), out.resolve(dest));
                            break;
                        case 2:
                            arg2 = args[1];
                            if (!arg2.equals("_null_")) {
                                arg2 = in.resolve(arg2).toString();
                            }
                            colorizeFolderSubFile3(in.resolve(args[0]).toString(), arg2, "_null_", out.resolve(dest));
                            break;
                        case 3:
                            arg2 = args[1];
                            if (!arg2.equals("_null_")) {
                                arg2 = in.resolve(arg2).toString();
                            }
                            arg3 = args[2];
                            if (!arg3.equals("_null_")) {
                                arg3 = in.resolve(arg3).toString();
                            }
                            colorizeFolderSubFile3(in.resolve(args[0]).toString(), arg2, arg3, out.resolve(dest));
                            break;
                    }
                } else {
                    colorizeFolderSubFile(in.resolve(line).toString(), out.resolve(line));
                }
            }
        }
        System.out.print("<< exiting folder `");
        System.out.print(in);
        System.out.println("`");
    }

    public void colorizeFolder(String in, String out) {
        try {
            Path pathIn = Paths.get(in).toRealPath();
            Path pathOut = Paths.get(out);
            if (!(Files.exists(pathOut.getParent()))) {
                Files.createDirectories(pathOut.getParent());
                pathOut = pathOut.toRealPath();
            }
            colorizeFolderSub(pathIn, pathOut);
//            try {
//                colorizeFolderSub(pathIn, pathOut);
//            } catch (IOException e2) {
//                System.err.println(e2);
//            }
        } catch (IOException e) {
            System.out.format("I/O Exception: %1$s", e);
            e.printStackTrace();
        }
    }
}
