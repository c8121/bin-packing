package de.c8121.packing;

/**
 * Position denotes center of Box.
 */
public interface Box {

    /**
     * X-Position
     */
    int x();

    /**
     * Y-Position
     */
    int y();

    /**
     * Z-Position
     */
    int z();

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
     * Change size
     */
    void resize(final int xs, final int ys, final int zs);


    /**
     * Move to given point
     */
    void placeAt(final int x, final int y, final int z);

    /**
     * Move by given values.
     */
    void moveBy(final int x, final int y, final int z);

    /**
     *
     */
    default int xMin() {
        return this.x() - this.xs() / 2;
    }

    /**
     *
     */
    default int yMin() {
        return this.y() - this.ys() / 2;
    }

    /**
     *
     */
    default int zMin() {
        return this.z() - this.zs() / 2;
    }

    /**
     *
     */
    default int xMax() {
        return this.x() + this.xs() / 2;
    }

    /**
     *
     */
    default int yMax() {
        return this.y() + this.ys() / 2;
    }

    /**
     *
     */
    default int zMax() {
        return this.z() + this.zs() / 2;
    }


    /**
     *
     */
    default boolean positionEquals(final Box b) {
        if (this == b)
            return true;
        if (b == null)
            return false;
        return this.x() == b.x() && this.y() == b.y() && this.z() == b.z();
    }

    /**
     *
     */
    default boolean dimensionEquals(final Box b) {
        if (this == b)
            return true;
        if (b == null)
            return false;
        return this.xs() == b.xs() && this.ys() == b.ys() && this.zs() == b.zs();
    }

    /**
     * Check if {@code this} fits into {@code b} without any rotation.
     */
    default boolean fitsIn(final Box b) {
        return this.xs() <= b.xs() && this.ys() <= b.ys() && this.zs() <= b.zs();
    }

    /**
     *
     */
    default boolean intersects(final Box b) {
        if (this.xMin() < b.xMax() && this.xMax() > b.xMin())
            if (this.yMin() < b.yMax() && this.yMax() > b.yMin())
                if (this.zMin() < b.zMax() && this.zMax() > b.zMin()) {
                    //System.out.println("Intersect:\n\t" + this + "\n\t" + b);
                    return true;
                }

        return false;
    }
}
