package de.c8121.packing;

/**
 *
 */
public class Box implements Position, Dimension {

    private int x = 0;
    private int y = 0;
    private int z = 0;

    private int xs = 0;
    private int ys = 0;
    private int zs = 0;

    /**
     * Creates a box at given {@link Position} (if not null, 0/0/0 otherwise)
     * and with given {@link Dimension} (if not null, 0/0/0 otherwise).
     *
     * @param position  Can be {@code null}. Position values will be initialized with zero if it is {@code null}.
     * @param dimension Can be {@code null}. Dimension values will be initialized with zero if it is {@code null}.
     */
    public Box(final Position position, final Dimension dimension) {

        if (position != null) {
            this.x = position.x();
            this.y = position.y();
            this.z = position.z();
        }

        if (dimension != null) {
            this.xs = dimension.xs();
            this.ys = dimension.ys();
            this.zs = dimension.zs();
        }
    }


    /**
     * Copy constructor
     */
    public Box(final Box box) {

        this.x = box.x;
        this.y = box.y;
        this.z = box.z;

        this.xs = box.xs;
        this.ys = box.ys;
        this.zs = box.zs;
    }

    /**
     * Creates a box with given size at given position
     */
    public Box(final int x, final int y, final int z, final int w, final int h, final int d) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.xs = w;
        this.ys = h;
        this.zs = d;
    }

    /**
     * Creates a box with given size at position 0/0/0
     */
    public Box(final int w, final int h, final int d) {
        this.xs = w;
        this.ys = h;
        this.zs = d;
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
}
