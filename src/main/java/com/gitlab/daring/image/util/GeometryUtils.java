package com.gitlab.daring.image.util;


import java.awt.*;

public class GeometryUtils {

    public static Rectangle getCenterRect(Rectangle r, double rectSize) {
        if (rectSize == 1.0) return new Rectangle(r);
        Point p = getCenter(r);
        Dimension sd = scale(r.getSize(), rectSize);
        return new Rectangle(p.x - sd.width / 2, p.y - sd.height / 2, sd.width, sd.height);
    }

    public static Rectangle getCenterRect(Dimension d, double rectSize) {
        return getCenterRect(new Rectangle(0, 0, d.width, d.height), rectSize);
    }

    public static Point getCenter(Rectangle r) {
        return new Point(r.x + r.width / 2, r.y + r.height / 2);
    }

    public static Dimension scale(Dimension d, double f) {
        return new Dimension(roundInt(d.width * f), roundInt(d.height * f));
    }

    public static Dimension scaleToMax(Dimension d, Dimension md) {
        if (md.width >= d.width && md.height >= d.height) return d;
        double f = Math.min(1.0 * md.width / d.width, 1.0 * md.height / d.height);
        return scale(d, f);
    }

    public static int roundInt(double v) {
        return (int) Math.round(v);
    }

    private GeometryUtils() {
    }

}
