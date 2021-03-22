package library;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class PaletteApplicator {
    // contains magenta on areas outside of defined palette.
    // if magenta sections are visible on a texture, you likely have used incorrect lookup colors on the texture.
    RGBPalette fullColors;
    BufferedImage imageBase;
    BufferedImage imageUncolored;
    BufferedImage imageOverlay;

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
        this.imageUncolored = new BufferedImage(image.getWidth(), image.getHeight(), TYPE_INT_ARGB);
        this.imageOverlay = new BufferedImage(image.getWidth(), image.getHeight(), TYPE_INT_ARGB);
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

    public BufferedImage colorifyBaseOnly() {
        return this.colorifyBaseOnly(this.imageBase);
    }

    public BufferedImage colorifyBaseOnly(String fileName) throws IOException {
        BufferedImage tex;
        try {
            tex = ImageIO.read(new File("/Users/alix/Documents/art and design/endless metallurgy/metals_base/ingot.png"));
            return this.colorifyBaseOnly(tex);
        } catch (IOException e) {
            System.out.println("image read failed");
            throw e;
        }
    }

    public BufferedImage colorifyBaseOnly(BufferedImage image) {
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

    public BufferedImage colorifyAll() {
        return this.colorifyAll(this.imageBase, this.imageUncolored, this.imageOverlay);
    }

    public BufferedImage colorifyAll(String baseUrl, String imageBase, String imageUncolored, String imageOverlay) throws IOException {
        String pathBase;
        String pathUncolored;
        String pathOverlay;

        if (baseUrl.endsWith("/")) {
            pathBase = baseUrl + imageBase;
            pathUncolored = baseUrl + imageUncolored;
            pathOverlay = baseUrl + imageOverlay;
        } else {
            pathBase = baseUrl + "/" + imageBase;
            pathUncolored = baseUrl + "/" + imageUncolored;
            pathOverlay = baseUrl + "/" + imageOverlay;
        }

        BufferedImage texBase;
        try {
            texBase = ImageIO.read(new File(pathBase));
        } catch (IOException e) {
            System.out.println("base image read failed");
            throw e;
        }

        BufferedImage texUncolored;
        try {
            texUncolored = ImageIO.read(new File(pathUncolored));
        } catch (IOException e) {
            System.out.println("uncolored image read failed");
            throw e;
        }

        BufferedImage texOverlay;
        try {
            texOverlay = ImageIO.read(new File(pathOverlay));
        } catch (IOException e) {
            System.out.println("overlay image read failed");
            throw e;
        }

        return this.colorifyAll(texBase, texUncolored, texOverlay);
    }

    public BufferedImage colorifyAll(BufferedImage imageBase, BufferedImage imageUncolored, BufferedImage imageOverlay) {
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
}
