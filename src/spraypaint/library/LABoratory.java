package library;


final class LABoratory {
    public static double chroma(double a, double b) {
        return Math.sqrt(a * a + b * b);
    }

    public static double hue(double a, double b) {
        return Math.atan2(b, a);
    }

    public static double chroma(LAB lab) {
        return Math.sqrt(lab.getA() * lab.getA() + lab.getB() * lab.getB());
    }

    public static double hue(LAB lab) {
        return Math.atan2(lab.getB(), lab.getA());
    }

    public static double a(double chroma, double hue) {
        return chroma * Math.cos(hue);
    }

    public static double b(double chroma, double hue) {
        return chroma * Math.sin(hue);
    }

    public static double a(LCH lch) {
        return lch.getC() * Math.cos(lch.getH());
    }

    public static double b(LCH lch) {
        return lch.getC() * Math.sin(lch.getH());
    }

    public static double toNonlinear(double x) {
        if (x < 0.0031308) {
            return 12.92 * x;
        } else {
            return (1.055) * Math.pow(x, 1.0 / 2.4) - 0.055;
        }
    }

    public static double toLinear(double x) {
        if (x < 0.04045) {
            return x / 12.92;
        } else {
            return Math.pow((x + 0.055) / (1 + 0.055), 2.4);
        }
    }

    public static int clipQuant(double value) {
        int out = (int) Math.round(value * 255);
        return Math.max(Math.min(out, 255), 0);
    }

    public static double computeMaxSaturation(Color color) {
        LAB lab = new LCH(color.luma, color.chroma, color.hue.getRadians()).toLAB();
        AB ab = new AB(lab.getA(), lab.getB()).normalize();
        return computeMaxSaturation(ab);
    }

    public static double computeMaxSaturation(AB ab) {
        double a = ab.getA();
        double b = ab.getB();
        double k0;
        double k1;
        double k2;
        double k3;
        double k4;
        double wl;
        double wm;
        double ws;

        if (-1.88170328 * a - 0.80936493 * b > 1) {
            // Red component
            k0 = +1.19086277; k1 = +1.76576728; k2 = +0.59662641; k3 = +0.75515197; k4 = +0.56771245;
            wl = +4.0767416621; wm = -3.3077115913; ws = +0.2309699292;
        }
        else if (1.81444104 * a - 1.19445276 * b > 1) {
            // Green component
            k0 = +0.73956515; k1 = -0.45954404; k2 = +0.08285427; k3 = +0.12541070; k4 = +0.14503204;
            wl = -1.2681437731; wm = +2.6097574011; ws = -0.3413193965;
        }
        else {
            // Blue component
            k0 = +1.35733652; k1 = -0.00915799; k2 = -1.15130210; k3 = -0.50559606; k4 = +0.00692167;
            wl = -0.0041960863; wm = -0.7034186147; ws = +1.7076147010;
        }

        // Approximate max saturation using a polynomial:
        double S = k0 + k1 * a + k2 * b + k3 * a * a + k4 * a * b;

        // Do one step Halley's method to get closer
        // this gives an error less than 10e6, except for some blue hues where the dS/dh is close to infinite
        // this should be sufficient for most applications, otherwise do two/three steps
        double k_l = +0.3963377774 * a + 0.2158037573 * b;
        double k_m = -0.1055613458 * a - 0.0638541728 * b;
        double k_s = -0.0894841775 * a - 1.2914855480 * b;

        double l_ = 1.0 + S * k_l;
        double m_ = 1.0 + S * k_m;
        double s_ = 1.0 + S * k_s;

        double l = l_ * l_ * l_;
        double m = m_ * m_ * m_;
        double s = s_ * s_ * s_;

        double l_dS = 3.0 * k_l * l_ * l_;
        double m_dS = 3.0 * k_m * m_ * m_;
        double s_dS = 3.0 * k_s * s_ * s_;

        double l_dS2 = 6.0 * k_l * k_l * l_;
        double m_dS2 = 6.0 * k_m * k_m * m_;
        double s_dS2 = 6.0 * k_s * k_s * s_;

        double f  = wl * l     + wm * m     + ws * s;
        double f1 = wl * l_dS  + wm * m_dS  + ws * s_dS;
        double f2 = wl * l_dS2 + wm * m_dS2 + ws * s_dS2;

        S = S - f * f1 / (f1*f1 - 0.5 * f * f2);

        return S;
    }

    public static LC findCusp (Color color) {
        LAB lab = new LCH(color.luma, color.chroma, color.hue.getRadians()).toLAB();
        AB ab = new AB(lab.getA(), lab.getB()).normalize();
        return findCusp(ab);
    }

    public static LC findCusp (AB ab) {
        double a = ab.getA();
        double b = ab.getB();
        double sCusp = computeMaxSaturation(ab);
        RGB rgbAtMax = Color.OKLABtoRGB(new LAB(1, sCusp * a, sCusp * b));
        double lCusp = Math.cbrt(1.f / Math.max(Math.max(rgbAtMax.getR(), rgbAtMax.getG()), rgbAtMax.getB()));
        double cCusp = lCusp * sCusp;
        return new LC(lCusp, cCusp);
    }

