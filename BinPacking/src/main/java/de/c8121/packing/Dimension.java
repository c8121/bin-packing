package de.c8121.packing;

public interface Dimension {

    /**
     * X-Size (Width)
     */
    int xs();

    /**
     * Y-Size (Height)
     */
    int ys();

    /**
     * Z-Size (Depth)
     */
    int zs();

    /**
     *
     */
    static boolean dimensionEquals(final Dimension a, final Dimension b) {
        if (a == b)
            return true;
        if (a == null || b == null)
            return false;
        return a.xs() == b.xs() && a.ys() == b.ys() && a.zs() == b.zs();
    }

    /**
     * Check if {@code a} fits into {@code b} without any rotation.
     */
    static boolean fitsIn(final Dimension a, final Dimension b) {
        return a.xs() <= b.xs() && a.ys() <= b.ys() && a.zs() <= b.zs();
    }

}
