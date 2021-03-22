package library;


public class Color {
    BaseHue hue;
    double chroma;
    double luma;

    public Color(BaseHue hue, double chroma, double luma) {
        this.hue = hue;
        this.chroma = chroma;
        this.luma = luma;
    }

    public Color() {
        this.hue = Hue.RED;
        this.chroma = 0;
        this.luma = 0;
    }

    public BaseHue getHue() {
        return hue;
    }

    public void setHue(BaseHue hue) {
        this.hue = hue;
    }

    public double getChroma() {
        return chroma;
    }

    public void setChroma(double chroma) {
        this.chroma = chroma;
    }

    public double getLuma() {
        return luma;
    }

    public void setLuma(double luma) {
        this.luma = luma;
    }

    public void RGBtoOKLAB(RGB rgb) {
        double l = 0.4122214708f * LABoratory.toLinear(rgb.getR())
                + 0.5363325363f * LABoratory.toLinear(rgb.getG())
                + 0.0514459929f * LABoratory.toLinear(rgb.getB());
        double m = 0.2119034982f * LABoratory.toLinear(rgb.getR())
                + 0.6806995451f * LABoratory.toLinear(rgb.getG())
                + 0.1073969566f * LABoratory.toLinear(rgb.getB());
        double s = 0.0883024619f * LABoratory.toLinear(rgb.getR())
                + 0.2817188376f * LABoratory.toLinear(rgb.getG())
                + 0.6299787005f * LABoratory.toLinear(rgb.getB());

        double l_ = Math.cbrt(l);
        double m_ = Math.cbrt(m);
        double s_ = Math.cbrt(s);

        LAB lab = new LAB(
                0.2104542553f*l_ + 0.7936177850f*m_ - 0.0040720468f*s_,
                1.9779984951f*l_ - 2.4285922050f*m_ + 0.4505937099f*s_,
                0.0259040371f*l_ + 0.7827717662f*m_ - 0.8086757660f*s_
        );
        LCH lch = lab.toLCH();
        this.luma = lch.getL();
        this.chroma = lch.getC();
        this.hue = new Hue(0).setFromRadians(lch.getH());
    }

    public static RGB OKLABtoRGB(LAB lab) {
        double l_ = lab.getL() + 0.3963377774 * lab.getA() + 0.2158037573 * lab.getB();
        double m_ = lab.getL() - 0.1055613458 * lab.getA() - 0.0638541728 * lab.getB();
        double s_ = lab.getL() - 0.0894841775 * lab.getA() - 1.2914855480 * lab.getB();

        double l = l_*l_*l_;
        double m = m_*m_*m_;
        double s = s_*s_*s_;

        return new RGB(
                LABoratory.toNonlinear(+4.0767416621 * l - 3.3077115913 * m + 0.2309699292 * s),
                LABoratory.toNonlinear(-1.2684380046 * l + 2.6097574011 * m - 0.3413193965 * s),
                LABoratory.toNonlinear(-0.0041960863 * l - 0.7034186147 * m + 1.7076147010 * s)
        );
    }

    public RGB OKLABtoRGB() {
        LAB lab = this.toLAB();
        double l_ = lab.getL() + 0.3963377774 * lab.getA() + 0.2158037573 * lab.getB();
        double m_ = lab.getL() - 0.1055613458 * lab.getA() - 0.0638541728 * lab.getB();
        double s_ = lab.getL() - 0.0894841775 * lab.getA() - 1.2914855480 * lab.getB();

        double l = l_*l_*l_;
        double m = m_*m_*m_;
        double s = s_*s_*s_;

        return new RGB(
                LABoratory.toNonlinear(+4.0767416621 * l - 3.3077115913 * m + 0.2309699292 * s),
                LABoratory.toNonlinear(-1.2684380046 * l + 2.6097574011 * m - 0.3413193965 * s),
                LABoratory.toNonlinear(-0.0041960863 * l - 0.7034186147 * m + 1.7076147010 * s)
        );
    }

    public LAB toLAB() {
        return new LCH(this.luma, this.chroma, this.hue.getRadians()).toLAB();
    }

    public RGB HSVtoRGB() {
        double chroma = this.chroma * this.luma;
        double hueDiv = hue.get() / 60;
        double x = chroma * (1 - Math.abs((hueDiv % 2) - 1));
        double rInitial = 0;
        double gInitial = 0;
        double bInitial = 0;
        if ((0 <= hueDiv) && (hueDiv <= 1)) {
            rInitial = chroma;
            gInitial = x;
            bInitial = 0;
        } else if ((1 < hueDiv) && (hueDiv <= 2)) {
            rInitial = x;
            gInitial = chroma;
            bInitial = 0;
        } else if ((2 < hueDiv) && (hueDiv <= 3)) {
            rInitial = 0;
            gInitial = chroma;
            bInitial = x;
        } else if ((3 < hueDiv) && (hueDiv <= 4)) {
            rInitial = 0;
            gInitial = x;
            bInitial = chroma;
        } else if ((4 < hueDiv) && (hueDiv <= 5)) {
            rInitial = x;
            gInitial = 0;
            bInitial = chroma;
        } else if ((5 < hueDiv) && (hueDiv <= 6)) {
            rInitial = chroma;
            gInitial = 0;
            bInitial = x;
        } else {
            System.out.println("bad values, the following number should be between 0 and 6, inclusive");
            System.out.println(hueDiv);
        }
        double m = this.luma - chroma;
        return new RGB(rInitial + m, gInitial + m, bInitial + m);
    }

    @Override
    public String toString() {
        return "Color{" +
                "hue=" + hue +
                ", chroma=" + chroma +
                ", luma=" + luma +
                '}';
    }
}