    public static double findGamutIntersection(Color color, double L1, double C1, double L0) {
        LAB lab = new LCH(color.luma, color.chroma, color.hue.getRadians()).toLAB();
        AB ab = new AB(lab.getA(), lab.getB()).normalize();
        return findGamutIntersection(ab, L1, C1, L0);
    }

    public static double findGamutIntersection(AB ab, double L1, double C1, double L0) {
        LC cusp = findCusp(ab);
        return findGamutIntersection(ab, L1, C1, L0, cusp);
    }

    public static double findGamutIntersection(AB ab, double L1, double C1, double L0, LC cusp) {
        double a = ab.getA();
        double b = ab.getB();
        double t;
        if (((L1 - L0) * cusp.C - (cusp.L - L0) * C1) <= 0.f)
        {
            // Lower half
            t = cusp.C * L0 / (C1 * cusp.L + cusp.C * (L0 - L1));
        }
        else
        {
            // Upper half

            // First intersect with triangle
            t = cusp.C * (L0 - 1.f) / (C1 * (cusp.L - 1.f) + cusp.C * (L0 - L1));

            // Then one step Halley's method
            {
                double dL = L1 - L0;
                double dC = C1;

                double k_l = +0.3963377774f * a + 0.2158037573f * b;
                double k_m = -0.1055613458f * a - 0.0638541728f * b;
                double k_s = -0.0894841775f * a - 1.2914855480f * b;

                double l_dt = dL + dC * k_l;
                double m_dt = dL + dC * k_m;
                double s_dt = dL + dC * k_s;


                // If higher accuracy is required, 2 or 3 iterations of the following block can be used:
                double L = L0 * (1.f - t) + t * L1;
                double C = t * C1;

                double l_ = L + C * k_l;
                double m_ = L + C * k_m;
                double s_ = L + C * k_s;

                double l = l_ * l_ * l_;
                double m = m_ * m_ * m_;
                double s = s_ * s_ * s_;

                double ldt = 3 * l_dt * l_ * l_;
                double mdt = 3 * m_dt * m_ * m_;
                double sdt = 3 * s_dt * s_ * s_;

                double ldt2 = 6 * l_dt * l_dt * l_;
                double mdt2 = 6 * m_dt * m_dt * m_;
                double sdt2 = 6 * s_dt * s_dt * s_;

                double r = 4.0767416621f * l - 3.3077115913f * m + 0.2309699292f * s - 1;
                double r1 = 4.0767416621f * ldt - 3.3077115913f * mdt + 0.2309699292f * sdt;
                double r2 = 4.0767416621f * ldt2 - 3.3077115913f * mdt2 + 0.2309699292f * sdt2;

                double u_r = r1 / (r1 * r1 - 0.5f * r * r2);
                double t_r = -r * u_r;

                double g = -1.2681437731f * l + 2.6097574011f * m - 0.3413193965f * s - 1;
                double g1 = -1.2681437731f * ldt + 2.6097574011f * mdt - 0.3413193965f * sdt;
                double g2 = -1.2681437731f * ldt2 + 2.6097574011f * mdt2 - 0.3413193965f * sdt2;

                double u_g = g1 / (g1 * g1 - 0.5f * g * g2);
                double t_g = -g * u_g;

                b = -0.0041960863f * l - 0.7034186147f * m + 1.7076147010f * s - 1;
                double b1 = -0.0041960863f * ldt - 0.7034186147f * mdt + 1.7076147010f * sdt;
                double b2 = -0.0041960863f * ldt2 - 0.7034186147f * mdt2 + 1.7076147010f * sdt2;

                double u_b = b1 / (b1 * b1 - 0.5f * b * b2);
                double t_b = -b * u_b;

                t_r = u_r >= 0.f ? t_r : Double.MAX_VALUE;
                t_g = u_g >= 0.f ? t_g : Double.MAX_VALUE;
                t_b = u_b >= 0.f ? t_b : Double.MAX_VALUE;

                t += Math.min(t_r, Math.min(t_g, t_b));
            }
        }

        return t;
    }

    public static double clamp(double x, double min, double max) {
        return Math.max(Math.min(x, max), min);
    }

    public static RGB gamutClipPreserveChroma(Color color) {
        RGB rgb = color.OKLABtoRGB();
        if (rgb.getR() < 1 && rgb.getG() < 1 && rgb.getB() < 1 && rgb.getR() > 0 && rgb.getG() > 0 && rgb.getB() > 0)
            return rgb;

        LAB lab = color.toLAB();

        double L = lab.getL();
        double eps = 0.00001f;
        double C = Math.max(eps, Math.sqrt(lab.getA() * lab.getA() + lab.getB() * lab.getB()));
        double a_ = lab.getA() / C;
        double b_ = lab.getB() / C;

        double L0 = clamp(L, 0, 1);

        double t = findGamutIntersection(new AB(a_, b_).normalize(), L, C, L0);
        double L_clipped = L0 * (1 - t) + t * L;
        double C_clipped = t * C;

        return Color.OKLABtoRGB(new LAB(L_clipped,C_clipped * a_,C_clipped * b_));
    }

