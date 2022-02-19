package de.c8121.packing;

/**
 * Position denotes center of Box.
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

        if (position != null)
            this.move(position.x(), position.y(), position.z());

        if (dimension != null)
            this.resize(dimension.xs(), dimension.ys(), dimension.zs());
    }


    /**
     * Copy constructor
     */
    public Box(final Box box) {
        this.move(box.x, box.y, box.z);
        this.resize(box.xs, box.ys, box.zs);
    }

    /**
     * Creates a box with given size at given position
     */
    public Box(final int x, final int y, final int z, final int xs, final int ys, final int zs) {
        this.move(x, y, z);
        this.resize(xs, ys, zs);
    }

    /**
     * Creates a box with given size at position 0/0/0
     */
    public Box(final int xs, final int ys, final int zs) {
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
    public void move(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
