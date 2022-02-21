package de.c8121.packing.util;

import de.c8121.packing.Box;

/**
 * Position denotes center of Box.
 */
public class BasicBox implements Box {

    private int x = 0;
    private int y = 0;
    private int z = 0;

    private int xs = 0;
    private int ys = 0;
    private int zs = 0;


    /**
     * Copy constructor
     */
    public BasicBox(final Box box) {
        this.placeAt(box.x(), box.y(), box.z());
        this.resize(box.xs(), box.ys(), box.zs());
    }

    /**
     * Creates a box with given size at given position
     */
    public BasicBox(final int x, final int y, final int z, final int xs, final int ys, final int zs) {
        this.placeAt(x, y, z);
        this.resize(xs, ys, zs);
    }

    /**
     * Creates a box with given size at position 0/0/0
     */
    public BasicBox(final int xs, final int ys, final int zs) {
        this.resize(xs, ys, zs);
    }

    @Override
    public int xs() {
        return this.xs;
    }

    @Override
    public int ys() {
        return this.ys;
    }

    @Override
    public int zs() {
        return this.zs;
    }

    /**
     *
     */
    @Override
    public void resize(final int xs, final int ys, final int zs) {
        this.xs = xs;
        this.ys = ys;
        this.zs = zs;
    }


    @Override
    public int x() {
        return this.x;
    }

    @Override
    public int y() {
        return this.y;
    }

    @Override
    public int z() {
        return this.z;
    }

    /**
     *
     */
    @Override
    public void placeAt(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     */
    @Override
    public void moveBy(final int x, final int y, final int z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    @Override
    public String toString() {
        return "Box{"
                + x + "/" + y + "/" + z
                + " " + xs + "/" + ys + "/" + zs
                + " " + xMin() + ".." + xMax() + "/" + yMin() + ".." + yMax() + "/" + zMin() + ".." + zMax()
                + "}";
    }
}