    public static RGB gamutClipProjectTo0_5(Color color) {
        RGB rgb = color.OKLABtoRGB();
        if (rgb.getR() < 1 && rgb.getG() < 1 && rgb.getB() < 1 && rgb.getR() > 0 && rgb.getG() > 0 && rgb.getB() > 0)
            return rgb;

        LAB lab = color.toLAB();

        double L = lab.getL();
        double eps = 0.00001f;
        double C = Math.max(eps, Math.sqrt(lab.getA() * lab.getA() + lab.getB() * lab.getB()));
        double a_ = lab.getA() / C;
        double b_ = lab.getB() / C;

        double L0 = 0.5;

        double t = findGamutIntersection(new AB(a_, b_).normalize(), L, C, L0);
        double L_clipped = L0 * (1 - t) + t * L;
        double C_clipped = t * C;

        return Color.OKLABtoRGB(new LAB(L_clipped,C_clipped * a_,C_clipped * b_));
    }

    public static RGB gamutClipProjectToLCusp(Color color) {
        RGB rgb = color.OKLABtoRGB();
        if (rgb.getR() < 1 && rgb.getG() < 1 && rgb.getB() < 1 && rgb.getR() > 0 && rgb.getG() > 0 && rgb.getB() > 0)
            return rgb;

        LAB lab = color.toLAB();

        double L = lab.getL();
        double eps = 0.00001f;
        double C = Math.max(eps, Math.sqrt(lab.getA() * lab.getA() + lab.getB() * lab.getB()));
        double a_ = lab.getA() / C;
        double b_ = lab.getB() / C;

        LC cusp = findCusp(new AB(a_, b_).normalize());
        double L0 = cusp.L;

        double t = findGamutIntersection(new AB(a_, b_).normalize(), L, C, L0, cusp);
        double L_clipped = L0 * (1 - t) + t * L;
        double C_clipped = t * C;

        return Color.OKLABtoRGB(new LAB(L_clipped,C_clipped * a_,C_clipped * b_));
    }

    public static RGB gamutClipAdaptiveL0_0_5(Color color) {
        return gamutClipAdaptiveL0_0_5(color, 0.05);
    }

    public static RGB gamutClipAdaptiveL0_0_5(Color color, double alpha) {
        RGB rgb = color.OKLABtoRGB();
        if (rgb.getR() < 1 && rgb.getG() < 1 && rgb.getB() < 1 && rgb.getR() > 0 && rgb.getG() > 0 && rgb.getB() > 0)
            return rgb;

        LAB lab = color.toLAB();

        double L = lab.getL();
        double eps = 0.00001f;
        double C = Math.max(eps, Math.sqrt(lab.getA() * lab.getA() + lab.getB() * lab.getB()));
        double a_ = lab.getA() / C;
        double b_ = lab.getB() / C;

        double Ld = L - 0.5;
        double e1 = 0.5f + Math.abs(Ld) + alpha * C;
        double L0 = 0.5f*(1.f + Math.signum(Ld)*(e1 - Math.sqrt(e1*e1 - 2.f * Math.abs(Ld))));

        double t = findGamutIntersection(new AB(a_, b_).normalize(), L, C, L0);
        double L_clipped = L0 * (1 - t) + t * L;
        double C_clipped = t * C;

        return Color.OKLABtoRGB(new LAB(L_clipped,C_clipped * a_,C_clipped * b_));
    }

    public static RGB gamutClipAdaptiveL0_lCusp(Color color) {
        return gamutClipAdaptiveL0_0_5(color, 0.05);
    }

    public static RGB gamutClipAdaptiveL0_lCusp(Color color, double alpha) {
        RGB rgb = color.OKLABtoRGB();
        if (rgb.getR() < 1 && rgb.getG() < 1 && rgb.getB() < 1 && rgb.getR() > 0 && rgb.getG() > 0 && rgb.getB() > 0)
            return rgb;

        LAB lab = color.toLAB();

        double L = lab.getL();
        double eps = 0.00001f;
        double C = Math.max(eps, Math.sqrt(lab.getA() * lab.getA() + lab.getB() * lab.getB()));
        double a_ = lab.getA() / C;
        double b_ = lab.getB() / C;

        LC cusp = findCusp(new AB(a_, b_).normalize());

        double Ld = L - cusp.L;
        double k = 2.f * (Ld > 0 ? 1.f - cusp.L : cusp.L);

        double e1 = 0.5f*k + Math.abs(Ld) + alpha * C/k;
        double L0 = cusp.L + 0.5f * (Math.signum(Ld) * (e1 - Math.sqrt(e1 * e1 - 2.f * k * Math.abs(Ld))));

        double t = findGamutIntersection(new AB(a_, b_).normalize(), L, C, L0, cusp);
        double L_clipped = L0 * (1 - t) + t * L;
        double C_clipped = t * C;

        return Color.OKLABtoRGB(new LAB(L_clipped,C_clipped * a_,C_clipped * b_));
    }
}
